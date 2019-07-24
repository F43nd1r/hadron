package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.Composite;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.annotation.Texture;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lukas
 * @since 24.07.19
 */
public class CarvedWoods {

    private static final String CARVED_OAK_WOOD_ID = "carved_oak_wood";
    @CarvedWood(id = CARVED_OAK_WOOD_ID, texture = CARVED_OAK_WOOD_ID, material = "minecraft:oak_slab")
    public static final Block CARVED_OAK_WOOD = new Block(Block.Properties.from(Blocks.OAK_PLANKS)).setRegistryName(CARVED_OAK_WOOD_ID);

    private static final String CARVED_ACACIA_WOOD_ID = "carved_acacia_wood";
    @CarvedWood(id = CARVED_ACACIA_WOOD_ID, texture = CARVED_ACACIA_WOOD_ID, material = "minecraft:acacia_slab")
    public static final Block CARVED_ACACIA_WOOD = new Block(Block.Properties.from(Blocks.ACACIA_PLANKS)).setRegistryName(CARVED_ACACIA_WOOD_ID);

    private static final String CARVED_BIRCH_WOOD_ID = "carved_birch_wood";
    @CarvedWood(id = CARVED_BIRCH_WOOD_ID, texture = CARVED_BIRCH_WOOD_ID, material = "minecraft:birch_slab")
    public static final Block CARVED_BIRCH_WOOD = new Block(Block.Properties.from(Blocks.BIRCH_PLANKS)).setRegistryName(CARVED_BIRCH_WOOD_ID);

    private static final String CARVED_DARK_OAK_WOOD_ID = "carved_dark_oak_wood";
    @CarvedWood(id = CARVED_DARK_OAK_WOOD_ID, texture = CARVED_DARK_OAK_WOOD_ID, material = "minecraft:dark_oak_slab")
    public static final Block CARVED_DARK_OAK_WOOD = new Block(Block.Properties.from(Blocks.DARK_OAK_PLANKS)).setRegistryName(CARVED_DARK_OAK_WOOD_ID);

    private static final String CARVED_JUNGLE_WOOD_ID = "carved_jungle_wood";
    @CarvedWood(id = CARVED_JUNGLE_WOOD_ID, texture = CARVED_JUNGLE_WOOD_ID, material = "minecraft:jungle_slab")
    public static final Block CARVED_JUNGLE_WOOD = new Block(Block.Properties.from(Blocks.JUNGLE_PLANKS)).setRegistryName(CARVED_JUNGLE_WOOD_ID);

    private static final String CARVED_SPRUCE_WOOD_ID = "carved_spruce_wood";
    @CarvedWood(id = CARVED_SPRUCE_WOOD_ID, texture = CARVED_SPRUCE_WOOD_ID, material = "minecraft:spruce_slab")
    public static final Block CARVED_SPRUCE_WOOD = new Block(Block.Properties.from(Blocks.SPRUCE_PLANKS)).setRegistryName(CARVED_SPRUCE_WOOD_ID);



    @Register(Block.class)
    @Recipe.Shaped(id = "{id}", pattern = {"xx", "xx"}, keys = @Recipe.Key(key = "x", value = "{material}"), count = 4)
    @BlockState(id = "{id}", variants = @BlockState.Variant(id = "", model = @BlockState.Model("{id}")))
    @Model(id = "{id}", parent = "minecraft:cube_all", textures = @Texture(key = "all", id = "{texture}"))
    @GenerateItem("{id}")
    @Composite
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
    public @interface CarvedWood {
        String id();

        String texture();

        String material();
    }
}
