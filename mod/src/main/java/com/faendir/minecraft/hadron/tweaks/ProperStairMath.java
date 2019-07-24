package com.faendir.minecraft.hadron.tweaks;

import com.faendir.minecraft.hadron.Hadron;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

/**
 * @author lukas
 * @since 24.07.19
 */
@Mod.EventBusSubscriber(
        modid = Hadron.ID
)
public class ProperStairMath {

    @SubscribeEvent
    public static void afterLoad(FMLLoadCompleteEvent event) {

        RecipeManager manager = new RecipeManager();
    }
}
