/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.rqndomuhc.tasks.teleportation.TGenerate;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.ITask;
import org.bukkit.Bukkit;

public class TStart implements ITask {

    private final UHCAPI api;
    String taskName;
    int remainingTime = 5; // TODO change to 15
    boolean wasStarting = false;

    public TStart(UHCAPI api) {
        this.api = api;
        if (api.getGameTaskManager() == null)
            return;
        this.taskName = "startTask";
    }

    @Override
    public void loop() {
        if (!(api.getGameTaskManager().getGameState().equals("LOBBY_START"))) {
            if (wasStarting)
                remainingTime = 5; // We set back to the default start time if it has been canceled // TODO CHANGE TO 15
            return;
        }

        wasStarting = true;
        if (remainingTime-- > 0)
            Bukkit.broadcastMessage("Game is starting in " + remainingTime + " seconds");

        if (remainingTime == 0)
            init();
    }

    private void init() {
        api.getRules().getScenariosManager().enableScenarios();
        new TGenerate(api.getWorldManager().getWorld("api.preparation"), 250, api.getGamePlayers(), 0, 0, api);
    }

    @Override
    public String getTaskName() {
        return taskName;
    }
}
