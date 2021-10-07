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

package com.retrooper.packetevents;

import com.retrooper.packetevents.event.EventManager;
import com.retrooper.packetevents.manager.player.PlayerManager;
import com.retrooper.packetevents.manager.server.ServerManager;
import com.retrooper.packetevents.manager.server.ServerVersion;
import com.retrooper.packetevents.settings.PacketEventsSettings;
import com.retrooper.packetevents.util.PEVersion;

import java.util.logging.Logger;

public interface PacketEventsAPI {
    ServerManager getServerManager();
    PlayerManager getPlayerManager();

    EventManager getEventManager();

    PacketEventsSettings getSettings();

    PEVersion getVersion();

    Logger getLogger();
}
