package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.base.HadronCube;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

/**
 * @author lukas
 * @since 25.07.19
 */
public class SnowBricks {
    private static final String SNOW_BRICKS_ID = "snow_bricks";
    @HadronCube.WithStairsSlabsAndWalls(id = SNOW_BRICKS_ID, texture = SNOW_BRICKS_ID)
    @Recipe.Shaped(id = "{id}", pattern = {"xx", "xx"}, keys = @Recipe.Key(key = "x", value = "minecraft:snow_block"), count = 4)
    public static final Block SNOW_BRICKS = new Block(Block.Properties.from(Blocks.SNOW_BLOCK)).setRegistryName(SNOW_BRICKS_ID);
}
