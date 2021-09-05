/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc;

import io.rqndomhax.rqndomuhc.game.GameMessages;
import io.rqndomhax.rqndomuhc.game.GameRules;
import io.rqndomhax.rqndomuhc.game.GameScoreboard;
import io.rqndomhax.rqndomuhc.tasks.TaskManager;
import io.rqndomhax.rqndomuhc.world.WorldManager;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.RGamePlayer;
import io.rqndomhax.uhcapi.game.RHost;
import io.rqndomhax.uhcapi.game.RRules;
import io.rqndomhax.uhcapi.utils.RDynamicInventory;
import io.rqndomhax.uhcapi.utils.RScoreboard;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GameManager implements UHCAPI {

    final GameScoreboard gameScoreboard;
    final Set<RGamePlayer> gamePlayers = new HashSet<>();
    final GameRules gameRules;
    final RDynamicInventory inventories = new RDynamicInventory();
    public TaskManager taskManager = null;
    final RHost hostManager = new RHost();
    final GameMessages gameMessages = new GameMessages();
    final WorldManager worldManager = new WorldManager();
    final JavaPlugin plugin;

    public GameManager(JavaPlugin plugin) throws IOException {
        this.plugin = plugin;
        this.gameScoreboard = new GameScoreboard(this);
        this.gameRules = new GameRules(this);
        worldManager.createWorld("io.rqndomhax.rqndomuhc.world.meetup", new File("Meetup"), new File("original/Meetup"));
    }

    @Override
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public RScoreboard getScoreboard() {
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
    public RHost getHostManager() {
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
