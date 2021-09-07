/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.utils;

import io.rqndomhax.uhcapi.utils.RDynamicInventory;
import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.*;

public class DynamicInventoryManager extends RValue implements RDynamicInventory, Listener {

    private final HashMap<RInventory, Set<Player>> players = new HashMap<>();

    @Override
    public void addInventory(String inventoryName, RInventory inventory) {
        addObject(inventoryName, inventory);
        players.put(inventory, new HashSet<>());
    }


    @Override
    public void openInventory(String inventoryName, Player player) {
        openInventory((RInventory) getObject(inventoryName), player);
    }


    @Override
    public void openInventory(RInventory inventory, Player player) {
        if (inventory == null)
            return;

        players.get(inventory).add(player);
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
        updateInventory((RInventory) getObjects().get(inventoryName));
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
        return (RInventory) getObject(inventoryName);
    }

    @Override
    public String getInventoryName(RInventory inventory) {
        return getKey(inventory);
    }

    @Override
    public HashMap<String, RInventory> getInventories() {
        return null;
        // TODO
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        onInventoryClose((Player) event.getPlayer());
    }

}
