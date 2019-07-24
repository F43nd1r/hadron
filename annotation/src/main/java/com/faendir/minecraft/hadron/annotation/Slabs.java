package com.faendir.minecraft.hadron.annotation;

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
@GenerateItem("{id}")
@Recipe.Shaped(
        pattern = {"   ","   ","xxx"},
        keys = @Recipe.Key(key = "x", value = "{material}"),
        id = "{id}",
        count = 6
)
@BlockState(
        id = "{id}",
        variants = {@BlockState.Variant(id = "type=bottom", model = @BlockState.Model("{id}")),
                @BlockState.Variant(id = "type=top", model = @BlockState.Model(value = "{id}", x = 180, uvlock = true)),
                @BlockState.Variant(id = "type=double", model = @BlockState.Model(value = "{doubleModel}"))}
)
@Model(
        id = "{id}",
        parent = "minecraft:slab",
        textures = {@Model.Texture(key = "top", id = "{texture}"), @Model.Texture(key = "bottom", id = "{texture}"), @Model.Texture(key = "side", id = "{texture}")}
)
@Tag(
        id = "{id}",
        tag = "slabs"
)
@Composite
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface Slabs {
    String id();
    String texture();
    String material();
    String doubleModel();
}
