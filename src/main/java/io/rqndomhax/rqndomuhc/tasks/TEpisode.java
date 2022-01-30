/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.ITask;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.Bukkit;

public class TEpisode implements ITask {

    final String taskName;
    final UHCAPI api;
    final RValue infos;

    public TEpisode(UHCAPI api, RValue infos) {
        this.api = api;
        this.taskName = "api.episode";
        this.infos = infos;
    }

    @Override
    public void loop() {
        if ((int) infos.getObject("api.episode") == 0)
            return;

        int remaining = (((int) infos.getObject("api.episodeLength")) * (int) infos.getObject("api.episode")) - (int) (infos.getObject("api.elapsedTime"));

        if (remaining == 30)
            Bukkit.broadcastMessage("30 seconds before the episode " + infos.getObject("api.episode") + 1);

        if (remaining == 0) {
            infos.addObject("api.episode", (int) infos.getObject("api.episode") + 1);
            Bukkit.broadcastMessage("Episode " + infos.getObject("api.episode") + " starting !");
        }
    }

    @Override
    public String getTaskName() {
        return null;
    }
}
