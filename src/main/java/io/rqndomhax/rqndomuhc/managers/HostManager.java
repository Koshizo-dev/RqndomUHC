/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.uhcapi.game.RHostManager;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class HostManager extends RValue implements RHostManager {

    public HostManager() {
        addObject("coHosts", new HashSet<>());
        addObject("host", UUID.fromString("9f7be940-8a94-497b-a963-f4af0691c005"));
    }

    @Override
    public void setHost(Object object) {
        addObject("host", object);
    }


    @Override
    public boolean isHost(Object object) {
        return isIn(object, getObject("host"));
    }


    @Override
    @SuppressWarnings("unchecked")
    public boolean isCoHost(Object object) {
        Object target = getObject("coHosts");
        return ((Set<Object>) target).stream().anyMatch(targetObj -> isIn(object, targetObj));
    }


    @Override
    @SuppressWarnings("unchecked")
    public void addCoHost(Object object) {
        ((Set<Object>) getObject("coHosts")).add(object);
    }


    @Override
    @SuppressWarnings("unchecked")
    public void removeCoHost(Object object) {
        ((Set<Object>) getObject("coHosts")).remove(object);
    }

    private boolean isIn(Object object, Object target) {
        // Check by player
        if (object instanceof Player) {
            // Check by player's uuid
            if (((Player) object).getUniqueId().equals(target))
                return true;
            // Check by player's name
            return ((Player) object).getName().equals(target);
        }

        // Check by Object instance
        return object.equals(target);
    }

}
