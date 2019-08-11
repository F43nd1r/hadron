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
public class SandyBricks {
    private static final String CONFIG_PATH = Hadron.Module.BUILDING + ".SandyBricks";
    private static final String SANDY_BRICKS_ID = "sandy_bricks";
    @HadronCube.WithStairsAndSlabs(id = SANDY_BRICKS_ID, texture = SANDY_BRICKS_ID, configPath = CONFIG_PATH)
    @Recipe.Shapeless(id = SANDY_BRICKS_ID, ingredients = {"minecraft:bricks", "minecraft:sand"}, configPath = CONFIG_PATH)
    public static final Block SANDY_BRICKS = new Block(Block.Properties.from(Blocks.BRICKS)).setRegistryName(SANDY_BRICKS_ID);
}
