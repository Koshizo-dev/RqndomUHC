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
        if (api.getRules().getScenariosManager().getActiveScenarios().containsKey("api.meetup")) {
            new TGenerate(api.getWorldManager().getMeetupWorld(), 250, api.getGamePlayers(), 0, 0, api);
            api.getGameTaskManager().getTasks().remove(0); // remove preparation's teleportation task
            api.getGameTaskManager().getTasks().remove(0); // remove preparation task
        } else
            new TGenerate(api.getWorldManager().getPreparationWorld(), 250, api.getGamePlayers(), 0, 0, api);
        api.getRules().getScenariosManager().enableScenarios();
    }

    @Override
    public String getTaskName() {
        return taskName;
    }
}
