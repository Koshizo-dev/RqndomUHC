/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.uhcapi.GameValue;
import io.rqndomhax.uhcapi.IRules;

public class GameRules implements IRules {

    String gameTitle;
    final GameManager gameManager;
    final GameValue gameTimers = new Timers();

    public GameRules(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public String getGameTitle() {
        return gameTitle;
    }

    @Override
    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    @Override
    public GameValue getTimers() {
        return gameTimers;
    }
}
