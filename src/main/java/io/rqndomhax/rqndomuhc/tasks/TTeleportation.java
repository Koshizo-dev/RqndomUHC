/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.rqndomuhc.managers.TaskManager;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.ITask;
import io.rqndomhax.uhcapi.scenarios.IScenario;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class TTeleportation implements ITask {

    final String taskName;
    final UHCAPI api;
    int remainingTime;

    public TTeleportation(UHCAPI api) {
        this.api = api;
        this.taskName = "api.teleportation";
        remainingTime = (Integer) api.getRules().getTimers().getObject("api.teleportation_duration");
    }

    @Override
    public void loop() {
        if (remainingTime-- == 0) {
            api.getRules().getScenariosManager().getScenarios().values().forEach(this::registerScenario);
            api.getGameTaskManager().endCurrentTask();
            api.getGameTaskManager().getGameInfos().addObject("api.episode", 1); // We start the first episode
        }
    }

    @Override
    public String getTaskName() {
        return null;
    }

    private void registerScenario(IScenario scenario) {
        scenario.init();
        if (scenario instanceof Listener)
            Bukkit.getPluginManager().registerEvents((Listener) scenario, api.getPlugin());
    }
}
