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
@Recipe(pattern = {"xx", "xx"}, keys = @Recipe.Key(key = "x", value = "minecraft:polished_andesite"), id = AndesiteBricks.ID, count = 4)
@BlockState(id = AndesiteBricks.ID, variants = @BlockState.Variant(id = "", model = @BlockState.Model(AndesiteBricks.LOCATION)))
@Model(id = AndesiteBricks.ID, parent = "minecraft:block/cube_all", textures = @Model.Texture(key = "all", id = AndesiteBricks.LOCATION))
@GenerateItem(AndesiteBricks.ID)
@GenerateStairs(id = AndesiteBricks.ID, texture = AndesiteBricks.LOCATION)
@GenerateSlabs(id = AndesiteBricks.ID, texture = AndesiteBricks.LOCATION)
public class AndesiteBricks extends Block {
    static final String ID = "andesite_bricks";
    static final String LOCATION = Hadron.ID + ":block/" + ID;

    public AndesiteBricks() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 10.0F).sound(SoundType.STONE));
        setRegistryName(ID);
    }
}
