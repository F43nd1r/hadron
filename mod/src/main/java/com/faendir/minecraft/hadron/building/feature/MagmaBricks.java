package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.base.HadronCube;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

/**
 * @author lukas
 * @since 24.07.19
 */
public class MagmaBricks {
    private static final String MAGMA_BRICKS_ID = "magma_bricks";
    private static final String STONE_BRICKS = "minecraft:stone_bricks";
    private static final String MAGMA_BLOCK = "minecraft:magma_block";
    @HadronCube.WithStairsAndSlabs(id = MAGMA_BRICKS_ID, texture = MAGMA_BRICKS_ID)
    @Recipe.Shapeless(id = MAGMA_BRICKS_ID, ingredients = {STONE_BRICKS, STONE_BRICKS, MAGMA_BLOCK, MAGMA_BLOCK}, count = 4)
    public static final Block MAGMA_BRICK = new Block(Block.Properties.from(Blocks.MAGMA_BLOCK).lightValue(3).tickRandomly()).setRegistryName(MAGMA_BRICKS_ID);
}
