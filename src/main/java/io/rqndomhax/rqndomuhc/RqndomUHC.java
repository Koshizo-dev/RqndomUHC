/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc;

import io.rqndomhax.rqndomuhc.game.GameManager;
import io.rqndomhax.uhcapi.GetUHCAPI;
import io.rqndomhax.uhcapi.UHCAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class RqndomUHC extends JavaPlugin implements GetUHCAPI {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        this.gameManager = new GameManager(this);
        Bukkit.getServicesManager().register(GetUHCAPI.class, this, this, ServicePriority.Highest);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public UHCAPI getUHCAPI() {
        return gameManager;
    }
}
