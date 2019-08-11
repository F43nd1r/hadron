package com.faendir.minecraft.hadron.helper;

import com.faendir.minecraft.hadron.event.ConfigCreationEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author lukas
 * @since 31.07.19
 */
public class HadronConfigHolder {
    private static final ThreadLocal<HadronConfigHolder> configHolder = ThreadLocal.withInitial(HadronConfigHolder::new);
    private ForgeConfigSpec configSpec;

    private ForgeConfigSpec fireConfigEvent() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        ConfigCreationEvent event = new ConfigCreationEvent(builder);
        MinecraftForge.EVENT_BUS.post(event);
        configSpec = builder.build();
        return configSpec;
    }

    public static ForgeConfigSpec buildConfig() {
        return configHolder.get().fireConfigEvent();
    }

    public static ForgeConfigSpec getConfig() {
        return configHolder.get().configSpec;
    }
}
