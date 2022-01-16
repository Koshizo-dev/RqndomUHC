/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc;

import io.rqndomhax.rqndomuhc.managers.GameManager;
import io.rqndomhax.uhcapi.GetUHCAPI;
import io.rqndomhax.uhcapi.UHCAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class RqndomUHC extends JavaPlugin implements GetUHCAPI {

    private UHCAPI gameManager;

    @Override
    public void onEnable() {
        try {
            this.gameManager = new GameManager(this);
            Bukkit.getServicesManager().register(GetUHCAPI.class, this, this, ServicePriority.Highest);
            super.onEnable();
        } catch (IOException e) {
            e.printStackTrace();
            onDisable();
        }
    }

    @Override
    public void onDisable() {
        if (gameManager == null) {
            super.onDisable();
            return;
        }
        /*
         * This might seem old, but it may be a source of error
         */
        gameManager.setGameTaskManager(null);
        gameManager.setHostManager(null);
        gameManager.setWorldManager(null);
        gameManager.setScoreboardManager(null);
        gameManager.setInventories(null);
        if (gameManager.getRules() != null) {
            gameManager.getRules().setRolesManager(null);
            gameManager.getRules().setScenariosManager(null);
        }
        gameManager.setRules(null);
        super.onDisable();
    }

    @Override
    public UHCAPI getUHCAPI() {
        return gameManager;
    }
}
