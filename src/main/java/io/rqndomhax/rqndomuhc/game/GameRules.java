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
import io.rqndomhax.uhcapi.utils.BukkitSerializer;
import io.rqndomhax.uhcapi.utils.HostConfig;
import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.logging.Level;

public class GameRules implements IRules {

    final UHCAPI api;
    HostConfig config;
    final RScenariosManager scenariosManager;
    final IRoleManager rolesManager;

    public GameRules(UHCAPI api) {
        this.api = api;
        this.scenariosManager = new ScenariosManager(api);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered scenarios manager.");
        this.config = new HostConfig(new RValue(), "default", "configs/default.cfg");
        this.rolesManager = new RolesManager();
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered roles manager.");
        config.getGameInfos().addObject("api.gameTitle", "RqndomUHC");
        config.getGameInfos().addObject("api.episodeLength", 20);
        config.getGameInfos().addObject("api.timeBeforeDeath", 10);
        config.getGameInfos().addObject("api.teleportationDuration", 10);
        config.getGameInfos().addObject("api.rolesAnnounce", 20*60);
        config.getGameInfos().addObject("api.preparationDuration", 40*60);
        config.getGameInfos().addObject("api.isServerLocked", false);
        config.getGameInfos().addObject("api.hasSpectatorsAfterBorder", true);
        config.getGameInfos().addObject("api.hasSpectators", true);
        config.getGameInfos().addObject("api.hasWhitelist", true);
        config.getGameInfos().addObject("api.startInventory", new ItemStack[40]);
        config.getGameInfos().addObject("api.deathInventory", new ItemStack[40]);
        config.getGameInfos().addObject("api.immunityDuration", 60);
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] Rules >> Registered default timers.");
    }

    @Override
    public RValue getGameInfos() {
        return config.getGameInfos();
    }

    @Override
    public void setGameInfos(RValue gameInfos) {
        this.config.setGameInfos(gameInfos);
    }

    @Override
    public HostConfig getHostConfig() {
        HostConfig config = this.config.copy();

        Object startInventory = this.config.getGameInfos().getObject("api.startInventory");
        if (startInventory instanceof ItemStack[])
            config.getGameInfos().addObject("api.startInventory", BukkitSerializer.inventoryToString((ItemStack[]) startInventory));
        Object deathInventory = this.config.getGameInfos().getObject("api.deathInventory");
        if (deathInventory instanceof ItemStack[])
            config.getGameInfos().addObject("api.deathInventory", BukkitSerializer.inventoryToString((ItemStack[]) deathInventory));
        config.getGameInfos().addObject("api.activeRoles", getRolesManager().getActiveRoles().keySet());
        config.getGameInfos().addObject("api.activeScenarios", getScenariosManager().getActiveScenarios().keySet());
        return (config);
    }

    @Override
    public void setHostConfig(HostConfig config) {
        Set<String> activeRoles = (Set<String>) config.getGameInfos().getObject("api.activeRoles");
        Set<String> activeScenarios = (Set<String>) config.getGameInfos().getObject("api.activeScenarios");
        Object startInventory = config.getGameInfos().getObject("api.startInventory");
        if (startInventory instanceof String)
            config.getGameInfos().addObject("api.startInventory", BukkitSerializer.stringToItemArray((String) startInventory));
        Object deathInventory = config.getGameInfos().getObject("api.deathInventory");
        if (deathInventory instanceof String)
            config.getGameInfos().addObject("api.deathInventory", BukkitSerializer.stringToItemArray((String) deathInventory));

        getRolesManager().clearActiveRoles();
        getScenariosManager().clearActiveScenarios();
        for (String roleKey : activeRoles)
            getRolesManager().enableRole(roleKey);

        for (String scenarioKey : activeScenarios)
            getScenariosManager().activateScenario(scenarioKey);
        config.getGameInfos().removeKey("api.activeRoles");
        config.getGameInfos().removeKey("api.activeScenarios");
        this.config = config;
        for (RInventory inventory : api.getInventories().getInventories().values()) {
            inventory.refreshInventory();
            api.getInventories().updateInventory(inventory);
        }
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
