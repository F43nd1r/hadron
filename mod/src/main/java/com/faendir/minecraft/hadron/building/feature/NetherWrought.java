package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.Hadron;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.base.HadronCube;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

/**
 * @author lukas
 * @since 25.07.19
 */
public class NetherWrought {
    private static final String CONFIG_PATH = Hadron.Module.BUILDING + ".NetherWrought";
    private static final String NETHERWROUGHT_ID = "netherwrought";
    @HadronCube.WithStairsSlabsAndWalls(id = NETHERWROUGHT_ID, texture = NETHERWROUGHT_ID, configPath = CONFIG_PATH)
    @Recipe.Shaped(id = NETHERWROUGHT_ID, pattern = {"xx", "xx"}, keys = @Recipe.Key(key = "x", value = "minecraft:netherrack"), count = 4, configPath = CONFIG_PATH)
    public static final Block NETHERWROUGHT = new Block(Block.Properties.from(Blocks.NETHER_BRICKS)).setRegistryName(NETHERWROUGHT_ID);

    private static final String NETHERWROUGHT_BRICKS_ID = "netherwrought_bricks";
    @HadronCube.WithStairsSlabsAndWalls(id = NETHERWROUGHT_BRICKS_ID, texture = NETHERWROUGHT_BRICKS_ID, configPath = CONFIG_PATH)
    @Recipe.Shaped(id = NETHERWROUGHT_BRICKS_ID, pattern = {"xx", "xx"}, keys = @Recipe.Key(key = "x", value = NETHERWROUGHT_ID), count = 4, configPath = CONFIG_PATH)
    public static final Block NETHERWROUGHT_BRICKS = new Block(Block.Properties.from(Blocks.NETHER_BRICKS)).setRegistryName(NETHERWROUGHT_BRICKS_ID);


}
