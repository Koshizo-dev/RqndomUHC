/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.rqndomuhc.GameScoreboard;
import io.rqndomhax.uhcapi.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class GameManager implements UHCAPI {

    final GameScoreboard gameScoreboard;
    final Set<RGamePlayer> gamePlayers = new HashSet<>();
    final GameRules gameRules;
    final RHost hostManager = new RHost();
    final GameMessages gameMessages = new GameMessages();
    final JavaPlugin plugin;

    public GameManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gameScoreboard = new GameScoreboard(this);
        this.gameRules = new GameRules(this);
    }

    @Override
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public IScoreboard getScoreboard() {
        return gameScoreboard;
    }

    @Override
    public IRules getRules() {
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
}
