package com.faendir.minecraft.hadron;

import com.faendir.minecraft.hadron.config.HadronConfiguration;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

/**
 * @author lukas
 * @since 27.06.19
 */
@Mod(Hadron.ID)
public class Hadron {
    public static final String ID = "hadron";

    public Hadron() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HadronConfiguration.spec);
    }
}
