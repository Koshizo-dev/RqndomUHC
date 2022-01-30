package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.rqndomuhc.tasks.teleportation.TGenerate;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.ITask;
import org.bukkit.Bukkit;

public class TPreparation implements ITask {

    final String taskName = "api.preparation";
    final UHCAPI api;
    int immunityDuration;
    boolean doesStop = false;

    public TPreparation(UHCAPI api) {
        this.api = api;
        immunityDuration = (int) api.getRules().getGameInfos().getObject("api.immunityDuration");
        api.getGameTaskManager().setGameState("GAME_IMMUNITY");
    }

    @Override
    public void loop() {
        if (doesStop)
            return;
        if (immunityDuration > 0) {
            immunityDuration--;
            if (immunityDuration == 0)
                api.getGameTaskManager().setGameState("GAME_PREPARATION");
            return;
        }

        int remainingTime = (int) api.getRules().getGameInfos().getObject("api.preparationDuration");
        api.getRules().getGameInfos().addObject("api.preparationDuration", remainingTime - 1);

        if (remainingTime <= 30 && remainingTime > 0)
            Bukkit.broadcastMessage(((String) api.getGameMessages().getObject("api.preparationEndingIn")).replaceAll("%time%", String.valueOf(remainingTime)));

        if (remainingTime == 0) {
            new TGenerate(api.getWorldManager().getMeetupWorld(), 250, api.getGamePlayers(), 1853,  999, api);
            doesStop = true;
        }
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

}
