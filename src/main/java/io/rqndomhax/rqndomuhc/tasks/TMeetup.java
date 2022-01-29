package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.ITask;

public class TMeetup implements ITask {

    final String taskName = "api.meetup";
    final UHCAPI api;
    int immunityDuration;

    public TMeetup(UHCAPI api) {
        this.api = api;
        api.getGameTaskManager().setGameState("GAME_IMMUNITY");
        immunityDuration = (int) api.getRules().getGameInfos().getObject("api.immunityDuration");
    }

    @Override
    public void loop() {
        if (immunityDuration > 0) {
            immunityDuration--;
            if (immunityDuration == 0)
                api.getGameTaskManager().setGameState("GAME_MEETUP");
        }
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

}
