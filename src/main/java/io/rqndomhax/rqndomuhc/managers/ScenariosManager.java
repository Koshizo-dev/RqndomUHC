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

public class ScenariosManager implements RScenariosManager {

    RValue activeScenarios = new RValue();
    RValue scenarios = new RValue();

    public ScenariosManager() {
        registerScenario("io.rqndomhax.rqndomuhc.scenarios.no_fall", new SNoFall());
        registerScenario("io.rqndomhax.rqndomuhc.scenarios.no_fall", new SNoFall());
    }

    @Override
    public HashMap<String, RScenario> getScenarios() {
        return (HashMap<String, RScenario>) scenarios.castObjects(RScenario.class);
    }

    @Override
    public RScenario getScenario(String key) {
        return (RScenario) scenarios.getObject(key);
    }

    @Override
    public void registerScenario(String name, RScenario scenario) {
        scenarios.addObject(name, scenario);
    }

    @Override
    public void unregisterScenario(String scenarioKey) {
        scenarios.removeKey(scenarioKey);
        activeScenarios.removeKey(scenarioKey);
    }

    @Override
    public void unregisterScenario(RScenario scenario) {
        scenarios.removeObject(scenario);
        scenarios.removeObject(scenario);
    }

    @Override
    public void enableScenario(RScenario scenario) {
        activeScenarios.addObject(scenarios.getKey(scenario), scenario);
    }

    @Override
    public void enableScenario(String scenarioKey) {
        activeScenarios.addObject(scenarioKey, scenarios.getObject(scenarioKey));
    }

    @Override
    public void disableScenario(RScenario scenario) {
        activeScenarios.removeObject(scenario);
    }

    @Override
    public void disableScenario(String scenarioKey) {
        activeScenarios.removeKey(scenarioKey);
    }

    @Override
    public HashMap<String, RScenario> getActiveScenarios() {
        return (HashMap<String, RScenario>) activeScenarios.castObjects(RScenario.class);
    }
}
