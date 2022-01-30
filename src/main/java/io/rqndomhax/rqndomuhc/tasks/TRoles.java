/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.ITask;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.Bukkit;

public class TRoles implements ITask {

    final String taskName;
    final UHCAPI api;
    final RValue infos;
    boolean hasInit = false;
    boolean doesStop = false;

    public TRoles(UHCAPI api, RValue infos) {
        this.api = api;
        this.taskName = "api.roles";
        this.infos = infos;
        infos.addObject("api.roleRemaining", -1);
    }

    @Override
    public void loop() {
        if (doesStop || (boolean) infos.getObject("api.hasRoleBeenAttributed")) {
            doesStop = true;
            return;
        }
        if (!api.getGameTaskManager().getGameState().startsWith("GAME"))
            return;
        if (!hasInit) {
            infos.addObject("api.roleRemaining", api.getRules().getGameInfos().getObject("api.rolesAnnounce"));
            hasInit = true;
        }

        int remaining = (int) infos.getObject("api.roleRemaining");
        infos.addObject("api.roleRemaining", remaining - 1);

        if (remaining == 30 || remaining <= 10 && remaining > 0)
            Bukkit.broadcastMessage(((String) api.getGameMessages().getObject("api.rolesAttributingIn")).replaceAll("%time%", String.valueOf(remaining)));

        if (remaining == 0) {
            infos.addObject("api.hasRoleBeenAttributed", true);
            doesStop = true;
            api.getRules().getRolesManager().dispatchRoles(api.getGamePlayers());
        }
    }

    @Override
    public String getTaskName() {
        return taskName;
    }
}
