/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.scenarios;

import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.game.IScenario;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.Material;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.LeavesDecayEvent;

public class SAppleFamine extends RValue implements Listener, IScenario {

    public SAppleFamine() {
        addObject("item", Material.APPLE);
        addObject("author", "WatDaSpark");
        addObject("name", "Apple Famine");
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
