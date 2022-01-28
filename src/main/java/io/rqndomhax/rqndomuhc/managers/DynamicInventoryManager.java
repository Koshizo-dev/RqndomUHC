/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.rqndomuhc.inventories.*;
import io.rqndomhax.rqndomuhc.inventories.configs.IHostCustomConfigs;
import io.rqndomhax.rqndomuhc.inventories.inventory.IHostDeathInventory;
import io.rqndomhax.rqndomuhc.inventories.inventory.IHostInventories;
import io.rqndomhax.rqndomuhc.inventories.inventory.IHostStartInventory;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.utils.inventory.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;
import java.util.logging.Level;

public class DynamicInventoryManager implements IDynamicInventoryManager, Listener {

    private final RValue inventories = new RValue();
    private final HashMap<RInventory, Set<Player>> players = new HashMap<>();

    public DynamicInventoryManager(UHCAPI api) {
        RInventoryManager inventoryManager = new RInventoryManager();
        Bukkit.getPluginManager().registerEvents(new RInventoryHandler(api.getPlugin(), inventoryManager), api.getPlugin());
        new RInventoryTask(inventoryManager).runTaskTimer(api.getPlugin(), 0, 1);
        Bukkit.getPluginManager().registerEvents(this, api.getPlugin());
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] DynamicInventoryManager >> Registered Events");

        /* HostConfig Inventories */
        addInventory("api.host", new IHost(api));
        addInventory("api.hostConfig", new IHostConfig(api));
        addInventory("api.hostScenarios", new IHostScenarios(api));
        addInventory("api.hostTimers", new IHostTimers(api));
        addInventory("api.hostCustomConfigs", new IHostCustomConfigs(api));
        addInventory("api.startInventory", new IHostStartInventory(api));
        addInventory("api.deathInventory", new IHostDeathInventory(api));
        addInventory("api.hostInventories", new IHostInventories(api));
        Bukkit.getLogger().log(Level.INFO, "[RqndomUHC] DynamicInventoryManager >> Registered API inventories");
    }

    @Override
    public void addInventory(String inventoryName, RInventory inventory) {
        inventories.addObject(inventoryName, inventory);
        players.put(inventory, new HashSet<>());
    }

    @Override
    public void removeInventory(String inventoryName) {
        inventories.removeKey(inventoryName);
    }

    @Override
    public void removeInventory(RInventory inventory) {
        inventories.removeObject(inventory);
    }

    @Override
    public void openInventory(String inventoryName, Player player) {
        Object inventory = inventories.getObject(inventoryName);
        if (inventory instanceof RInventory)
            openInventory((RInventory) inventory, player);
    }

    @Override
    public void openInventory(RInventory inventory, Player player) {
        if (inventory == null)
            return;

        players.get(inventory).add(player);
        player.openInventory(inventory.getInventory());
    }


    @Override
    public void onInventoryClose(Player player) {
        if (players.values().stream().noneMatch(playerS -> playerS.contains(player)))
            return;

        Optional<RInventory> result = players.entrySet().stream().filter(target -> target.getValue().contains(player)).map(Map.Entry::getKey).findFirst();

        if (!result.isPresent())
            return;

        players.get(result.get()).remove(player);
    }


    @Override
    public void updateInventory(String inventoryName) {
        updateInventory((RInventory) inventories.getObject(inventoryName));
    }

    @Override
    public void updateInventory(RInventory inventory) {
        if (inventory == null)
            return;

        Set<Player> players = this.players.get(inventory);

        for (Player player : players)
            player.updateInventory();
    }

    @Override
    public RInventory getInventory(String inventoryName) {
        return (RInventory) inventories.getObject(inventoryName);
    }

    @Override
    public String getInventoryKey(RInventory inventory) {
        return inventories.getKey(inventory);
    }

    @Override
    public HashMap<String, RInventory> getInventories() {
        return (HashMap<String, RInventory>) inventories.castObjects(RInventory.class);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        onInventoryClose((Player) event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeave(PlayerQuitEvent event) {
        onInventoryClose((Player) event.getPlayer());
    }

}
