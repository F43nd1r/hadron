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
public class QuiltedWool {
    private static final String WHITE_QUILTED_WOOL_ID = "white_quilted_wool";
    @Quilted(id = WHITE_QUILTED_WOOL_ID, texture = WHITE_QUILTED_WOOL_ID, material = "minecraft:white_wool")
    public static final Block WHITE_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.WHITE_WOOL)).setRegistryName(WHITE_QUILTED_WOOL_ID);

    private static final String ORANGE_QUILTED_WOOL_ID = "orange_quilted_wool";
    @Quilted(id = ORANGE_QUILTED_WOOL_ID, texture = ORANGE_QUILTED_WOOL_ID, material = "minecraft:orange_wool")
    public static final Block ORANGE_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.ORANGE_WOOL)).setRegistryName(ORANGE_QUILTED_WOOL_ID);

    private static final String MAGENTA_QUILTED_WOOL_ID = "magenta_quilted_wool";
    @Quilted(id = MAGENTA_QUILTED_WOOL_ID, texture = MAGENTA_QUILTED_WOOL_ID, material = "minecraft:magenta_wool")
    public static final Block MAGENTA_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.MAGENTA_WOOL)).setRegistryName(MAGENTA_QUILTED_WOOL_ID);

    private static final String LIGHT_BLUE_QUILTED_WOOL_ID = "light_blue_quilted_wool";
    @Quilted(id = LIGHT_BLUE_QUILTED_WOOL_ID, texture = LIGHT_BLUE_QUILTED_WOOL_ID, material = "minecraft:light_blue_wool")
    public static final Block LIGHT_BLUE_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.LIGHT_BLUE_WOOL)).setRegistryName(LIGHT_BLUE_QUILTED_WOOL_ID);

    private static final String YELLOW_QUILTED_WOOL_ID = "yellow_quilted_wool";
    @Quilted(id = YELLOW_QUILTED_WOOL_ID, texture = YELLOW_QUILTED_WOOL_ID, material = "minecraft:yellow_wool")
    public static final Block YELLOW_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.YELLOW_WOOL)).setRegistryName(YELLOW_QUILTED_WOOL_ID);

    private static final String LIME_QUILTED_WOOL_ID = "lime_quilted_wool";
    @Quilted(id = LIME_QUILTED_WOOL_ID, texture = LIME_QUILTED_WOOL_ID, material = "minecraft:lime_wool")
    public static final Block LIME_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.LIME_WOOL)).setRegistryName(LIME_QUILTED_WOOL_ID);

    private static final String PINK_QUILTED_WOOL_ID = "pink_quilted_wool";
    @Quilted(id = PINK_QUILTED_WOOL_ID, texture = PINK_QUILTED_WOOL_ID, material = "minecraft:pink_wool")
    public static final Block PINK_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.PINK_WOOL)).setRegistryName(PINK_QUILTED_WOOL_ID);

    private static final String GRAY_QUILTED_WOOL_ID = "gray_quilted_wool";
    @Quilted(id = GRAY_QUILTED_WOOL_ID, texture = GRAY_QUILTED_WOOL_ID, material = "minecraft:gray_wool")
    public static final Block GRAY_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.GRAY_WOOL)).setRegistryName(GRAY_QUILTED_WOOL_ID);

    private static final String LIGHT_GRAY_QUILTED_WOOL_ID = "light_gray_quilted_wool";
    @Quilted(id = LIGHT_GRAY_QUILTED_WOOL_ID, texture = LIGHT_GRAY_QUILTED_WOOL_ID, material = "minecraft:light_gray_wool")
    public static final Block LIGHT_GRAY_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.LIGHT_GRAY_WOOL)).setRegistryName(LIGHT_GRAY_QUILTED_WOOL_ID);

    private static final String CYAN_QUILTED_WOOL_ID = "cyan_quilted_wool";
    @Quilted(id = CYAN_QUILTED_WOOL_ID, texture = CYAN_QUILTED_WOOL_ID, material = "minecraft:cyan_wool")
    public static final Block CYAN_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.CYAN_WOOL)).setRegistryName(CYAN_QUILTED_WOOL_ID);

    private static final String PURPLE_QUILTED_WOOL_ID = "purple_quilted_wool";
    @Quilted(id = PURPLE_QUILTED_WOOL_ID, texture = PURPLE_QUILTED_WOOL_ID, material = "minecraft:purple_wool")
    public static final Block PURPLE_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.PURPLE_WOOL)).setRegistryName(PURPLE_QUILTED_WOOL_ID);

    private static final String BLUE_QUILTED_WOOL_ID = "blue_quilted_wool";
    @Quilted(id = BLUE_QUILTED_WOOL_ID, texture = BLUE_QUILTED_WOOL_ID, material = "minecraft:blue_wool")
    public static final Block BLUE_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.BLUE_WOOL)).setRegistryName(BLUE_QUILTED_WOOL_ID);

    private static final String BROWN_QUILTED_WOOL_ID = "brown_quilted_wool";
    @Quilted(id = BROWN_QUILTED_WOOL_ID, texture = BROWN_QUILTED_WOOL_ID, material = "minecraft:brown_wool")
    public static final Block BROWN_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.BROWN_WOOL)).setRegistryName(BROWN_QUILTED_WOOL_ID);

    private static final String GREEN_QUILTED_WOOL_ID = "green_quilted_wool";
    @Quilted(id = GREEN_QUILTED_WOOL_ID, texture = GREEN_QUILTED_WOOL_ID, material = "minecraft:green_wool")
    public static final Block GREEN_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.GREEN_WOOL)).setRegistryName(GREEN_QUILTED_WOOL_ID);

    private static final String RED_QUILTED_WOOL_ID = "red_quilted_wool";
    @Quilted(id = RED_QUILTED_WOOL_ID, texture = RED_QUILTED_WOOL_ID, material = "minecraft:red_wool")
    public static final Block RED_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.RED_WOOL)).setRegistryName(RED_QUILTED_WOOL_ID);

    private static final String BLACK_QUILTED_WOOL_ID = "black_quilted_wool";
    @Quilted(id = BLACK_QUILTED_WOOL_ID, texture = BLACK_QUILTED_WOOL_ID, material = "minecraft:black_wool")
    public static final Block BLACK_QUILTED_WOOL = new Block(Block.Properties.from(Blocks.BLACK_WOOL)).setRegistryName(BLACK_QUILTED_WOOL_ID);


    private static final String CONFIG_PATH = Hadron.Module.BUILDING + ".QuiltedWool";
    @HadronCube(id = "{id}", texture = "{texture}", configPath = CONFIG_PATH)
    @Recipe.Shaped(id = "{id}", pattern = {"s", "xxx", "s"}, keys = {
            @Recipe.Key(key = "x", value = "{material}"),
            @Recipe.Key(key = "s", value = "minecraft:string")
    }, count = 4, configPath = CONFIG_PATH)
    @Composite
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
    public @interface Quilted {
        String id();

        String texture();

        String material();
    }
}
