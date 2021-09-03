/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.uhcapi.RGamePlayer;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GamePlayer implements RGamePlayer {

    @Override
    public UUID getUniqueID() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public OfflinePlayer getOfflinePlayer() {
        return null;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public List<UUID> getKills() {
        return null;
    }

    @Override
    public Location getPlayerLocation() {
        return null;
    }
}
