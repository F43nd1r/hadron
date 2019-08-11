package com.faendir.minecraft.hadron.tweaks;

import com.faendir.minecraft.hadron.Hadron;
import com.faendir.minecraft.hadron.generated.HadronConfigCreationEvent;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lukas
 * @since 24.07.19
 */
@Mod.EventBusSubscriber(
        modid = Hadron.ID,
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ProperStairMath {
    private static ForgeConfigSpec.BooleanValue enabled;
    private static ForgeConfigSpec.IntValue targetValue;
    private static final int DEFAULT_SIZE = 4;

    @SubscribeEvent
    public static void createConfig(HadronConfigCreationEvent event) {
        enabled = event.getBuilder().worldRestart().define(Hadron.Module.TWEAKS + ".ProperStairsMath.Enabled", true);
        targetValue = event.getBuilder().worldRestart().defineInRange(Hadron.Module.TWEAKS + ".ProperStairsMath.TargetSize", 8, 1, Integer.MAX_VALUE);
    }


    @Mod.EventBusSubscriber(
            modid = Hadron.ID
    )
    public static class ServerProxy {
        @SuppressWarnings({"unchecked", "deprecation"})
        @SubscribeEvent
        public static void start(FMLServerAboutToStartEvent event) {
            IReloadableResourceManager manager = event.getServer().getResourceManager();
            manager.addReloadListener((IResourceManagerReloadListener) resourceManager -> {
                try {
                    boolean isEnabled = enabled.get();
                    int target = targetValue.get();
                    RecipeManager recipeManager = event.getServer().getRecipeManager();
                    Field recipeField = RecipeManager.class.getDeclaredFields()[2];
                    recipeField.setAccessible(true);
                    Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> oldRecipes = (Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>>) recipeField.get(recipeManager);
                    Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = new HashMap<>(oldRecipes);
                    recipeField.set(recipeManager, recipes);
                    Map<ResourceLocation, IRecipe<?>> craftingRecipes = new HashMap<>(recipes.get(IRecipeType.CRAFTING));
                    recipes.put(IRecipeType.CRAFTING, craftingRecipes);
                    for (IRecipe<?> recipe : craftingRecipes.values()) {
                        ItemStack output = recipe.getRecipeOutput();
                        if (output.getItem() instanceof BlockItem) {
                            BlockItem item = (BlockItem) output.getItem();
                            if (item.getBlock() instanceof StairsBlock) {
                                if (isEnabled && output.getCount() == DEFAULT_SIZE) {
                                    output.setCount(target);
                                }
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
