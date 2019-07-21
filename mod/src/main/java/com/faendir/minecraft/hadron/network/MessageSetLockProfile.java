package com.faendir.minecraft.hadron.network;

import com.faendir.minecraft.hadron.misc.feature.LockDirectionHotkey;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author lukas
 * @since 20.07.19
 */
public class MessageSetLockProfile extends NetworkMessage<MessageSetLockProfile> {

    public LockDirectionHotkey.LockProfile profile;

    public MessageSetLockProfile() { }

    public MessageSetLockProfile(LockDirectionHotkey.LockProfile profile) {
        this.profile = profile;
    }

    @Override
    public void handleMessage(NetworkEvent.Context context) {
        LockDirectionHotkey.setProfile(context.getSender(), profile);
    }

}
