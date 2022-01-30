/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.scenarios;

import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.game.IScenario;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.Material;
import org.bukkit.event.Listener;

public class SMeetup extends RValue implements Listener, IScenario {

    public SMeetup() {
        addObject("item", Material.CLOCK);
        addObject("author", "RqndomHax");
        addObject("name", "Meetup");
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

    @Override
    public RValue getScenarioInfos() {
        return this;
    }
}
