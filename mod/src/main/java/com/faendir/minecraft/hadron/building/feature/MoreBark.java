package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.annotation.Composite;
import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.GenerateWall;
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
public class MoreBark {
    @Bark(id = "minecraft:oak_wood", texture = "minecraft:oak_log")
    public static final Block OAK_BARK = Blocks.OAK_WOOD;

    @Bark(id = "minecraft:spruce_wood", texture = "minecraft:spruce_log")
    public static final Block SPRUCE_BARK = Blocks.SPRUCE_WOOD;

    @Bark(id = "minecraft:birch_wood", texture = "minecraft:birch_log")
    public static final Block BIRCH_BARK = Blocks.BIRCH_WOOD;

    @Bark(id = "minecraft:jungle_wood", texture = "minecraft:jungle_log")
    public static final Block JUNGLE_BARK = Blocks.JUNGLE_WOOD;

    @Bark(id = "minecraft:acacia_wood", texture = "minecraft:acacia_log")
    public static final Block ACACIA_BARK = Blocks.ACACIA_WOOD;

    @Bark(id = "minecraft:dark_oak_wood", texture = "minecraft:dark_oak_log")
    public static final Block DARK_OAK_BARK = Blocks.DARK_OAK_WOOD;

    @GenerateStairs(id = "{id}", texture = "{texture}")
    @GenerateSlabs(id = "{id}", texture = "{texture}")
    @GenerateWall(id = "{id}", texture = "{texture}")
    @Composite
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
    public @interface Bark {
        String id();

        String texture();
    }
}
