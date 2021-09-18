/*
 * This file is part of packetevents - https://github.com/retrooper/packetevents
 * Copyright (C) 2021 retrooper and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.retrooper.packetevents.utils.dependencies.viaversion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ViaVersionUtil {
    private static ViaVersionAccessor viaVersionAccessor;

    private ViaVersionUtil() {
    }

    private static void load() {
        if (viaVersionAccessor == null) {
            try {
                Class.forName("com.viaversion.viaversion.api.Via");
                viaVersionAccessor = new ViaVersionAccessorImpl();
            } catch (Exception e) {
                viaVersionAccessor = new ViaVersionAccessorImplLegacy();
            }
        }
    }

    public static boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("ViaVersion") != null;
    }


    public static ViaVersionAccessor getViaVersionAccessor() {
        load();
        return viaVersionAccessor;
    }


    public static int getProtocolVersion(Player player) {
        return getViaVersionAccessor().getProtocolVersion(player);
    }

    public static void transformPacket(Object userConnectionObj, Object byteBufObj, boolean clientSide) {
        getViaVersionAccessor().transformPacket(userConnectionObj, byteBufObj, clientSide);
    }

    public static void setUserConnectionActive(Object userConnectionObj, boolean active) {
        getViaVersionAccessor().setUserConnectionActive(userConnectionObj, active);
    }

    public static boolean isUserConnectionActive(Object userConnectionObj) {
        return getViaVersionAccessor().isUserConnectionActive(userConnectionObj);
    }

    public static Class<?> getUserConnectionClass() {
        return getViaVersionAccessor().getUserConnectionClass();
    }
}
