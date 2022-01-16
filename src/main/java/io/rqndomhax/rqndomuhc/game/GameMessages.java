/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.uhcapi.utils.RValue;

public class GameMessages extends RValue {

    public GameMessages() {
        addObject("api.player_dead", "The player %player% is dead !");
        addObject("api.player_revive", "The player %player% has been revived !");
    }
}
