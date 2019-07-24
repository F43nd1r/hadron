package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.GenerateWall;
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
public class CharredNetherBricks {
    private static final String CHARRED_NETHER_BRICKS_ID = "charred_nether_bricks";
    private static final String NETHER_BRICKS = "minecraft:nether_bricks";

    @Register(Block.class)
    @Recipe.Shapeless(id = CHARRED_NETHER_BRICKS_ID, ingredients = {NETHER_BRICKS, NETHER_BRICKS, NETHER_BRICKS, "minecraft:fire_charge"}, count = 3)
    @BlockState(id = CHARRED_NETHER_BRICKS_ID, variants = @BlockState.Variant(id = "", model = @BlockState.Model(CHARRED_NETHER_BRICKS_ID)))
    @Model(id = CHARRED_NETHER_BRICKS_ID, parent = "minecraft:cube_all", textures = @Texture(key = "all", id = CHARRED_NETHER_BRICKS_ID))
    @GenerateItem(CHARRED_NETHER_BRICKS_ID)
    @GenerateStairs(id = CHARRED_NETHER_BRICKS_ID, texture = CHARRED_NETHER_BRICKS_ID)
    @GenerateSlabs(id = CHARRED_NETHER_BRICKS_ID, texture = CHARRED_NETHER_BRICKS_ID)
    @GenerateWall(id = CHARRED_NETHER_BRICKS_ID, texture = CHARRED_NETHER_BRICKS_ID)
    public static final Block CHARRED_NETHER_BRICKS = new Block(Block.Properties.from(Blocks.NETHER_BRICKS)).setRegistryName(CHARRED_NETHER_BRICKS_ID);
}
