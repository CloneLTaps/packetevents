package io.github.retrooper.packetevents.utils;

import io.github.retrooper.packetevents.enums.ServerVersion;
import io.github.retrooper.packetevents.packetwrappers.Sendable;
import io.github.retrooper.packetevents.utils.nms_entityfinder.EntityFinderUtils;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NMSUtils {
    private static final ServerVersion version = ServerVersion.getVersion();


    private static final String nettyPrefix = version == ServerVersion.v_1_7_10 ? "net.minecraft.util.io.netty" : "io.netty";
    private static final String nmsDir = ServerVersion.getNMSDirectory();
    private static final String obcDir = ServerVersion.getOBCDirectory();

    public static Class<?> minecraftServerClass, craftWorldsClass,
            packetClass, entityPlayerClass, playerConnectionClass, craftPlayerClass;
    private static Class<?> serverConnectionClass, craftEntityClass;

    private static Method getServerMethod;
    private static Method getCraftWorldHandleMethod;
    private static Method entityMethod;
    public static Method getServerConnection;
    public static Method getCraftPlayerHandle;
    public static Method getCraftEntityHandle;
    private static Method sendPacketMethod;

    private static Field recentTPSField;
    private static Field entityPlayerPingField;
    private static Field playerConnectionField;

    static {
        try {
            minecraftServerClass = getNMSClass("MinecraftServer");
            craftWorldsClass = getOBCClass("CraftWorld");
            craftPlayerClass = getOBCClass("entity.CraftPlayer");
            entityPlayerClass = getNMSClass("EntityPlayer");
            packetClass = getNMSClass("Packet");
            playerConnectionClass = getNMSClass("PlayerConnection");
            serverConnectionClass = getNMSClass("ServerConnection");
            craftEntityClass = getOBCClass("entity.CraftEntity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //METHODS
        try {
            getServerMethod = minecraftServerClass.getMethod("getServer");
            getCraftWorldHandleMethod = craftWorldsClass.getMethod("getHandle");
            getCraftPlayerHandle = craftPlayerClass.getMethod("getHandle");
            getCraftEntityHandle = craftEntityClass.getMethod("getHandle");
            sendPacketMethod = playerConnectionClass.getMethod("sendPacket", packetClass);
            getServerConnection = minecraftServerClass.getMethod("getServerConnection");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        try {
            recentTPSField = minecraftServerClass.getField("recentTps");
            entityPlayerPingField = entityPlayerClass.getField("ping");
            playerConnectionField = entityPlayerClass.getField("playerConnection");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

    public static Object getMinecraftServerInstance() throws InvocationTargetException, IllegalAccessException {
        return getServerMethod.invoke(null);
    }

    public static double[] recentTPS() throws IllegalAccessException, InvocationTargetException {
        final Object minecraftServerObj = getMinecraftServerInstance();
        double[] recentTPS = (double[]) recentTPSField.get(minecraftServerObj);
        return recentTPS;
    }

    public static Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName(nmsDir + "." + name);
    }

    public static Class<?> getOBCClass(String name) throws ClassNotFoundException {
        return Class.forName(obcDir + "." + name);
    }

    public static Class<?> getNettyClass(String name) throws ClassNotFoundException {
        return Class.forName(nettyPrefix + "." + name);
    }

    public static final String getChannelFutureListFieldName() {
        if (version.isLowerThan(ServerVersion.v_1_8))
            return "e";
        if (version.isLowerThan(ServerVersion.v_1_13))
            return "g";
        return "f";
    }

    @Nullable
    public static Entity getEntityById(final int id) {
        return EntityFinderUtils.getEntityById(id);
    }

    @Nullable
    public static Entity getEntityByIdWithWorld(final World world, final int id) {
        return EntityFinderUtils.getEntityByIdWithWorld(world, id);
    }

    public static Object getNMSEntity(final Entity entity) {
        final Object craftEntity = craftEntityClass.cast(entity);
        try {
            return getCraftEntityHandle.invoke(craftEntity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getEntityPlayer(final Player player) {
        try {
            return getCraftPlayerHandle.invoke(craftPlayerClass.cast(player));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getPlayerPing(final Player player) {
        final Object craftPlayer = craftPlayerClass.cast(player);
        Object entityPlayer = null;
        try {
            entityPlayer = getCraftPlayerHandle.invoke(craftPlayer);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            return entityPlayerPingField.getInt(entityPlayer);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean is_1_8() {
        return version.isHigherThan(ServerVersion.v_1_7_10)
                && version.isLowerThan(ServerVersion.v_1_9);
    }

    public static void sendSendableWrapper(final Player player, final Sendable sendable) {
        sendNMSPacket(player, sendable.asNMSPacket());
    }

    public static void sendNMSPacket(final Player player, final Object nmsPacket) {
        Object entityPlayer = null;
        try {
            entityPlayer = getCraftPlayerHandle.invoke(craftPlayerClass.cast(player));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Object playerConnection = null;
        try {
            playerConnection = playerConnectionField.get(entityPlayer);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            sendPacketMethod.invoke(playerConnection, nmsPacket);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public static String getServerConnectionFieldName() {
        return "p";
    }
}
