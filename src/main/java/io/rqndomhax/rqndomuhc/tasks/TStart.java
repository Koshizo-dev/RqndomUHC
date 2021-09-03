/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.uhcapi.GameTask;

public class TStart implements GameTask {

    private final TaskManager mainTask;
    String taskName = "io.rqndomhax.rqndomuhc.startTask";
    int remainingTime = 0;

    public TStart(TaskManager mainTask) {
        this.mainTask = mainTask;
        if (mainTask == null)
            return;
        mainTask.lastTaskFinished = false;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null)
            return;

        if (remainingTime-- == 0)
            mainTask.lastTaskFinished = true;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }
}
