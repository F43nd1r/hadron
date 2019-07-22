package com.faendir.minecraft.hadron.building.block;

import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.Composite;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.annotation.Register;
import net.minecraft.block.Block;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lukas
 * @since 22.07.19
 */
@Register(Block.class)
@Recipe(pattern = {"xx", "xx"}, keys = @Recipe.Key(key = "x", value = "{material}"), id = "{id}", count = 4)
@BlockState(id = "{id}", variants = @BlockState.Variant(id = "", model = @BlockState.Model("hadron:block/{id}")))
@Model(id = "{id}", parent = "minecraft:block/cube_all", textures = @Model.Texture(key = "all", id = "{texture}"))
@GenerateItem("{id}")
@GenerateStairs(id = "{id}", texture = "{texture}")
@GenerateSlabs(id = "{id}", texture = "{texture}")
@Composite
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Brick {
    String id();
    String texture();
    String material();
}
