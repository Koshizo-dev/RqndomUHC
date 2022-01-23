/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.uhcapi.events.GameHostChangeEvent;
import io.rqndomhax.uhcapi.managers.IHostManager;
import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class HostManager extends RValue implements IHostManager {

    final HashMap<JavaPlugin, RInventory> pluginConfigInventory = new HashMap<>();

    public HostManager() {
        addObject("coHosts", new HashSet<>());
        addObject("host", UUID.fromString("9f7be940-8a94-497b-a963-f4af0691c005"));
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
    public void setHost(Object object) {
        UUID newHost = retrieveUUID(object);
        GameHostChangeEvent event = new GameHostChangeEvent((UUID) getObject("host"), newHost);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled())
            addObject("host", retrieveUUID(object));
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
        ((Set<Object>) getObject("coHosts")).add(retrieveUUID(object));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void removeCoHost(Object object) {
        ((Set<Object>) getObject("coHosts")).remove(retrieveUUID(object));
    }

    @Override
    public HashMap<JavaPlugin, RInventory> getPluginConfigInventory() {
        return pluginConfigInventory;
    }

    @Override
    public RInventory getConfigInventory(JavaPlugin plugin) {
        return pluginConfigInventory.get(plugin);
    }

    UUID retrieveUUID(Object object) {
        if (object instanceof UUID)
            return (UUID) object;
        if (object instanceof Player)
            return ((Player) object).getUniqueId();
        if (object instanceof OfflinePlayer)
            return ((OfflinePlayer) object).getUniqueId();
        if (object instanceof String && Bukkit.getPlayer((String) object) != null)
            return Bukkit.getPlayer((String) object).getUniqueId();
        return null;
    }

}
