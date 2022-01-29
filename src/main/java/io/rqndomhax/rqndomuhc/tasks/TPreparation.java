package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.ITask;
import org.bukkit.Bukkit;

public class TPreparation implements ITask {

    final String taskName = "api.preparation";
    final UHCAPI api;
    int immunityDuration;
    int remainingTime;

    public TPreparation(UHCAPI api) {
        this.api = api;
        immunityDuration = (int) api.getRules().getGameInfos().getObject("api.immunityDuration");
        remainingTime = (int) api.getRules().getGameInfos().getObject("api.preparationDuration");
        api.getGameTaskManager().setGameState("GAME_IMMUNITY");
        api.getWorldManager().getPreparationWorld().setPVP(true);
    }

    @Override
    public void loop() {
        if (immunityDuration > 0) {
            immunityDuration--;
            if (immunityDuration == 0)
                api.getGameTaskManager().setGameState("GAME_PREPARATION");
            return;
        }

        if (--remainingTime == 0)
            api.getGameTaskManager().endCurrentTask();
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

}
