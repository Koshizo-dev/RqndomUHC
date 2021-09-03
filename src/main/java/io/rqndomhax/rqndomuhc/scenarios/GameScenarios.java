/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.scenarios;

import io.rqndomhax.uhcapi.GameValue;
import io.rqndomhax.uhcapi.IScenarios;
import org.bukkit.event.Listener;

import java.util.*;
import java.util.stream.Collectors;

public class GameScenarios extends GameValue implements IScenarios{

    @Override
    public Set<Listener> getScenarios() {
        return new HashSet<>(getObjects().values()).stream().map(object -> (Listener) object).collect(Collectors.toSet());
    }

    @Override
    public void registerScenario(String name, Listener listener) {
        addObject(name, listener);
    }

    @Override
    public void unregisterScenario(String name) {
        removeObject(name);
    }

    @Override
    public void unregisterScenario(Listener listener) {
        removeObject(listener);
    }
}
