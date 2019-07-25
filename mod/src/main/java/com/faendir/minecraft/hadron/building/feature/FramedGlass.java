package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.annotation.Texture;
import com.faendir.minecraft.hadron.base.HadronCube;
import com.faendir.minecraft.hadron.base.HadronPaneBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GlassBlock;

/**
 * @author lukas
 * @since 24.07.19
 */
public class FramedGlass {
    private static final String FRAMED_GLASS_ID = "framed_glass";
    private static final String DECORATIONS = "decorations";
    @HadronCube(id = FRAMED_GLASS_ID, texture = FRAMED_GLASS_ID)
    @Recipe.Shaped(id = FRAMED_GLASS_ID, pattern = {"gig", "igi", "gig"}, keys = {
            @Recipe.Key(key = "g", value = "minecraft:glass"),
            @Recipe.Key(key = "i", value = "minecraft:iron_ingot")
    }, count = 4)
    public static final Block FRAMED_GLASS = new GlassBlock(Block.Properties.from(Blocks.GLASS)).setRegistryName(FRAMED_GLASS_ID);

    private static final String FRAMED_GLASS_PANE_ID = "framed_glass_pane";
    private static final String FRAMED_GLASS_PANE_TOP = FRAMED_GLASS_PANE_ID + "_top";
    private static final String FRAMED_GLASS_PANE_POST = FRAMED_GLASS_PANE_ID + "_post";
    private static final String FRAMED_GLASS_PANE_NOSIDE = FRAMED_GLASS_PANE_ID + "_noside";
    private static final String FRAMED_GLASS_PANE_NOSIDE_ALT = FRAMED_GLASS_PANE_ID + "_noside_alt";
    private static final String FRAMED_GLASS_PANE_SIDE = FRAMED_GLASS_PANE_ID + "_side";
    private static final String FRAMED_GLASS_PANE_SIDE_ALT = FRAMED_GLASS_PANE_ID + "_side_alt";
    private static final String PANE = "pane";
    private static final String EDGE = "edge";

    @Register(Block.class)
    @Recipe.Shaped(id = FRAMED_GLASS_PANE_ID, pattern = {"ggg", "ggg"}, keys = {
            @Recipe.Key(key = "g", value = FRAMED_GLASS_ID),
    }, count = 16)
    @BlockState(id = FRAMED_GLASS_PANE_ID, multipart = {
            @BlockState.Multipart(apply = @BlockState.Model(FRAMED_GLASS_PANE_POST)),
            @BlockState.Multipart(when = @BlockState.Conditions(@BlockState.Condition(name = "north", value = "true")), apply = @BlockState.Model(FRAMED_GLASS_PANE_SIDE)),
            @BlockState.Multipart(when = @BlockState.Conditions(@BlockState.Condition(name = "east", value = "true")), apply = @BlockState.Model(value = FRAMED_GLASS_PANE_SIDE, y = 90)),
            @BlockState.Multipart(when = @BlockState.Conditions(@BlockState.Condition(name = "south", value = "true")), apply = @BlockState.Model(FRAMED_GLASS_PANE_SIDE_ALT)),
            @BlockState.Multipart(when = @BlockState.Conditions(@BlockState.Condition(name = "west", value = "true")), apply = @BlockState.Model(value = FRAMED_GLASS_PANE_SIDE_ALT, y= 90)),
            @BlockState.Multipart(when = @BlockState.Conditions(@BlockState.Condition(name = "north", value = "false")), apply = @BlockState.Model(FRAMED_GLASS_PANE_NOSIDE)),
            @BlockState.Multipart(when = @BlockState.Conditions(@BlockState.Condition(name = "east", value = "false")), apply = @BlockState.Model(FRAMED_GLASS_PANE_NOSIDE_ALT)),
            @BlockState.Multipart(when = @BlockState.Conditions(@BlockState.Condition(name = "south", value = "false")), apply = @BlockState.Model(value = FRAMED_GLASS_PANE_NOSIDE_ALT, y = 90)),
            @BlockState.Multipart(when = @BlockState.Conditions(@BlockState.Condition(name = "west", value = "false")), apply = @BlockState.Model(value = FRAMED_GLASS_PANE_NOSIDE, y= 270))
    })
    @Model(id = FRAMED_GLASS_PANE_NOSIDE, parent = "minecraft:template_glass_pane_noside", textures = @Texture(key = PANE, id = FRAMED_GLASS_ID))
    @Model(id = FRAMED_GLASS_PANE_NOSIDE_ALT, parent = "minecraft:template_glass_pane_noside_alt", textures = @Texture(key = PANE, id = FRAMED_GLASS_ID))
    @Model(id = FRAMED_GLASS_PANE_POST, parent = "minecraft:template_glass_pane_post", textures = {
            @Texture(key = PANE, id = FRAMED_GLASS_ID),
            @Texture(key = EDGE, id = FRAMED_GLASS_PANE_TOP)
    })
    @Model(id = FRAMED_GLASS_PANE_SIDE, parent = "minecraft:template_glass_pane_side", textures = {
            @Texture(key = PANE, id = FRAMED_GLASS_ID),
            @Texture(key = EDGE, id = FRAMED_GLASS_PANE_TOP)
    })
    @Model(id = FRAMED_GLASS_PANE_SIDE_ALT, parent = "minecraft:template_glass_pane_side_alt", textures = {
            @Texture(key = PANE, id = FRAMED_GLASS_ID),
            @Texture(key = EDGE, id = FRAMED_GLASS_PANE_TOP)
    })
    @GenerateItem(value = FRAMED_GLASS_PANE_ID, category = DECORATIONS, parent = "minecraft:item/generated", textures = @Texture(key = "layer0", id = FRAMED_GLASS_ID))
    public static final Block FRAMED_GLASS_PANE = new HadronPaneBlock(Block.Properties.from(Blocks.GLASS_PANE)).setRegistryName(FRAMED_GLASS_PANE_ID);
}
