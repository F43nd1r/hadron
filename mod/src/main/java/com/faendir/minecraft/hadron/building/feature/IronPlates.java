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
public class IronPlates {
    private static final String IRON_PLATES_ID = "iron_plates";
    @Register(Block.class)
    @Recipe.Shaped(id = IRON_PLATES_ID, pattern = {"xxx", "x x", "xxx"}, keys = @Recipe.Key(key = "x", value = "minecraft:iron_ingot"), count = 24)
    @BlockState(id = IRON_PLATES_ID, variants = @BlockState.Variant(id = "", model = @BlockState.Model(IRON_PLATES_ID)))
    @Model(id = IRON_PLATES_ID, parent = "minecraft:cube_all", textures = @Texture(key = "all", id = IRON_PLATES_ID))
    @GenerateItem(IRON_PLATES_ID)
    @GenerateStairs(id = IRON_PLATES_ID, texture = IRON_PLATES_ID)
    @GenerateSlabs(id = IRON_PLATES_ID, texture = IRON_PLATES_ID)
    public static final Block IRON_PLATES = new net.minecraft.block.Block(Block.Properties.from(Blocks.IRON_BLOCK)).setRegistryName(IRON_PLATES_ID);

    private static final String RUSTY_IRON_PLATES_ID = "rusty_iron_plates";
    @Register(Block.class)
    @Recipe.Shaped(id = RUSTY_IRON_PLATES_ID, pattern = {"xxx", "xbx", "xxx"}, keys = {
            @Recipe.Key(key = "x", value = "minecraft:iron_ingot"),
            @Recipe.Key(key = "b", value = "minecraft:water_bucket")
    }, count = 24)
    @Recipe.Shaped(id = RUSTY_IRON_PLATES_ID, pattern = {"xxx", "xbx", "xxx"}, keys = {
            @Recipe.Key(key = "x", value = IRON_PLATES_ID),
            @Recipe.Key(key = "b", value = "minecraft:water_bucket")
    }, count = 8)
    @BlockState(id = RUSTY_IRON_PLATES_ID, variants = @BlockState.Variant(id = "", model = @BlockState.Model(RUSTY_IRON_PLATES_ID)))
    @Model(id = RUSTY_IRON_PLATES_ID, parent = "minecraft:cube_all", textures = @Texture(key = "all", id = RUSTY_IRON_PLATES_ID))
    @GenerateItem(RUSTY_IRON_PLATES_ID)
    @GenerateStairs(id = RUSTY_IRON_PLATES_ID, texture = RUSTY_IRON_PLATES_ID)
    @GenerateSlabs(id = RUSTY_IRON_PLATES_ID, texture = RUSTY_IRON_PLATES_ID)
    public static final Block RUSTY_IRON_PLATES = new net.minecraft.block.Block(Block.Properties.from(Blocks.IRON_BLOCK)).setRegistryName(RUSTY_IRON_PLATES_ID);


}
