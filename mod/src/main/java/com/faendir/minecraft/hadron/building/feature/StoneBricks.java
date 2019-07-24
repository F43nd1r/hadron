package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.Composite;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.annotation.Register;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lukas
 * @since 22.07.19
 */
public class StoneBricks {
    private static final String ANDESITE_BRICKS_ID = "andesite_bricks";
    @Brick(id = ANDESITE_BRICKS_ID, texture = ANDESITE_BRICKS_ID, material = "minecraft:polished_andesite")
    public static final Block ANDESITE_BRICKS = new Block(Block.Properties.from(Blocks.POLISHED_ANDESITE)).setRegistryName(ANDESITE_BRICKS_ID);

    private static final String DIORITE_BRICKS_ID = "diorite_bricks";
    @Brick(id = DIORITE_BRICKS_ID, texture = DIORITE_BRICKS_ID, material = "minecraft:polished_diorite")
    public static final Block DIORITE_BRICKS = new Block(Block.Properties.from(Blocks.POLISHED_DIORITE)).setRegistryName(DIORITE_BRICKS_ID);

    private static final String GRANITE_BRICKS_ID = "granite_bricks";
    @Brick(id = GRANITE_BRICKS_ID, texture = GRANITE_BRICKS_ID, material = "minecraft:polished_granite")
    public static final Block GRANITE_BRICKS = new Block(Block.Properties.from(Blocks.POLISHED_GRANITE)).setRegistryName(GRANITE_BRICKS_ID);

    @Register(Block.class)
    @Recipe.Shaped(id = "{id}", pattern = {"xx", "xx"}, keys = @Recipe.Key(key = "x", value = "{material}"), count = 4)
    @BlockState(id = "{id}", variants = @BlockState.Variant(id = "", model = @BlockState.Model("{id}")))
    @Model(id = "{id}", parent = "minecraft:cube_all", textures = @Model.Texture(key = "all", id = "{texture}"))
    @GenerateItem("{id}")
    @GenerateStairs(id = "{id}", texture = "{texture}")
    @GenerateSlabs(id = "{id}", texture = "{texture}")
    @Composite
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
    public @interface Brick {
        String id();

        String texture();

        String material();
    }
}
