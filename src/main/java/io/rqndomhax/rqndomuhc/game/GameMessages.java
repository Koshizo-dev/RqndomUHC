/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.uhcapi.GameValue;

public class GameMessages extends GameValue {

    public GameMessages() {
        addObject("io.rqndomhax.rqndomuhc.player_dead", "The player %player% is dead !");
        addObject("io.rqndomhax.rqndomuhc.player_revive", "The player %player% has been revived !");
    }
}
