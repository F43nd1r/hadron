package com.faendir.minecraft.hadron.misc.feature;

import com.faendir.minecraft.hadron.Hadron;
import com.faendir.minecraft.hadron.network.MessageSetLockProfile;
import com.faendir.minecraft.hadron.network.NetworkHandler;
import com.faendir.minecraft.hadron.network.NetworkMessage;
import com.mojang.blaze3d.platform.GlStateManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.Collection;
import java.util.HashMap;


/**
 * @author lukas
 * @since 04.07.19
 */
@Mod.EventBusSubscriber(
        modid = Hadron.ID
)
public class LockDirectionHotkey {
    private static final KeyBinding keyBinding = new KeyBinding("hadron.key.lockBuilding", GLFW.GLFW_KEY_K, "key.categories.hadron");
    private static final String TAG_LOCKED_ONCE = "quark:locked_once";
    private static final HashMap<String, LockProfile> lockProfiles = new HashMap<>();
    private static final ResourceLocation GENERAL_ICONS_RESOURCE = new ResourceLocation(Hadron.ID, "textures/misc/general_icons.png");
    private static LockProfile clientProfile;

    static {
        NetworkHandler.register(MessageSetLockProfile.class);
        NetworkMessage.mapHandler(LockProfile.class, LockProfile::readProfile, LockProfile::writeProfile);
    }

    public LockDirectionHotkey() {
    }

    @Mod.EventBusSubscriber(
            modid = Hadron.ID,
            value = Dist.CLIENT,
            bus = Mod.EventBusSubscriber.Bus.MOD
    )
    public static class ClientProxy {
        @SubscribeEvent
        public static void registerHotkey(FMLClientSetupEvent event) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.isCanceled() || event.getResult() == Event.Result.DENY || event.getEntity() == null) {
            return;
        }

        IWorld world = event.getWorld();
        BlockState state = event.getPlacedBlock();
        BlockPos pos = event.getPos();

        String id = event.getEntity().getUniqueID().toString();
        if (lockProfiles.containsKey(id)) {
            LockProfile profile = lockProfiles.get(id);
            setBlockRotated(world, state, pos, profile.facing.getOpposite(), true, profile.half);
        }
    }

    public static void setBlockRotated(IWorld world, BlockState state, BlockPos pos, Direction face) {
        setBlockRotated(world, state, pos, face, false, -1);
    }

    public static void setBlockRotated(IWorld world, BlockState state, BlockPos pos, Direction face, boolean stateCheck, int half) {
        BlockState setState = state;
        Collection<IProperty<?>> props = state.getProperties();
        Block block = state.getBlock();

        // API hook
        /*if(block instanceof IRotationLockHandler)
            setState = ((IRotationLockHandler) block).setRotation(world, pos, setState, face, half != -1, half == 1);

            // General Facing
        else*/
        if (props.contains(BlockStateProperties.FACING)) {
            setState = state.with(BlockStateProperties.FACING, face);
        }

        // Horizontal Facing
        else if (props.contains(BlockStateProperties.HORIZONTAL_FACING) && face.getAxis() != Direction.Axis.Y) {
            if (block instanceof StairsBlock) {
                setState = state.with(BlockStateProperties.HORIZONTAL_FACING, face.getOpposite());
            } else {
                setState = state.with(BlockStateProperties.HORIZONTAL_FACING, face);
            }
        }

        // Pillar & Log Axis
        else if (props.contains(BlockStateProperties.AXIS)) {
            setState = state.with(BlockStateProperties.AXIS, face.getAxis());
        }

        // Quartz Variant/Axis
        /*else if(props.contains(BlockStateProperties.)) {
            BlockQuartz.EnumType type = state.getValue(BlockQuartz.VARIANT);
            if(ImmutableSet.of(BlockQuartz.EnumType.LINES_X, BlockQuartz.EnumType.LINES_Y, BlockQuartz.EnumType.LINES_Z).contains(type))
                setState = state.withProperty(BlockQuartz.VARIANT, BlockQuartz.VARIANT.parseValue("lines_" + face.getAxis().getName()).or(BlockQuartz.EnumType.LINES_Y));
        }*/

        // Hopper Facing
        else if (props.contains(HopperBlock.FACING)) {
            setState = state.with(HopperBlock.FACING, face == Direction.DOWN ? face : face.getOpposite());
        }

        if (half != -1) {
            if (props.contains(StairsBlock.HALF)) {
                setState = setState.with(StairsBlock.HALF, half == 1 ? Half.TOP : Half.BOTTOM);
            } else if (props.contains(SlabBlock.TYPE) && state.get(SlabBlock.TYPE) != SlabType.DOUBLE) {
                setState = setState.with(SlabBlock.TYPE, half == 1 ? SlabType.TOP : SlabType.BOTTOM);
            }
        }

        if (!stateCheck || setState != state) {
            world.setBlockState(pos, setState, 3);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogoff(PlayerEvent.PlayerLoggedOutEvent event) {
        lockProfiles.remove(event.getPlayer().getUniqueID().toString());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onKeyInputEvent(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        boolean down = keyBinding.isKeyDown();
        if (mc.isGameFocused() && down) {
            LockProfile newProfile;
            RayTraceResult result = mc.objectMouseOver;

            if (result != null && result.getType() == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult blockResult = (BlockRayTraceResult) result;
                int half = (int) ((blockResult.getHitVec().y - (int) blockResult.getHitVec().y) * 2);
                if (blockResult.getFace().getAxis() == Direction.Axis.Y) {
                    half = -1;
                }

                newProfile = new LockProfile(blockResult.getFace().getOpposite(), half);

            } else {
                Vec3d look = mc.player.getLookVec();
                newProfile = new LockProfile(Direction.getFacingFromVector((float) look.x, (float) look.y, (float) look.z), -1);
            }

            if (clientProfile != null && clientProfile.equals(newProfile)) {
                clientProfile = null;
            } else {
                clientProfile = newProfile;
            }
            NetworkHandler.INSTANCE.sendToServer(new MessageSetLockProfile(clientProfile));
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onHUDRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && clientProfile != null) {
            Minecraft mc = Minecraft.getInstance();
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.enableAlphaTest();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color4f(1F, 1F, 1F, 0.5F);

            mc.textureManager.bindTexture(GENERAL_ICONS_RESOURCE);

            int x = event.getWindow().getScaledWidth() / 2 + 20;
            int y = event.getWindow().getScaledHeight() / 2 - 8;
            AbstractGui.blit(x, y, clientProfile.facing.ordinal() * 16, 32, 16, 16, 256, 256);

            if (clientProfile.half > -1) {
                AbstractGui.blit(x + 16, y, clientProfile.half * 16, 48, 16, 16, 256, 256);
            }

            GlStateManager.popMatrix();
        }
    }

    public static void setProfile(PlayerEntity player, LockProfile profile) {
        String id = player.getUniqueID().toString();
        System.out.println(id);

        if (profile == null) {
            lockProfiles.remove(id);
        } else {
            boolean locked = player.getEntityData().getBoolean(TAG_LOCKED_ONCE);
            if (!locked) {
                ITextComponent text = new TranslationTextComponent("hadron.misc.rotationLockBefore");
                ITextComponent keybind = new KeybindTextComponent("hadron.key.lockBuilding");
                keybind.getStyle().setColor(TextFormatting.AQUA);
                text.appendSibling(keybind);
                text.appendSibling(new TranslationTextComponent("hadron.misc.rotationLockAfter"));
                player.sendMessage(text);

                player.getEntityData().putBoolean(TAG_LOCKED_ONCE, true);
            }

            lockProfiles.put(id, profile);
        }
    }

    public static class LockProfile {

        Direction facing;
        int half;

        public LockProfile(Direction facing, int half) {
            this.facing = facing;
            this.half = half;
        }

        public static LockProfile readProfile(ByteBuf buf) {
            boolean valid = buf.readBoolean();
            if (!valid) {
                return null;
            }

            int face = buf.readInt();
            int half = buf.readInt();
            return new LockProfile(Direction.class.getEnumConstants()[face], half);
        }

        public static void writeProfile(LockProfile p, ByteBuf buf) {
            if (p == null) {
                buf.writeBoolean(false);
            } else {
                buf.writeBoolean(true);
                buf.writeInt(p.facing.ordinal());
                buf.writeInt(p.half);
            }
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof LockProfile)) {
                return false;
            }

            LockProfile otherProfile = (LockProfile) other;
            return otherProfile.facing == facing && otherProfile.half == half;
        }

    }
}
