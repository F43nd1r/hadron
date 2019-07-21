package com.faendir.minecraft.hadron.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author lukas
 * @since 03.07.19
 */
public class HadronConfiguration {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final Building building = new Building(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();


    public static class Building {
        public final ForgeConfigSpec.ConfigValue<Boolean> moreStoneBricks;

        public Building(ForgeConfigSpec.Builder builder) {
            builder.push("Building");
            moreStoneBricks = builder.translation("config.hadron.building.more_stone_bricks")
                    .define("moreStoneBricks", true);
            builder.pop();
        }
    }
}
