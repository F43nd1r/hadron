package com.faendir.minecraft.hadron.building.feature;

import com.faendir.minecraft.hadron.Hadron;
import com.faendir.minecraft.hadron.annotation.Composite;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.base.HadronCube;
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
public class TerracottaShingles {

    private static final String TERRACOTTA_SHINGLES_ID = "terracotta_shingles";
    @Shingles(id = TERRACOTTA_SHINGLES_ID, texture = TERRACOTTA_SHINGLES_ID, material = "minecraft:terracotta")
    public static final Block TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.TERRACOTTA)).setRegistryName(TERRACOTTA_SHINGLES_ID);

    private static final String WHITE_TERRACOTTA_SHINGLES_ID = "white_terracotta_shingles";
    @Shingles(id = WHITE_TERRACOTTA_SHINGLES_ID, texture = WHITE_TERRACOTTA_SHINGLES_ID, material = "minecraft:white_terracotta")
    public static final Block WHITE_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.WHITE_TERRACOTTA)).setRegistryName(WHITE_TERRACOTTA_SHINGLES_ID);

    private static final String ORANGE_TERRACOTTA_SHINGLES_ID = "orange_terracotta_shingles";
    @Shingles(id = ORANGE_TERRACOTTA_SHINGLES_ID, texture = ORANGE_TERRACOTTA_SHINGLES_ID, material = "minecraft:orange_terracotta")
    public static final Block ORANGE_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.ORANGE_TERRACOTTA)).setRegistryName(ORANGE_TERRACOTTA_SHINGLES_ID);

    private static final String MAGENTA_TERRACOTTA_SHINGLES_ID = "magenta_terracotta_shingles";
    @Shingles(id = MAGENTA_TERRACOTTA_SHINGLES_ID, texture = MAGENTA_TERRACOTTA_SHINGLES_ID, material = "minecraft:magenta_terracotta")
    public static final Block MAGENTA_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.MAGENTA_TERRACOTTA)).setRegistryName(MAGENTA_TERRACOTTA_SHINGLES_ID);

    private static final String LIGHT_BLUE_TERRACOTTA_SHINGLES_ID = "light_blue_terracotta_shingles";
    @Shingles(id = LIGHT_BLUE_TERRACOTTA_SHINGLES_ID, texture = LIGHT_BLUE_TERRACOTTA_SHINGLES_ID, material = "minecraft:light_blue_terracotta")
    public static final Block LIGHT_BLUE_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.LIGHT_BLUE_TERRACOTTA)).setRegistryName(LIGHT_BLUE_TERRACOTTA_SHINGLES_ID);

    private static final String YELLOW_TERRACOTTA_SHINGLES_ID = "yellow_terracotta_shingles";
    @Shingles(id = YELLOW_TERRACOTTA_SHINGLES_ID, texture = YELLOW_TERRACOTTA_SHINGLES_ID, material = "minecraft:yellow_terracotta")
    public static final Block YELLOW_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.YELLOW_TERRACOTTA)).setRegistryName(YELLOW_TERRACOTTA_SHINGLES_ID);

    private static final String LIME_TERRACOTTA_SHINGLES_ID = "lime_terracotta_shingles";
    @Shingles(id = LIME_TERRACOTTA_SHINGLES_ID, texture = LIME_TERRACOTTA_SHINGLES_ID, material = "minecraft:lime_terracotta")
    public static final Block LIME_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.LIME_TERRACOTTA)).setRegistryName(LIME_TERRACOTTA_SHINGLES_ID);

    private static final String PINK_TERRACOTTA_SHINGLES_ID = "pink_terracotta_shingles";
    @Shingles(id = PINK_TERRACOTTA_SHINGLES_ID, texture = PINK_TERRACOTTA_SHINGLES_ID, material = "minecraft:pink_terracotta")
    public static final Block PINK_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.PINK_TERRACOTTA)).setRegistryName(PINK_TERRACOTTA_SHINGLES_ID);

    private static final String GRAY_TERRACOTTA_SHINGLES_ID = "gray_terracotta_shingles";
    @Shingles(id = GRAY_TERRACOTTA_SHINGLES_ID, texture = GRAY_TERRACOTTA_SHINGLES_ID, material = "minecraft:gray_terracotta")
    public static final Block GRAY_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.GRAY_TERRACOTTA)).setRegistryName(GRAY_TERRACOTTA_SHINGLES_ID);

    private static final String LIGHT_GRAY_TERRACOTTA_SHINGLES_ID = "light_gray_terracotta_shingles";
    @Shingles(id = LIGHT_GRAY_TERRACOTTA_SHINGLES_ID, texture = LIGHT_GRAY_TERRACOTTA_SHINGLES_ID, material = "minecraft:light_gray_terracotta")
    public static final Block LIGHT_GRAY_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.LIGHT_GRAY_TERRACOTTA)).setRegistryName(LIGHT_GRAY_TERRACOTTA_SHINGLES_ID);

    private static final String CYAN_TERRACOTTA_SHINGLES_ID = "cyan_terracotta_shingles";
    @Shingles(id = CYAN_TERRACOTTA_SHINGLES_ID, texture = CYAN_TERRACOTTA_SHINGLES_ID, material = "minecraft:cyan_terracotta")
    public static final Block CYAN_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.CYAN_TERRACOTTA)).setRegistryName(CYAN_TERRACOTTA_SHINGLES_ID);

    private static final String PURPLE_TERRACOTTA_SHINGLES_ID = "purple_terracotta_shingles";
    @Shingles(id = PURPLE_TERRACOTTA_SHINGLES_ID, texture = PURPLE_TERRACOTTA_SHINGLES_ID, material = "minecraft:purple_terracotta")
    public static final Block PURPLE_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.PURPLE_TERRACOTTA)).setRegistryName(PURPLE_TERRACOTTA_SHINGLES_ID);

    private static final String BLUE_TERRACOTTA_SHINGLES_ID = "blue_terracotta_shingles";
    @Shingles(id = BLUE_TERRACOTTA_SHINGLES_ID, texture = BLUE_TERRACOTTA_SHINGLES_ID, material = "minecraft:blue_terracotta")
    public static final Block BLUE_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.BLUE_TERRACOTTA)).setRegistryName(BLUE_TERRACOTTA_SHINGLES_ID);

    private static final String BROWN_TERRACOTTA_SHINGLES_ID = "brown_terracotta_shingles";
    @Shingles(id = BROWN_TERRACOTTA_SHINGLES_ID, texture = BROWN_TERRACOTTA_SHINGLES_ID, material = "minecraft:brown_terracotta")
    public static final Block BROWN_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.BROWN_TERRACOTTA)).setRegistryName(BROWN_TERRACOTTA_SHINGLES_ID);

    private static final String GREEN_TERRACOTTA_SHINGLES_ID = "green_terracotta_shingles";
    @Shingles(id = GREEN_TERRACOTTA_SHINGLES_ID, texture = GREEN_TERRACOTTA_SHINGLES_ID, material = "minecraft:green_terracotta")
    public static final Block GREEN_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.GREEN_TERRACOTTA)).setRegistryName(GREEN_TERRACOTTA_SHINGLES_ID);

    private static final String RED_TERRACOTTA_SHINGLES_ID = "red_terracotta_shingles";
    @Shingles(id = RED_TERRACOTTA_SHINGLES_ID, texture = RED_TERRACOTTA_SHINGLES_ID, material = "minecraft:red_terracotta")
    public static final Block RED_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.RED_TERRACOTTA)).setRegistryName(RED_TERRACOTTA_SHINGLES_ID);

    private static final String BLACK_TERRACOTTA_SHINGLES_ID = "black_terracotta_shingles";
    @Shingles(id = BLACK_TERRACOTTA_SHINGLES_ID, texture = BLACK_TERRACOTTA_SHINGLES_ID, material = "minecraft:black_terracotta")
    public static final Block BLACK_TERRACOTTA_SHINGLES = new Block(Block.Properties.from(Blocks.BLACK_TERRACOTTA)).setRegistryName(BLACK_TERRACOTTA_SHINGLES_ID);

    private static final String CONFIG_PATH = Hadron.Module.BUILDING + ".NetherWrought";

    @HadronCube.WithStairsAndSlabs(id = "{id}", texture = "{texture}", configPath = CONFIG_PATH)
    @Recipe.Shaped(id = "{id}", pattern = {"xx", "xx"}, keys = @Recipe.Key(key = "x", value = "{material}"), count = 4, configPath = CONFIG_PATH)
    @Composite
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
    public @interface Shingles {
        String id();

        String texture();

        String material();
    }
}
