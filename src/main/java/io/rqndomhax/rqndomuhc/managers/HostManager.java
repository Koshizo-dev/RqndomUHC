/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.rqndomuhc.utils.Defaults;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.events.GameCoHostAddEvent;
import io.rqndomhax.uhcapi.events.GameCoHostRemoveEvent;
import io.rqndomhax.uhcapi.events.GameHostChangeEvent;
import io.rqndomhax.uhcapi.managers.IHostManager;
import io.rqndomhax.uhcapi.utils.PlayerUtils;
import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class HostManager extends RValue implements IHostManager {

    final HashMap<JavaPlugin, RInventory> pluginConfigInventory = new HashMap<>();
    UUID startInventory;
    UUID deathInventory;
    final UHCAPI api;

    public HostManager(UHCAPI api) {
        addObject("coHosts", new HashSet<>());
        addObject("host", Defaults.HOST_UNIQUE_ID);
        addObject("hostLobbyInventory", Defaults.HOST_LOBBY_INVENTORY(api));
        this.api = api;
    }

    @Override
    public UUID getHost() {
        return (UUID) getObject("host");
    }

    @Override
    public String getHostName() {
        return Bukkit.getOfflinePlayer((UUID) getObject("host")).getName();
    }

    @Override
    public UUID getStartInventory() {
        return startInventory;
    }

    @Override
    public void setStartInventory(UUID startInventory) {
        this.startInventory = startInventory;
    }

    @Override
    public UUID getDeathInventory() {
        return deathInventory;
    }

    @Override
    public void setDeathInventory(UUID deathInventory) {
        this.deathInventory = deathInventory;
    }

    @Override
    public void setHost(Object object) {
        UUID oldHostUniqueId = (UUID) getObject("host");
        UUID newHostUniqueId = retrieveUUID(object);
        if (oldHostUniqueId.equals(newHostUniqueId)) // If the oldHost is the same as the newHost there is no need to continue
            return;

        /* Event */
        GameHostChangeEvent event = new GameHostChangeEvent(oldHostUniqueId, newHostUniqueId);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;

        addObject("host", newHostUniqueId); // Set the GameHost
        if (!api.getGameTaskManager().getGameState().equals("LOBBY")) // Lobby specific actions
            return;

        /* newHost */
        Player newHost = Bukkit.getPlayer(oldHostUniqueId);
        if (newHost != null) // If the player is connected, we have to give him his host inventory
            PlayerUtils.giveInventory(getHostLobbyInventory(), newHost);

        /* oldHost */
        Player oldHost = Bukkit.getPlayer(oldHostUniqueId);
        if (oldHost != null && !isCoHost(oldHost)) { // If the player is connected, and he's not a host anymore, we have to clear his inventory
            PlayerUtils.clearInventory(oldHost);
            oldHost.closeInventory();
        }

    }

    @Override
    public boolean isHost(Object object) {
        return getObject("host").equals(retrieveUUID(object));
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isCoHost(Object object) {
        Object target = getObject("coHosts");
        UUID targetUUID = retrieveUUID(object);
        return ((Set<Object>) target).stream().anyMatch(targetObj -> targetObj.equals(targetUUID));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addCoHost(Object object) {
        UUID newCoHost = retrieveUUID(object);

        if (isCoHost(newCoHost)) // There is no need to continue if the player is already a Co-Host
            return;

        /* Event */
        GameCoHostAddEvent event = new GameCoHostAddEvent(newCoHost);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;

        ((Set<Object>) getObject("coHosts")).add(retrieveUUID(object));

        if (!api.getGameTaskManager().getGameState().equals("LOBBY")) // Lobby specific actions
            return;
        Player host = Bukkit.getPlayer(newCoHost);
        if (host != null && !isHost(host)) { // If the player is connected, we have to give him his inventory
            PlayerUtils.giveInventory(getHostLobbyInventory(), host);
            PlayerUtils.clearInventory(host);
            host.closeInventory();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void removeCoHost(Object object) {
        UUID oldCoHost = retrieveUUID(object);

        if (!isCoHost(oldCoHost)) // There is no need to continue if the player is not a Co-Host
            return;

        /* Event */
        GameCoHostRemoveEvent event = new GameCoHostRemoveEvent(oldCoHost);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;

        ((Set<Object>) getObject("coHosts")).remove(retrieveUUID(object));

        if (!api.getGameTaskManager().getGameState().equals("LOBBY")) // Lobby specific actions
            return;
        Player host = Bukkit.getPlayer(oldCoHost);
        if (host != null && !isHost(host)) { // If the player is connected, and he's not a host anymore, we have to clear his inventory
            PlayerUtils.clearInventory(host);
            host.closeInventory();
        }
    }

    @Override
    public void sendToHost(String message) {
        Player host = Bukkit.getPlayer(getHost());
        Set<Object> coHosts = (Set<Object>) getObject("coHosts");
        if (host != null)
            host.sendMessage(message);
        for (Object coHost : coHosts) {
            UUID coHostUniqueId = retrieveUUID(coHost);
            if (coHostUniqueId == null)
                continue;
            Player tmp = Bukkit.getPlayer((UUID) coHost);
            if (tmp != null)
                tmp.sendMessage(message);
        }

    }

    @Override
    public void putPluginConfigInventory(JavaPlugin plugin, RInventory inventory) {
        pluginConfigInventory.put(plugin, inventory);
        api.getInventories().addInventory(UUID.randomUUID().toString().substring(0, 8), inventory);
        api.getInventories().getInventory("api.hostPlugins").refreshInventory();
        api.getInventories().updateInventory("api.hostPlugins");
    }

    @Override
    public HashMap<JavaPlugin, RInventory> getPluginsConfigInventory() {
        return pluginConfigInventory;
    }

    @Override
    public RInventory getPluginConfigInventory(JavaPlugin plugin) {
        return pluginConfigInventory.get(plugin);
    }

    @Override
    public ItemStack[] getHostLobbyInventory() {
        return (ItemStack[]) getObject("hostLobbyInventory");
    }

    @Override
    public void setHostLobbyInventory(ItemStack[] newInventory) {
        addObject("hostLobbyInventory", newInventory);
    }

    UUID retrieveUUID(Object object) {
        if (object instanceof UUID)
            return (UUID) object;
        if (object instanceof Player)
            return ((Player) object).getUniqueId();
        if (object instanceof OfflinePlayer)
            return ((OfflinePlayer) object).getUniqueId();
        if (object instanceof String && Bukkit.getPlayer((String) object) != null)
            return Objects.requireNonNull(Bukkit.getPlayer((String) object)).getUniqueId();
        return null;
    }

}
