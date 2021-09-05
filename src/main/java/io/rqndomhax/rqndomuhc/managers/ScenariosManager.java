/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.rqndomuhc.scenarios.SNoFall;
import io.rqndomhax.uhcapi.scenarios.RScenario;
import io.rqndomhax.uhcapi.scenarios.RScenariosManager;
import io.rqndomhax.uhcapi.utils.RValue;

import java.util.HashMap;

public class ScenariosManager extends RValue implements RScenariosManager {

    public ScenariosManager() {
        registerScenario("io.rqndomhax.rqndomuhc.scenarios.no_fall", new SNoFall());
    }

    @Override
    public HashMap<String, RScenario> getScenarios() {
        return (HashMap<String, RScenario>) castObjects(RScenario.class);
    }

    @Override
    public RScenario getScenario(String key) {
        return (RScenario) getObject(key);
    }

    @Override
    public void registerScenario(String name, RScenario scenario) {
        addObject(name, scenario);
    }

    @Override
    public void unregisterScenario(String name) {
        removeKey(name);
    }

    @Override
    public void unregisterScenario(RScenario scenario) {
        removeObject(scenario);
    }
}
