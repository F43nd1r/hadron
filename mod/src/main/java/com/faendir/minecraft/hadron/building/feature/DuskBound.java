package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.Hadron;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.base.HadronCube;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

/**
 * @author lukas
 * @since 24.07.19
 */
public class DuskBound {
    private static final String CONFIG_PATH = Hadron.Module.BUILDING + ".DuskBound";

    private static final String DUSKBOUND_ID = "duskbound";
    @HadronCube.WithStairsSlabsAndWalls(id = DUSKBOUND_ID, texture = DUSKBOUND_ID, configPath = CONFIG_PATH)
    @Recipe.Shaped(id = DUSKBOUND_ID, pattern = {"xxx", "xox", "xxx"}, keys = {
            @Recipe.Key(key = "x", value = "minecraft:obsidian"),
            @Recipe.Key(key = "o", value = "minecraft:purpur_block")
    }, count = 16, configPath = CONFIG_PATH)
    public static final Block DUSKBOUND = new Block(Block.Properties.from(Blocks.STONE_BRICKS)).setRegistryName(DUSKBOUND_ID);

    private static final String DUSKBOUND_LANTERN_ID = "duskbound_lantern";
    @HadronCube(id = DUSKBOUND_LANTERN_ID, texture = DUSKBOUND_LANTERN_ID, configPath = CONFIG_PATH)
    @Recipe.Shaped(id = DUSKBOUND_LANTERN_ID, pattern = {"xxx", "xox", "xxx"}, keys = {
            @Recipe.Key(key = "x", value = DUSKBOUND_ID),
            @Recipe.Key(key = "o", value = "minecraft:ender_pearl")
    }, count = 4, configPath = CONFIG_PATH)
    public static final Block DUSKBOUND_LANTERN = new Block(Block.Properties.from(DUSKBOUND).lightValue(15)).setRegistryName(DUSKBOUND_LANTERN_ID);

}
