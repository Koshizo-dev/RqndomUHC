/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.rqndomuhc.managers.RolesManager;
import io.rqndomhax.rqndomuhc.managers.ScenariosManager;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.IRules;
import io.rqndomhax.uhcapi.managers.IRoleManager;
import io.rqndomhax.uhcapi.managers.RScenariosManager;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class GameRules implements IRules {

    final UHCAPI api;
    RValue gameInfos = new RValue();
    final RScenariosManager scenariosManager;
    final IRoleManager rolesManager;

    public GameRules(UHCAPI api) {
        this.api = api;
        this.scenariosManager = new ScenariosManager(api);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered scenarios manager.");
        this.rolesManager = new RolesManager();
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered roles manager.");
        gameInfos.addObject("api.gameTitle", "RqndomUHC");
        gameInfos.addObject("api.episodeLength", 20);
        gameInfos.addObject("api.teleportationDuration", 15);
        gameInfos.addObject("api.isServerLocked", false);
        gameInfos.addObject("api.serverLockedKickMessage", "The server is currently locked!");
        gameInfos.addObject("api.hasSpectatorsAfterBorder", true);
        gameInfos.addObject("api.hasSpectators", true);
        gameInfos.addObject("api.hasWhitelist", true);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered default timers.");
    }

    @Override
    public RValue getGameInfos() {
        return gameInfos;
    }

    @Override
    public void setGameInfos(RValue gameInfos) {
        this.gameInfos = gameInfos;
    }

    @Override
    public RScenariosManager getScenariosManager() {
        return scenariosManager;
    }

    @Override
    public IRoleManager getRolesManager() {
        return rolesManager;
    }
}
