/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.scenarios;

import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.game.IScenario;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SNoFall extends RValue implements Listener, IScenario {

    public SNoFall() {
        addObject("item", Material.STONE);
        addObject("author", "RqndomHax");
        addObject("name", "NoFall");
    }

    @Override
    public void init() {
    }

    @Override
    public void init(IGamePlayer gamePlayer) {
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
