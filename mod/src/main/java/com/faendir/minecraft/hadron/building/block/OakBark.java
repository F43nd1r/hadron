package com.faendir.minecraft.hadron.building.block;

import com.faendir.minecraft.hadron.Hadron;
import com.faendir.minecraft.hadron.annotation.BlockState;
import com.faendir.minecraft.hadron.annotation.GenerateItem;
import com.faendir.minecraft.hadron.annotation.GenerateSlabs;
import com.faendir.minecraft.hadron.annotation.GenerateStairs;
import com.faendir.minecraft.hadron.annotation.GenerateWall;
import com.faendir.minecraft.hadron.annotation.Model;
import com.faendir.minecraft.hadron.annotation.Recipe;
import com.faendir.minecraft.hadron.annotation.Register;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

/**
 * @author lukas
 * @since 21.07.19
 */
@Register(Block.class)
@Recipe(id = OakBark.ID, pattern = {"xx", "xx"}, keys = @Recipe.Key(key = "x", value = "minecraft:oak_log"), count = 4)
@BlockState(id = OakBark.ID, variants = @BlockState.Variant(id = "", model = @BlockState.Model(Hadron.ID + ":block/" + OakBark.ID)))
@Model(id = OakBark.ID, parent = "minecraft:block/cube_all", textures = @Model.Texture(key = "all", id = OakBark.TEXTURE))
@GenerateItem(OakBark.ID)
@GenerateStairs(id = OakBark.ID, texture = OakBark.TEXTURE)
@GenerateSlabs(id = OakBark.ID, texture = OakBark.TEXTURE)
@GenerateWall(id = OakBark.ID, texture = OakBark.TEXTURE)
public class OakBark extends Block {
    static final String ID = "oak_bark";
    static final String TEXTURE = "minecraft:block/oak_log";

    public OakBark() {
        super(Properties.from(Blocks.OAK_LOG));
        setRegistryName(ID);
    }
}
