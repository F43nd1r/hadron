package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.base.HadronCube;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

/**
 * @author lukas
 * @since 24.07.19
 */
public class CharredNetherBricks {
    private static final String CHARRED_NETHER_BRICKS_ID = "charred_nether_bricks";
    private static final String NETHER_BRICKS = "minecraft:nether_bricks";

    @HadronCube.WithStairsSlabsAndWalls(id = CHARRED_NETHER_BRICKS_ID, texture = CHARRED_NETHER_BRICKS_ID)
    @Recipe.Shapeless(id = CHARRED_NETHER_BRICKS_ID, ingredients = {NETHER_BRICKS, NETHER_BRICKS, NETHER_BRICKS, "minecraft:fire_charge"}, count = 3)
    public static final Block CHARRED_NETHER_BRICKS = new Block(Block.Properties.from(Blocks.NETHER_BRICKS)).setRegistryName(CHARRED_NETHER_BRICKS_ID);
}
