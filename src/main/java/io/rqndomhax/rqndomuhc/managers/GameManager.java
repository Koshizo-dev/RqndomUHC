/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.rqndomuhc.game.GameMessages;
import io.rqndomhax.rqndomuhc.game.GameRules;
import io.rqndomhax.rqndomuhc.game.GameScoreboard;
import io.rqndomhax.rqndomuhc.utils.DynamicInventoryManager;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.RGamePlayer;
import io.rqndomhax.uhcapi.game.RHostManager;
import io.rqndomhax.uhcapi.game.RRules;
import io.rqndomhax.uhcapi.utils.RDynamicInventory;
import io.rqndomhax.uhcapi.utils.RScoreboard;
import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.world.RWorldManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GameManager implements UHCAPI {

    RScoreboard gameScoreboard;
    final Set<RGamePlayer> gamePlayers = new HashSet<>();
    RRules gameRules;
    RDynamicInventory inventories;
    public TaskManager taskManager = null;
    RHostManager hostManager;
    final RValue gameMessages = new GameMessages();
    RWorldManager worldManager;
    final JavaPlugin plugin;

    public GameManager(JavaPlugin plugin) throws IOException {
        this.plugin = plugin;
        setScoreboardManager(new GameScoreboard(this));
        setRules(new GameRules(this));
        setInventories(new DynamicInventoryManager());
        setHostManager(new HostManager());
        setWorldManager(new WorldManager());
    }

    @Override
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public RScoreboard getScoreboardManager() {
        return gameScoreboard;
    }

    @Override
    public void setScoreboardManager(RScoreboard gameScoreboard) {
        this.gameScoreboard = gameScoreboard;
    }

    @Override
    public RRules getRules() {
        return gameRules;
    }

    @Override
    public void setRules(RRules gameRules) {
        this.gameRules = gameRules;
    }

    @Override
    public Set<RGamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    @Override
    public RGamePlayer getGamePlayer(String username) {
        return getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getName().equals(username)).findFirst().orElse(null);
    }

    @Override
    public RGamePlayer getGamePlayer(UUID uuid) {
        return getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getUniqueID().equals(uuid)).findFirst().orElse(null);
    }

    @Override
    public RGamePlayer getGamePlayer(Player player) {
        return getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getUniqueID().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    @Override
    public RHostManager getHostManager() {
        return hostManager;
    }

    @Override
    public void setHostManager(RHostManager hostManager) {
        this.hostManager = hostManager;
    }

    @Override
    public RDynamicInventory getInventories() {
        return inventories;
    }

    @Override
    public void setInventories(RDynamicInventory inventories) {
        this.inventories = inventories;
    }

    @Override
    public RWorldManager getWorldManager() {
        return worldManager;
    }

    @Override
    public void setWorldManager(RWorldManager worldManager) {
        this.worldManager = worldManager;
    }
}
