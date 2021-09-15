/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.uhcapi.scenarios.RScenario;
import io.rqndomhax.uhcapi.scenarios.RScenariosManager;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class ScenariosManager implements RScenariosManager {

    RValue activeScenarios = new RValue();
    RValue scenarios = new RValue();
    final GameManager gameManager;

    public ScenariosManager(GameManager gameManager) {
        this.gameManager = gameManager;
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
    public void activateScenario(RScenario scenario) {
        activeScenarios.addObject(scenarios.getKey(scenario), scenario);
    }

    public void activateScenario(String scenarioKey) {
        activeScenarios.addObject(scenarioKey, scenarios.getObject(scenarioKey));
    }

    @Override
    public void deactivateScenario(RScenario scenario) {
        activeScenarios.removeObject(scenario);
    }

    @Override
    public void deactivateScenario(String scenarioKey) {
        activeScenarios.removeKey(scenarioKey);
    }

    @Override
    public HashMap<String, RScenario> getActiveScenarios() {
        return (HashMap<String, RScenario>) activeScenarios.castObjects(RScenario.class);
    }

    @Override
    public void enableScenarios() {
        for (RScenario scenario : getActiveScenarios().values())
            enableScenario(scenario);
    }

    @Override
    public void enableScenario(RScenario scenario) {
        scenario.init();
        if (scenario instanceof Listener)
            Bukkit.getPluginManager().registerEvents((Listener) scenario, gameManager.plugin);
    }

    @Override
    public void enableScenario(String scenarioKey) {
        if (activeScenarios.getObjects().containsKey(scenarioKey))
            enableScenario((RScenario) activeScenarios.getObject(scenarioKey));
    }

    @Override
    public void disableScenarios() {
        for (RScenario scenario : getActiveScenarios().values())
            disableScenario(scenario);
    }

    @Override
    public void disableScenario(RScenario scenario) {
        scenario.destroy();
        if (scenario instanceof Listener)
            HandlerList.unregisterAll((Listener) scenario);
    }

    @Override
    public void disableScenario(String scenarioKey) {
        if (activeScenarios.getObjects().containsKey(scenarioKey))
            disableScenario((RScenario) activeScenarios.getObject(scenarioKey));
    }


}
