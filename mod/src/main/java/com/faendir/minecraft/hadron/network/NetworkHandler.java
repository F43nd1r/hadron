package com.faendir.minecraft.hadron.network;

import com.faendir.minecraft.hadron.Hadron;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * @author lukas
 * @since 20.07.19
 */

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1.0";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Hadron.ID, "main_channel"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    private static int i = 0;

    public static <T extends NetworkMessage<T>> void register(Class<T> clazz) {
        INSTANCE.registerMessage(i++, clazz, NetworkMessage::toBytes, buf -> NetworkMessage.fromBytes(clazz, buf), (msg, contextSupplier) -> msg.handleMessage(contextSupplier.get()));
    }

}
