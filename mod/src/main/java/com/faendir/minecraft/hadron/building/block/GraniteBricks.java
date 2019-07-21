package com.faendir.minecraft.hadron.building.block;

import com.faendir.minecraft.hadron.Hadron;
import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.annotation.Register;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * @author lukas
 * @since 27.06.19
 */
@Register(Block.class)
@Recipe(pattern = {"xx", "xx"}, keys = @Recipe.Key(key = "x", value = "minecraft:polished_granite"), id = GraniteBricks.ID, count = 4)
@BlockState(id = GraniteBricks.ID, variants = @BlockState.Variant(id = "", model = @BlockState.Model(GraniteBricks.LOCATION)))
@Model(id = GraniteBricks.ID, parent = "minecraft:block/cube_all", textures = @Model.Texture(key = "all", id = GraniteBricks.LOCATION))
@GenerateItem(GraniteBricks.ID)
@GenerateStairs(id = GraniteBricks.ID, texture = GraniteBricks.LOCATION)
@GenerateSlabs(id = GraniteBricks.ID, texture = GraniteBricks.LOCATION)
public class GraniteBricks extends Block {
    static final String ID = "granite_bricks";
    static final String LOCATION = Hadron.ID + ":block/" + ID;

    public GraniteBricks() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 10.0F).sound(SoundType.STONE));
        setRegistryName(ID);
    }
}
