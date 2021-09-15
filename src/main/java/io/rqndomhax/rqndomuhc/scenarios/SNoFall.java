/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.scenarios;

import io.rqndomhax.uhcapi.game.RGamePlayer;
import io.rqndomhax.uhcapi.scenarios.RScenario;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SNoFall extends RValue implements Listener, RScenario {

    public SNoFall() {
        addObject("item", Material.STONE);
        addObject("author", "RqndomHax");
        addObject("name", "NoFall");
    }

    @Override
    public void init() {
    }

    @Override
    public void init(RGamePlayer gamePlayer) {
    }

    @Override
    public void destroy() {

    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
            return;
        if (!(event.getEntity() instanceof Player))
            return;
        event.setCancelled(true);
    }

    @Override
    public RValue getScenarioInfos() {
        return this;
    }
}
