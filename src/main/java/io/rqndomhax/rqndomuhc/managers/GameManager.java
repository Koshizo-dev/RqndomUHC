/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.rqndomuhc.game.GameMessages;
import io.rqndomhax.rqndomuhc.game.GameRules;
import io.rqndomhax.rqndomuhc.game.GameScoreboard;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.game.IHostManager;
import io.rqndomhax.uhcapi.game.IRules;
import io.rqndomhax.uhcapi.utils.IScoreboard;
import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.utils.inventory.IDynamicInventoryManager;
import io.rqndomhax.uhcapi.world.IWorldManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GameManager implements UHCAPI {

    IScoreboard gameScoreboard;
    final Set<IGamePlayer> gamePlayers = new HashSet<>();
    IRules gameRules;
    IDynamicInventoryManager inventories;
    public TaskManager taskManager = null;
    IHostManager hostManager;
    final RValue gameMessages = new GameMessages();
    IWorldManager worldManager;
    final JavaPlugin plugin;

    public GameManager(JavaPlugin plugin) throws IOException {
        this.plugin = plugin;
        setScoreboardManager(new GameScoreboard(this));
        setRules(new GameRules(this));
        setInventories(new DynamicInventoryManager(plugin));
        setHostManager(new HostManager());
        setWorldManager(new WorldManager());
    }

    @Override
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public IScoreboard getScoreboardManager() {
        return gameScoreboard;
    }

    @Override
    public void setScoreboardManager(IScoreboard gameScoreboard) {
        this.gameScoreboard = gameScoreboard;
    }

    @Override
    public IRules getRules() {
        return gameRules;
    }

    @Override
    public void setRules(IRules gameRules) {
        this.gameRules = gameRules;
    }

    @Override
    public Set<IGamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    @Override
    public IGamePlayer getGamePlayer(String username) {
        return getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getName().equals(username)).findFirst().orElse(null);
    }

    @Override
    public IGamePlayer getGamePlayer(UUID uuid) {
        return getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getUniqueID().equals(uuid)).findFirst().orElse(null);
    }

    @Override
    public IGamePlayer getGamePlayer(Player player) {
        return getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getUniqueID().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    @Override
    public IHostManager getHostManager() {
        return hostManager;
    }

    @Override
    public void setHostManager(IHostManager hostManager) {
        this.hostManager = hostManager;
    }

    @Override
    public IDynamicInventoryManager getInventories() {
        return inventories;
    }

    @Override
    public void setInventories(IDynamicInventoryManager inventories) {
        this.inventories = inventories;
    }

    @Override
    public IWorldManager getWorldManager() {
        return worldManager;
    }

    @Override
    public void setWorldManager(IWorldManager worldManager) {
        this.worldManager = worldManager;
    }
}
