package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.ITask;
import org.bukkit.Bukkit;

public class TPreparation implements ITask {

    final String taskName = "api.preparation";
    final UHCAPI api;
    int remainingTime;

    public TPreparation(UHCAPI api) {
        this.api = api;
        remainingTime = (Integer) api.getRules().getGameInfos().getObject("api.preparationDuration");
        api.getGameTaskManager().setGameState("GAME_PREPARATION");
        api.getWorldManager().getPreparationWorld().setPVP(true);
    }

    @Override
    public void loop() {
        if (--remainingTime == 0)
            api.getGameTaskManager().endCurrentTask();
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

}
