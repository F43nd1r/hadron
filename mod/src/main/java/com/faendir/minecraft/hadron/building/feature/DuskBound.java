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
public class DuskBound {

    private static final String DUSKBOUND_ID = "duskbound";
    @Register(Block.class)
    @Recipe.Shaped(id = DUSKBOUND_ID, pattern = {"xxx", "xox", "xxx"}, keys = {
            @Recipe.Key(key = "x", value = "minecraft:obsidian"),
            @Recipe.Key(key = "o", value = "minecraft:purpur_block")
    }, count = 16)
    @BlockState(id = DUSKBOUND_ID, variants = @BlockState.Variant(id = "", model = @BlockState.Model(DUSKBOUND_ID)))
    @Model(id = DUSKBOUND_ID, parent = "minecraft:cube_all", textures = @Texture(key = "all", id = DUSKBOUND_ID))
    @GenerateItem(DUSKBOUND_ID)
    @GenerateStairs(id = DUSKBOUND_ID, texture = DUSKBOUND_ID)
    @GenerateSlabs(id = DUSKBOUND_ID, texture = DUSKBOUND_ID)
    @GenerateWall(id = DUSKBOUND_ID, texture = DUSKBOUND_ID)
    public static final Block DUSKBOUND = new Block(Block.Properties.from(Blocks.STONE_BRICKS)).setRegistryName(DUSKBOUND_ID);

    private static final String DUSKBOUND_LANTERN_ID = "duskbound_lantern";
    @Register(Block.class)
    @Recipe.Shaped(id = DUSKBOUND_LANTERN_ID, pattern = {"xxx", "xox", "xxx"}, keys = {
            @Recipe.Key(key = "x", value = DUSKBOUND_ID),
            @Recipe.Key(key = "o", value = "minecraft:ender_pearl")
    }, count = 4)
    @BlockState(id = DUSKBOUND_LANTERN_ID, variants = @BlockState.Variant(id = "", model = @BlockState.Model(DUSKBOUND_LANTERN_ID)))
    @Model(id = DUSKBOUND_LANTERN_ID, parent = "minecraft:cube_all", textures = @Texture(key = "all", id = DUSKBOUND_LANTERN_ID))
    @GenerateItem(DUSKBOUND_LANTERN_ID)
    public static final Block DUSKBOUND_LANTERN = new Block(Block.Properties.from(DUSKBOUND).lightValue(15)).setRegistryName(DUSKBOUND_LANTERN_ID);

}
