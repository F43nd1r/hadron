package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.Hadron;
import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.annotation.Texture;
import com.faendir.minecraft.hadron.base.HadronCube;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.Blocks;

/**
 * @author lukas
 * @since 25.07.19
 */
public class Midori {
    private static final String CONFIG_PATH = Hadron.Module.BUILDING + ".Midori";

    private static final String MIDORI_ID = "midori";
    @HadronCube.WithStairsSlabsAndWalls(id = MIDORI_ID, texture = MIDORI_ID, configPath = CONFIG_PATH)
    @Recipe.Shaped(id = MIDORI_ID, pattern = {"xx", "xx"}, keys = @Recipe.Key(key = "x", value = "minecraft:green_dye"), configPath = CONFIG_PATH)
    public static final Block MIDORI = new Block(Properties.from(Blocks.STONE_BRICKS)).setRegistryName(MIDORI_ID);

    private static final String MIDORI_PILLAR_ID = "midori_pillar";
    private static final String MIDORI_PILLAR_TOP = MIDORI_PILLAR_ID + "_top";
    @Register(Block.class)
    @BlockState(id = MIDORI_PILLAR_ID, variants = @BlockState.Variant(id = "", model = @BlockState.Model(MIDORI_PILLAR_ID)))
    @Model(id = MIDORI_PILLAR_ID, parent = "minecraft:cube_bottom_top", textures = {
            @Texture(key = "side", id = MIDORI_PILLAR_ID),
            @Texture(key = "top", id  = MIDORI_PILLAR_TOP ),
            @Texture(key = "bottom", id  = MIDORI_PILLAR_TOP )

    })
    @GenerateItem(value = MIDORI_PILLAR_ID, configPath = CONFIG_PATH)
    @Recipe.Shaped(id = MIDORI_PILLAR_ID, pattern = {"x", "x"}, keys = @Recipe.Key(key = "x", value = "midori_slabs"), configPath = CONFIG_PATH)
    public static final Block MIDORI_PILLAR = new Block(Properties.from(Blocks.STONE_BRICKS)).setRegistryName(MIDORI_PILLAR_ID);
}
