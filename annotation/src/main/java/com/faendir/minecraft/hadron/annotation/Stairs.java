package com.faendir.minecraft.hadron.annotation;

import net.minecraft.block.Block;

/**
 * @author lukas
 * @since 21.07.19
 */
@Register(Block.class)
@GenerateItem("{id}")
@Recipe(
        pattern = {"x  ","xx ","xxx"},
        keys = @Recipe.Key(key = "x", value = "{material}"),
        id = "{id}",
        count = 4
)
@BlockState(
        id = "{id}",
        variants = {@BlockState.Variant(id = "facing=east,half=bottom,shape=straight", model = @BlockState.Model("hadron:block/{id}")),
                @BlockState.Variant(id = "facing=west,half=bottom,shape=straight", model = @BlockState.Model(value = "hadron:block/{id}", y = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=south,half=bottom,shape=straight", model = @BlockState.Model(value = "hadron:block/{id}", y = 90, uvlock = true)),
                @BlockState.Variant(id = "facing=north,half=bottom,shape=straight", model = @BlockState.Model(value = "hadron:block/{id}", y = 270, uvlock = true)),
                @BlockState.Variant(id = "facing=east,half=bottom,shape=outer_right", model = @BlockState.Model("hadron:block/{id}_outer")),
                @BlockState.Variant(id = "facing=west,half=bottom,shape=outer_right", model = @BlockState.Model(value = "hadron:block/{id}_outer", y = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=south,half=bottom,shape=outer_right", model = @BlockState.Model(value = "hadron:block/{id}_outer", y = 90, uvlock = true)),
                @BlockState.Variant(id = "facing=north,half=bottom,shape=outer_right", model = @BlockState.Model(value = "hadron:block/{id}_outer", y = 270, uvlock = true)),
                @BlockState.Variant(id = "facing=east,half=bottom,shape=outer_left", model = @BlockState.Model(value = "hadron:block/{id}_outer", y = 270, uvlock = true)),
                @BlockState.Variant(id = "facing=west,half=bottom,shape=outer_left", model = @BlockState.Model(value = "hadron:block/{id}_outer", y = 90, uvlock = true)),
                @BlockState.Variant(id = "facing=south,half=bottom,shape=outer_left", model = @BlockState.Model("hadron:block/{id}_outer")),
                @BlockState.Variant(id = "facing=north,half=bottom,shape=outer_left", model = @BlockState.Model(value = "hadron:block/{id}_outer", y = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=east,half=bottom,shape=inner_right", model = @BlockState.Model("hadron:block/{id}_inner")),
                @BlockState.Variant(id = "facing=west,half=bottom,shape=inner_right", model = @BlockState.Model(value = "hadron:block/{id}_inner", y = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=south,half=bottom,shape=inner_right", model = @BlockState.Model(value = "hadron:block/{id}_inner", y = 90, uvlock = true)),
                @BlockState.Variant(id = "facing=north,half=bottom,shape=inner_right", model = @BlockState.Model(value = "hadron:block/{id}_inner", y = 270, uvlock = true)),
                @BlockState.Variant(id = "facing=east,half=bottom,shape=inner_left", model = @BlockState.Model(value = "hadron:block/{id}_inner", y = 270, uvlock = true)),
                @BlockState.Variant(id = "facing=west,half=bottom,shape=inner_left", model = @BlockState.Model(value = "hadron:block/{id}_inner", y = 90, uvlock = true)),
                @BlockState.Variant(id = "facing=south,half=bottom,shape=inner_left", model = @BlockState.Model("hadron:block/{id}_inner")),
                @BlockState.Variant(id = "facing=north,half=bottom,shape=inner_left", model = @BlockState.Model(value = "hadron:block/{id}_inner", y = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=east,half=top,shape=straight", model = @BlockState.Model(value = "hadron:block/{id}", x = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=west,half=top,shape=straight", model = @BlockState.Model(value = "hadron:block/{id}", x = 180, y = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=south,half=top,shape=straight", model = @BlockState.Model(value = "hadron:block/{id}", x = 180, y = 90, uvlock = true)),
                @BlockState.Variant(id = "facing=north,half=top,shape=straight", model = @BlockState.Model(value = "hadron:block/{id}", x = 180, y = 270, uvlock = true)),
                @BlockState.Variant(id = "facing=east,half=top,shape=outer_right", model = @BlockState.Model(value = "hadron:block/{id}_outer", x = 180, y = 90, uvlock = true)),
                @BlockState.Variant(id = "facing=west,half=top,shape=outer_right", model = @BlockState.Model(value = "hadron:block/{id}_outer", x = 180, y = 270, uvlock = true)),
                @BlockState.Variant(id = "facing=south,half=top,shape=outer_right", model = @BlockState.Model(value = "hadron:block/{id}_outer", x = 180, y = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=north,half=top,shape=outer_right", model = @BlockState.Model(value = "hadron:block/{id}_outer", x = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=east,half=top,shape=outer_left", model = @BlockState.Model(value = "hadron:block/{id}_outer", x = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=west,half=top,shape=outer_left", model = @BlockState.Model(value = "hadron:block/{id}_outer", x = 180, y = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=south,half=top,shape=outer_left", model = @BlockState.Model(value = "hadron:block/{id}_outer", x = 180, y = 90, uvlock = true)),
                @BlockState.Variant(id = "facing=north,half=top,shape=outer_left", model = @BlockState.Model(value = "hadron:block/{id}_outer", x = 180, y = 270, uvlock = true)),
                @BlockState.Variant(id = "facing=east,half=top,shape=inner_right", model = @BlockState.Model(value = "hadron:block/{id}_inner", x = 180, y = 90, uvlock = true)),
                @BlockState.Variant(id = "facing=west,half=top,shape=inner_right", model = @BlockState.Model(value = "hadron:block/{id}_inner", x = 180, y = 270, uvlock = true)),
                @BlockState.Variant(id = "facing=south,half=top,shape=inner_right", model = @BlockState.Model(value = "hadron:block/{id}_inner", x = 180, y = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=north,half=top,shape=inner_right", model = @BlockState.Model(value = "hadron:block/{id}_inner", x = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=east,half=top,shape=inner_left", model = @BlockState.Model(value = "hadron:block/{id}_inner", x = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=west,half=top,shape=inner_left", model = @BlockState.Model(value = "hadron:block/{id}_inner", x = 180, y = 180, uvlock = true)),
                @BlockState.Variant(id = "facing=south,half=top,shape=inner_left", model = @BlockState.Model(value = "hadron:block/{id}_inner", x = 180, y = 90, uvlock = true)),
                @BlockState.Variant(id = "facing=north,half=top,shape=inner_left", model = @BlockState.Model(value = "hadron:block/{id}_inner", x = 180, y = 270, uvlock = true))}
)
@Model(
        id = "{id}",
        parent = "minecraft:block/stairs",
        textures = {@Model.Texture(key = "top", id = "{texture}"), @Model.Texture(key = "bottom", id = "{texture}"), @Model.Texture(key = "side", id = "{texture}")}
)
@Model(
        id = "{id}_outer",
        parent = "minecraft:block/outer_stairs",
        textures = {@Model.Texture(key = "top", id = "{texture}"), @Model.Texture(key = "bottom", id = "{texture}"), @Model.Texture(key = "side", id = "{texture}")}
)
@Model(
        id = "{id}_inner",
        parent = "minecraft:block/inner_stairs",
        textures = {@Model.Texture(key = "top", id = "{texture}"), @Model.Texture(key = "bottom", id = "{texture}"), @Model.Texture(key = "side", id = "{texture}")}
)
@Tag(
        id = "{id}",
        tag = "stairs"
)
@Composite
public @interface Stairs {
    String id();
    String texture();
    String material();
}
