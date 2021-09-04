/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.uhcapi.game.RTask;

public class TEpisode implements RTask {

    final String taskName;
    final TaskManager taskManager;
    int episode = 0;

    public TEpisode(TaskManager taskManager, String taskName) {
        this.taskManager = taskManager;
        this.taskName = taskName;
    }

    public TEpisode(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.taskName = "default";
    }

    @Override
    public void loop() {
        if (episode == 0)
            return;
        int remaining = ((Integer) (taskManager.getGameManager().getRules().getTimers().getObject("io.rqndomhax.rqndomuhc.episode_length")) * taskManager.episode) - (taskManager.elapsedTime);

        if (remaining == 30) {
            // 30 seconds left before new episode
        }

        if (remaining == 0) {
            // 0 seconds left before new episode
        }
    }

    @Override
    public String getTaskName() {
        return null;
    }
}
