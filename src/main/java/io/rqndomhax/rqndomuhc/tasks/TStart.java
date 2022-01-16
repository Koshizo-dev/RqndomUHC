/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.rqndomuhc.managers.TaskManager;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.ITask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TStart implements ITask {

    private final UHCAPI api;
    String taskName;
    int remainingTime = 15;
    boolean wasStarting = false;

    public TStart(UHCAPI api) {
        this.api = api;
        if (api.getGameTaskManager() == null)
            return;
        this.taskName = "startTask";
    }

    @Override
    public void loop() {
        if (api.getGameTaskManager() == null)
            return;

        if (!(api.getGameTaskManager().getGameInfos().getObject("api.gameState")).equals("LOBBY_START")) {
            if (wasStarting)
                remainingTime = 15; // We set back to the default start time if it has been canceled
            return;
        }

        wasStarting = true;
        Bukkit.broadcastMessage("Game is starting in " + remainingTime + " seconds");

        if (remainingTime-- == 0) {
            api.getGameTaskManager().endCurrentTask();
            init();
        }
    }

    private void init() {
        api.getRules().getScenariosManager().enableScenarios();
    }

    @Override
    public String getTaskName() {
        return taskName;
    }
}
