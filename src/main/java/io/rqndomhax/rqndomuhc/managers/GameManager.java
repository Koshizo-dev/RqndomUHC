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
import io.rqndomhax.uhcapi.role.RRoleManager;
import io.rqndomhax.uhcapi.scenarios.RScenariosManager;
import io.rqndomhax.uhcapi.utils.RDynamicInventory;
import io.rqndomhax.uhcapi.utils.RScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GameManager implements UHCAPI {

    final GameScoreboard gameScoreboard;
    final Set<RGamePlayer> gamePlayers = new HashSet<>();
    final GameRules gameRules;
    final RDynamicInventory inventories = new DynamicInventoryManager();
    public TaskManager taskManager = null;
    final RHostManager hostManager = new HostManager();
    final GameMessages gameMessages = new GameMessages();
    final WorldManager worldManager = new WorldManager();
    final JavaPlugin plugin;

    public GameManager(JavaPlugin plugin) throws IOException {
        this.plugin = plugin;
        this.gameScoreboard = new GameScoreboard(this);
        this.gameRules = new GameRules(this);
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
    public RRules getRules() {
        return gameRules;
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
    public RDynamicInventory getInventories() {
        return inventories;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
}
