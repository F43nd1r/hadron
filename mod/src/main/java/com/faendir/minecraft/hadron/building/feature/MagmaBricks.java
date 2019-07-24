package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.annotation.Texture;
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
    @Register(Block.class)
    @Recipe.Shapeless(id = MAGMA_BRICKS_ID, ingredients = {STONE_BRICKS, STONE_BRICKS, MAGMA_BLOCK, MAGMA_BLOCK}, count = 4)
    @BlockState(id = MAGMA_BRICKS_ID, variants = @BlockState.Variant(id = "", model = @BlockState.Model(MAGMA_BRICKS_ID)))
    @Model(id = MAGMA_BRICKS_ID, parent = "minecraft:cube_all", textures = @Texture(key = "all", id = MAGMA_BRICKS_ID))
    @GenerateItem(MAGMA_BRICKS_ID)
    @GenerateStairs(id = MAGMA_BRICKS_ID, texture = MAGMA_BRICKS_ID)
    @GenerateSlabs(id = MAGMA_BRICKS_ID, texture = MAGMA_BRICKS_ID)
    public static final Block MAGMA_BRICK = new Block(Block.Properties.from(Blocks.MAGMA_BLOCK).lightValue(3).tickRandomly()).setRegistryName(MAGMA_BRICKS_ID);
}
