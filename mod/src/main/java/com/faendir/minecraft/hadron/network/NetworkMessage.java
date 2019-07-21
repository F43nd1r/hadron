package com.faendir.minecraft.hadron.network;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author lukas
 * @since 20.07.19
 */
public abstract class NetworkMessage<REQ extends NetworkMessage> implements Serializable {

    private static final HashMap<Class, Pair<Reader, Writer>> handlers = new HashMap<>();
    private static final HashMap<Class, Field[]> fieldCache = new HashMap<>();

    static {
        mapHandler(byte.class, NetworkMessage::readByte, NetworkMessage::writeByte);
        mapHandler(short.class, NetworkMessage::readShort, NetworkMessage::writeShort);
        mapHandler(int.class, NetworkMessage::readInt, NetworkMessage::writeInt);
        mapHandler(long.class, NetworkMessage::readLong, NetworkMessage::writeLong);
        mapHandler(float.class, NetworkMessage::readFloat, NetworkMessage::writeFloat);
        mapHandler(double.class, NetworkMessage::readDouble, NetworkMessage::writeDouble);
        mapHandler(boolean.class, NetworkMessage::readBoolean, NetworkMessage::writeBoolean);
        mapHandler(char.class, NetworkMessage::readChar, NetworkMessage::writeChar);
        mapHandler(String.class, NetworkMessage::readString, NetworkMessage::writeString);
        mapHandler(CompoundNBT.class, NetworkMessage::readNBT, NetworkMessage::writeNBT);
        mapHandler(ItemStack.class, NetworkMessage::readItemStack, NetworkMessage::writeItemStack);
        mapHandler(BlockPos.class, NetworkMessage::readBlockPos, NetworkMessage::writeBlockPos);
    }

    // The thing you override!
    public abstract void handleMessage(NetworkEvent.Context context);

    public static <T extends NetworkMessage<?>> T fromBytes(Class<T> clazz, PacketBuffer buf) {
        try {
            Field[] clFields = getClassFields(clazz);
            T message = clazz.getDeclaredConstructor().newInstance();
            for (Field f : clFields) {
                Class<?> type = f.getType();
                if (acceptField(f, type))
                    message.readField(f, type, buf);
            }
            return message;
        } catch (Exception e) {
            throw new RuntimeException("Error at reading packet", e);
        }
    }

    public final void toBytes(PacketBuffer buf) {
        try {
            Class<?> clazz = getClass();
            Field[] clFields = getClassFields(clazz);
            for (Field f : clFields) {
                Class<?> type = f.getType();
                if (acceptField(f, type))
                    writeField(f, type, buf);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error at writing packet " + this, e);
        }
    }

    private static Field[] getClassFields(Class<?> clazz) {
        if (fieldCache.containsKey(clazz))
            return fieldCache.get(clazz);
        else {
            Field[] fields = clazz.getFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            fieldCache.put(clazz, fields);
            return fields;
        }
    }

    @SuppressWarnings("unchecked")
    private void writeField(Field f, Class clazz, PacketBuffer buf) throws IllegalArgumentException, IllegalAccessException {
        Pair<Reader, Writer> handler = getHandler(clazz);
        handler.getRight().write(f.get(this), buf);
    }

    protected void readField(Field f, Class clazz, PacketBuffer buf) throws IllegalArgumentException, IllegalAccessException {
        Pair<Reader, Writer> handler = getHandler(clazz);
        f.set(this, handler.getLeft().read(buf));
    }

    private static Pair<Reader, Writer> getHandler(Class<?> clazz) {
        Pair<Reader, Writer> pair = handlers.get(clazz);
        if (pair == null)
            throw new RuntimeException("No R/W handler for  " + clazz);
        return pair;
    }

    private static boolean acceptField(Field f, Class<?> type) {
        int mods = f.getModifiers();
        if (Modifier.isFinal(mods) || Modifier.isStatic(mods) || Modifier.isTransient(mods))
            return false;

        return handlers.containsKey(type);
    }

    public static <T> void mapHandler(Class<T> type, Reader<T> reader, Writer<T> writer) {
        handlers.put(type, Pair.of(reader, writer));
    }

    private static byte readByte(PacketBuffer buf) {
        return buf.readByte();
    }

    private static void writeByte(byte b, PacketBuffer buf) {
        buf.writeByte(b);
    }

    private static short readShort(PacketBuffer buf) {
        return buf.readShort();
    }

    private static void writeShort(short s, PacketBuffer buf) {
        buf.writeShort(s);
    }

    private static int readInt(PacketBuffer buf) {
        return buf.readInt();
    }

    private static void writeInt(int i, PacketBuffer buf) {
        buf.writeInt(i);
    }

    private static long readLong(PacketBuffer buf) {
        return buf.readLong();
    }

    private static void writeLong(long l, PacketBuffer buf) {
        buf.writeLong(l);
    }

    private static float readFloat(PacketBuffer buf) {
        return buf.readFloat();
    }

    private static void writeFloat(float f, PacketBuffer buf) {
        buf.writeFloat(f);
    }

    private static double readDouble(PacketBuffer buf) {
        return buf.readDouble();
    }

    private static void writeDouble(double d, PacketBuffer buf) {
        buf.writeDouble(d);
    }

    private static boolean readBoolean(PacketBuffer buf) {
        return buf.readBoolean();
    }

    private static void writeBoolean(boolean b, PacketBuffer buf) {
        buf.writeBoolean(b);
    }

    private static char readChar(PacketBuffer buf) {
        return buf.readChar();
    }

    private static void writeChar(char c, PacketBuffer buf) {
        buf.writeChar(c);
    }

    private static String readString(PacketBuffer buf) {
        return buf.readString();
    }

    private static void writeString(String s, PacketBuffer buf) {
        buf.writeString(s);
    }

    private static CompoundNBT readNBT(PacketBuffer buf) {
        return buf.readCompoundTag();
    }

    private static void writeNBT(CompoundNBT cmp, PacketBuffer buf) {
        buf.writeCompoundTag(cmp);
    }

    private static ItemStack readItemStack(PacketBuffer buf) {
        return ItemStack.read(readNBT(buf));
    }

    private static void writeItemStack(ItemStack stack, PacketBuffer buf) {
        writeNBT(stack.serializeNBT(), buf);
    }

    private static BlockPos readBlockPos(PacketBuffer buf) {
        return BlockPos.fromLong(buf.readLong());
    }

    private static void writeBlockPos(BlockPos pos, PacketBuffer buf) {
        buf.writeLong(pos.toLong());
    }

    // Functional interfaces
    public interface Writer<T> {
        void write(T t, PacketBuffer buf);
    }

    public interface Reader<T> {
        T read(PacketBuffer buf);
    }
}

