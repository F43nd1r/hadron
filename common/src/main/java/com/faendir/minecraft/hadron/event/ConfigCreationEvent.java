package com.faendir.minecraft.hadron.event;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.Event;

/**
 * @author lukas
 * @since 01.08.19
 */
public class ConfigCreationEvent extends Event {
    private final ForgeConfigSpec.Builder builder;

    public ConfigCreationEvent(ForgeConfigSpec.Builder builder) {
        this.builder = builder;
    }

    public ForgeConfigSpec.Builder getBuilder() {
        return builder;
    }
}
