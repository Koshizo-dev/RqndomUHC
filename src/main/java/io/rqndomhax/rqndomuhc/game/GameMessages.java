/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.uhcapi.RValue;

public class GameMessages extends RValue {

    public GameMessages() {
        addObject("io.rqndomhax.rqndomuhc.player_dead", "The player %player% is dead !");
        addObject("io.rqndomhax.rqndomuhc.player_revive", "The player %player% has been revived !");
    }
}
