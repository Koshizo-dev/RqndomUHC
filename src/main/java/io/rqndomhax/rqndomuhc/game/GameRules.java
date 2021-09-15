/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.rqndomuhc.managers.GameManager;
import io.rqndomhax.rqndomuhc.managers.RolesManager;
import io.rqndomhax.rqndomuhc.managers.ScenariosManager;
import io.rqndomhax.uhcapi.game.RRules;
import io.rqndomhax.uhcapi.role.RRoleManager;
import io.rqndomhax.uhcapi.scenarios.RScenariosManager;
import io.rqndomhax.uhcapi.utils.RValue;

public class GameRules implements RRules {

    String gameTitle;
    final GameManager gameManager;
    final RValue gameTimers = new RValue();
    RScenariosManager scenariosManager;
    RRoleManager rolesManager;

    public GameRules(GameManager gameManager) {
        this.gameManager = gameManager;
        setScenariosManager(new ScenariosManager(gameManager));
        setRolesManager(new RolesManager());
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
    public RRoleManager getRolesManager() {
        return rolesManager;
    }

    @Override
    public void setRolesManager(RRoleManager rolesManager) {
        this.rolesManager = rolesManager;
    }
}
