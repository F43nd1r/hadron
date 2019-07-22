package com.faendir.minecraft.hadron.building.block;

import com.faendir.minecraft.hadron.Hadron;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

/**
 * @author lukas
 * @since 27.06.19
 */
@Brick(id = AndesiteBricks.ID, texture = Hadron.ID + ":block/" + AndesiteBricks.ID, material = "minecraft:polished_andesite")
public class AndesiteBricks extends Block {
    static final String ID = "andesite_bricks";

    public AndesiteBricks() {
        super(Properties.from(Blocks.POLISHED_ANDESITE));
        setRegistryName(ID);
    }
}
