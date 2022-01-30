/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.rqndomuhc.commands.CEnchant;
import io.rqndomhax.rqndomuhc.commands.CForce;
import io.rqndomhax.rqndomuhc.commands.CHost;
import io.rqndomhax.rqndomuhc.commands.CHostSaveInventory;
import io.rqndomhax.rqndomuhc.game.GameMessages;
import io.rqndomhax.rqndomuhc.game.GamePlayer;
import io.rqndomhax.rqndomuhc.game.GameRules;
import io.rqndomhax.rqndomuhc.game.GameScoreboard;
import io.rqndomhax.rqndomuhc.listeners.EGamePlayer;
import io.rqndomhax.rqndomuhc.listeners.ELobby;
import io.rqndomhax.rqndomuhc.listeners.ERole;
import io.rqndomhax.rqndomuhc.listeners.GameLock;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.game.IGameTask;
import io.rqndomhax.uhcapi.game.IRules;
import io.rqndomhax.uhcapi.managers.IHostConfigManager;
import io.rqndomhax.uhcapi.managers.IHostManager;
import io.rqndomhax.uhcapi.managers.IWorldManager;
import io.rqndomhax.uhcapi.utils.FileManager;
import io.rqndomhax.uhcapi.utils.IScoreboard;
import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.utils.inventory.IDynamicInventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class GameManager implements UHCAPI {

    final IScoreboard gameScoreboard;
    final Set<IGamePlayer> gamePlayers = new HashSet<>();
    final IRules gameRules;
    final IDynamicInventoryManager inventories;
    final IGameTask taskManager;
    final IHostManager hostManager;
    final IHostConfigManager configManager;
    final RValue gameMessages = new GameMessages();
    final IWorldManager worldManager;
    final JavaPlugin plugin;

    public GameManager(JavaPlugin plugin) throws IOException {
        this.plugin = plugin;

        this.gameScoreboard = new GameScoreboard(this);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Register scoreboard manager.");

        this.gameRules = new GameRules(this);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Registered rules.");

        this.hostManager = new HostManager(this);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Registered host manager.");

        this.configManager = new HostConfigManager(this, new FileManager(plugin), plugin.getDataFolder());
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Registered host config manager.");

        this.worldManager = new WorldManager(this);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Registered world manager.");

        this.taskManager = new TaskManager(this);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Registered game task manager.");

        Bukkit.getPluginManager().registerEvents(new ELobby(this), plugin);
        Bukkit.getPluginManager().registerEvents(new EGamePlayer(this), plugin);
        Bukkit.getPluginManager().registerEvents(new GameLock(this), plugin);
        Bukkit.getPluginManager().registerEvents(new ERole(this), plugin);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Registered listeners.");

        Objects.requireNonNull(plugin.getCommand("save")).setExecutor(new CHostSaveInventory(this));
        Objects.requireNonNull(plugin.getCommand("host")).setExecutor(new CHost(this));
        Objects.requireNonNull(plugin.getCommand("force")).setExecutor(new CForce(this));
        Objects.requireNonNull(plugin.getCommand("enchant")).setExecutor(new CEnchant(this));
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Registered commands.");

        this.inventories = new DynamicInventoryManager(this);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Registered inventories manager.");
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
    public IRules getRules() {
        return gameRules;
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
    public IGamePlayer registerGamePlayer(Player player) {
        IGamePlayer target = new GamePlayer(player.getUniqueId(), player.getName());
        getGamePlayers().add(target);
        return target;
    }

    @Override
    public IGameTask getGameTaskManager() {
        return taskManager;
    }

    @Override
    public RValue getGameMessages() {
        return gameMessages;
    }

    @Override
    public IHostManager getHostManager() {
        return hostManager;
    }

    @Override
    public IHostConfigManager getHostConfigManager() {
        return this.configManager;
    }

    @Override
    public IDynamicInventoryManager getInventories() {
        return inventories;
    }

    @Override
    public IWorldManager getWorldManager() {
        return worldManager;
    }

}
