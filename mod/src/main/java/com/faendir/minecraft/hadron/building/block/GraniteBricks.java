package com.faendir.minecraft.hadron.building.block;

import com.faendir.minecraft.hadron.Hadron;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * @author lukas
 * @since 27.06.19
 */
@Brick(id = GraniteBricks.ID, texture = Hadron.ID + ":block/" + GraniteBricks.ID, material = "minecraft:polished_granite")
public class GraniteBricks extends Block {
    static final String ID = "granite_bricks";

    public GraniteBricks() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(1.5F, 10.0F).sound(SoundType.STONE));
        setRegistryName(ID);
    }
}
