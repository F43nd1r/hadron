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
@GenerateItem(
        value = "{id}",
        parent = "{id}_inventory"
)
@Recipe.Shaped(
        pattern = {"   ","xxx","xxx"},
        keys = @Recipe.Key(key = "x", value = "{material}"),
        id = "{id}",
        count = 6
)
@BlockState(
        id = "{id}",
        multipart = {@BlockState.Multipart(when = @BlockState.Conditions({@BlockState.Condition(name = "up", value = "true")}), apply = @BlockState.Model("{id}_post")),
                @BlockState.Multipart(when = @BlockState.Conditions({@BlockState.Condition(name = "north", value = "true")}), apply = @BlockState.Model(value = "{id}_side", y = 0, uvlock = true)),
                @BlockState.Multipart(when = @BlockState.Conditions({@BlockState.Condition(name = "east", value = "true")}), apply = @BlockState.Model(value = "{id}_side", y = 90, uvlock = true)),
                @BlockState.Multipart(when = @BlockState.Conditions({@BlockState.Condition(name = "south", value = "true")}), apply = @BlockState.Model(value = "{id}_side", y = 180, uvlock = true)),
                @BlockState.Multipart(when = @BlockState.Conditions({@BlockState.Condition(name = "west", value = "true")}), apply = @BlockState.Model(value = "{id}_side", y = 270, uvlock = true))}
)
@Model(
        id = "{id}_inventory",
        parent = "minecraft:wall_inventory",
        textures = @Model.Texture(key = "wall", id = "{texture}")
)
@Model(
        id = "{id}_post",
        parent = "minecraft:template_wall_post",
        textures = @Model.Texture(key = "wall", id = "{texture}")
)
@Model(
        id = "{id}_side",
        parent = "minecraft:template_wall_side",
        textures = @Model.Texture(key = "wall", id = "{texture}")
)
@Tag(
        id = "{id}",
        tag = "walls"
)
@Composite
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface Wall {
    String id();
    String texture();
    String material();
}
