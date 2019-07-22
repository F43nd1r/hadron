package com.faendir.minecraft.hadron.building.block;

import com.faendir.minecraft.hadron.Hadron;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

/**
 * @author lukas
 * @since 27.06.19
 */
@Brick(id = DioriteBricks.ID, texture = Hadron.ID + ":block/" + DioriteBricks.ID, material = "minecraft:polished_diorite")
public class DioriteBricks extends Block {
    static final String ID = "diorite_bricks";

    public DioriteBricks() {
        super(Properties.from(Blocks.POLISHED_DIORITE));
        setRegistryName(ID);
    }
}
