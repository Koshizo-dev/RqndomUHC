/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.rqndomuhc.utils.Defaults;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.ChatColor;

public class GameMessages extends RValue {

    public GameMessages() {
        addObject("api.playerDeath", Defaults.PLAYER_DEATH);
        addObject("api.playerRevive", "The player %player% has been revived !");
        addObject("api.operatorCancelledGameStart", ChatColor.RED + "The operator %player% cancelled the game start timer !");
        addObject("api.playerTriedJoiningGameStart", ChatColor.RED + "The player %player% tried to join while the game was starting !");
        addObject("api.loginWhileGameStart", ChatColor.RED + "You are not able to join this game while it's starting !");
        addObject("api.gameStartNeedMorePlayer", ChatColor.RED + "There is not enough player to start the game !");
        addObject("api.gameStartNeedAnotherTeam", ChatColor.RED + "Every active role is in the same team !");
        addObject("api.serverLockedKickMessage", "The server is currently locked!");
    }

}
