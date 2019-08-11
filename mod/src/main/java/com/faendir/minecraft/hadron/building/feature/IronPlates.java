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
public class IronPlates {
    private static final String CONFIG_PATH = Hadron.Module.BUILDING + ".IronPlates";
    private static final String IRON_PLATES_ID = "iron_plates";
    @HadronCube.WithStairsAndSlabs(id = IRON_PLATES_ID, texture = IRON_PLATES_ID, configPath = CONFIG_PATH)
    @Recipe.Shaped(id = IRON_PLATES_ID, pattern = {"xxx", "x x", "xxx"}, keys = @Recipe.Key(key = "x", value = "minecraft:iron_ingot"), count = 24, configPath = CONFIG_PATH)
    public static final Block IRON_PLATES = new net.minecraft.block.Block(Block.Properties.from(Blocks.IRON_BLOCK)).setRegistryName(IRON_PLATES_ID);

    private static final String RUSTY_IRON_PLATES_ID = "rusty_iron_plates";
    @HadronCube.WithStairsAndSlabs(id = RUSTY_IRON_PLATES_ID, texture = RUSTY_IRON_PLATES_ID, configPath = CONFIG_PATH)
    @Recipe.Shaped(id = RUSTY_IRON_PLATES_ID, pattern = {"xxx", "xbx", "xxx"}, keys = {
            @Recipe.Key(key = "x", value = "minecraft:iron_ingot"),
            @Recipe.Key(key = "b", value = "minecraft:water_bucket")
    }, count = 24, configPath = CONFIG_PATH)
    @Recipe.Shaped(id = RUSTY_IRON_PLATES_ID, pattern = {"xxx", "xbx", "xxx"}, keys = {
            @Recipe.Key(key = "x", value = IRON_PLATES_ID),
            @Recipe.Key(key = "b", value = "minecraft:water_bucket")
    }, count = 8, configPath = CONFIG_PATH)
    public static final Block RUSTY_IRON_PLATES = new net.minecraft.block.Block(Block.Properties.from(Blocks.IRON_BLOCK)).setRegistryName(RUSTY_IRON_PLATES_ID);


}
