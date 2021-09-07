/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.scenarios;

import io.rqndomhax.uhcapi.scenarios.RScenario;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.Material;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Optional;
import java.util.Random;

public class SAppleFamine extends RValue implements Listener, RScenario {

    public SAppleFamine() {
        addObject("item", Material.APPLE);
        addObject("author", "WatDaSpark");
        addObject("name", "Apple Famine");
    }

    @Override
    public void init() {
    }

    @EventHandler
    public void onLeafDecay(LeavesDecayEvent event) {
        cancelEvent(event);
    }

    @EventHandler
    public void onBreakLeaves(BlockBreakEvent event) {
        if (event.getBlock() instanceof Leaves)
            cancelEvent(event);
    }

    private void cancelEvent(BlockEvent event) {
        ((Cancellable) event).setCancelled(true);
        event.getBlock().setType(Material.AIR);
        event.getBlock().getState().update();
    }

    @Override
    public RValue getScenarioInfos() {
        return this;
    }
}
