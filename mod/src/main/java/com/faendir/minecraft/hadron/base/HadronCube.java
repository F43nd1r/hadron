package com.faendir.minecraft.hadron.base;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.Composite;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.GenerateWall;
import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Register;
import com.faendir.minecraft.hadron.annotation.Texture;
import net.minecraft.block.Block;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lukas
 * @since 25.07.19
 */
@Register(Block.class)
@BlockState(id = "{id}", variants = @BlockState.Variant(id = "", model = @BlockState.Model("{id}")))
@Model(id = "{id}", parent = "minecraft:cube_all", textures = @Texture(key = "all", id = "{texture}"))
@GenerateItem(value = "{id}", configPath = "{configPath}")
@Composite
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface HadronCube {
    String id();

    String texture();

    String configPath() default "";

    @HadronCube(id = "{id}", texture = "{texture}", configPath = "{configPath}")
    @GenerateStairs(id = "{id}", texture = "{texture}", configPath = "{configPath}")
    @GenerateSlabs(id = "{id}", texture = "{texture}", configPath = "{configPath}")
    @Composite
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
    @interface WithStairsAndSlabs {
        String id();

        String texture();

        String configPath() default "";
    }


    @HadronCube.WithStairsAndSlabs(id = "{id}", texture = "{texture}", configPath = "{configPath}")
    @GenerateWall(id = "{id}", texture = "{texture}", configPath = "{configPath}")
    @Composite
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
    @interface WithStairsSlabsAndWalls {
        String id();

        String texture();

        String configPath() default "";
    }
}
