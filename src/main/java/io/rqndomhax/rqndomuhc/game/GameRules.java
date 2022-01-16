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
    final RValue gameTimers = new RValue();
    RScenariosManager scenariosManager;
    IRoleManager rolesManager;

    public GameRules(GameManager gameManager) {
        this.gameManager = gameManager;
        setScenariosManager(new ScenariosManager(gameManager));
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered scenarios manager");
        setRolesManager(new RolesManager());
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered roles manager");
        gameTimers.addObject("api.episode_length", 20);
        gameTimers.addObject("api.teleportation_duration", 15);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered default timers");
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
    public RValue getTimers() {
        return gameTimers;
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
