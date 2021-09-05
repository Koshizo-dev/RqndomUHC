/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.rqndomuhc.managers.TaskManager;
import io.rqndomhax.uhcapi.game.RTask;
import io.rqndomhax.uhcapi.scenarios.RScenario;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class TTeleportation implements RTask {

    final String taskName;
    final TaskManager taskManager;
    int remainingTime;

    public TTeleportation(TaskManager taskManager, String taskName) {
        this.taskManager = taskManager;
        this.taskName = taskName;
        remainingTime = (Integer) taskManager.getGameManager().getRules().getTimers().getObject("io.rqndomhax.rqndomuhc.teleportation_duration");
    }

    public TTeleportation(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.taskName = "default";
        remainingTime = (Integer) taskManager.getGameManager().getRules().getTimers().getObject("io.rqndomhax.rqndomuhc.teleportation_duration");
    }

    @Override
    public void loop() {
        if (remainingTime-- == 0) {
            // TODO break platforms etcetc
            taskManager.getGameManager().getRules().getScenariosManager().getScenarios().values().forEach(this::registerScenario);
        }
    }

    @Override
    public String getTaskName() {
        return null;
    }

    private void registerScenario(RScenario scenario) {
        scenario.init();
        if (scenario instanceof Listener)
            Bukkit.getPluginManager().registerEvents((Listener) scenario, taskManager.getGameManager().getPlugin());
    }
}
