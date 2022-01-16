/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.rqndomuhc.managers.GameManager;
import io.rqndomhax.rqndomuhc.managers.RolesManager;
import io.rqndomhax.rqndomuhc.managers.ScenariosManager;
import io.rqndomhax.uhcapi.game.IRules;
import io.rqndomhax.uhcapi.role.IRoleManager;
import io.rqndomhax.uhcapi.scenarios.RScenariosManager;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class GameRules implements IRules {

    String gameTitle;
    final GameManager gameManager;
    final RValue gameInfos = new RValue();
    RScenariosManager scenariosManager;
    IRoleManager rolesManager;

    public GameRules(GameManager gameManager) {
        this.gameManager = gameManager;
        setScenariosManager(new ScenariosManager(gameManager));
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered scenarios manager.");
        setRolesManager(new RolesManager());
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered roles manager.");
        gameInfos.addObject("api.episode_length", 20);
        gameInfos.addObject("api.teleportation_duration", 15);
        gameInfos.addObject("api.isServerLocked", false);
        gameInfos.addObject("api.serverLockedKickMessage", "The server is currently locked!");
        gameInfos.addObject("api.hasSpectatorsAfterBorder", true);
        gameInfos.addObject("api.hasSpectators", true);
        gameInfos.addObject("api.hasWhitelist", true);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered default timers.");
    }

    @Override
    public String getGameTitle() {
        return gameTitle;
    }

    @Override
    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    @Override
    public RValue getGameInfos() {
        return gameInfos;
    }

    @Override
    public RScenariosManager getScenariosManager() {
        return scenariosManager;
    }

    @Override
    public void setScenariosManager(RScenariosManager scenariosManager) {
        this.scenariosManager = scenariosManager;
    }

    @Override
    public IRoleManager getRolesManager() {
        return rolesManager;
    }

    @Override
    public void setRolesManager(IRoleManager rolesManager) {
        this.rolesManager = rolesManager;
    }
}
