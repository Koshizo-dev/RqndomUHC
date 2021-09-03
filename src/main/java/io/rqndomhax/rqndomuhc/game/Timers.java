/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.uhcapi.ITimers;

import java.util.HashMap;

public class Timers implements ITimers {

    HashMap<String, Object> timers = new HashMap<>();

    @Override
    public void addTimer(String timerName, Object timerValue) {
        timers.put(timerName, timerValue);
    }

    @Override
    public void removeTimer(String timerName) {
        timers.remove(timerName);
    }

    @Override
    public HashMap<String, Object> getTimers() {
        return timers;
    }
}
