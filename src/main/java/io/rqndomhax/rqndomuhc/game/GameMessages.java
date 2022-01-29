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
        addObject("api.playerRevive", "The player %player% has been revived!");
        addObject("api.operatorCancelledGameStart", ChatColor.RED + "The operator %player% cancelled the game start timer!");
        addObject("api.playerTriedJoiningGameStart", ChatColor.RED + "The player %player% tried to join while the game was starting!");
        addObject("api.loginWhileGameStart", ChatColor.RED + "You are not able to join this game while it's starting!");
        addObject("api.gameStartNeedMorePlayer", ChatColor.RED + "There is not enough player to start the game!");
        addObject("api.gameStartNeedAnotherTeam", ChatColor.RED + "Every active role is in the same team !");
        addObject("api.serverLockedKickMessage", "The server is currently locked!");
        addObject("api.defaultConfigEdit", "You cannot edit the default host configuration!");
        addObject("api.inventoryAlreadyEdit", "This inventory is already being edited!");
        addObject("api.inventoryEditing", "You are editing the inventory");
        addObject("api.onlyPlayerCommand", "You have to be a player to execute this command!");
        addObject("api.hostInventorySaved", "You have successfully updated this inventory!");
        addObject("api.notEditingAnyInventory", "You are not editing any inventory!");
        addObject("api.playerNotConnected", "The player %player% is not connected on the server!");
        addObject("api.onlyCoHostCommand", "You have to be a co-host or higher to execute this command!");
        addObject("api.hostSayUsage", "Usage: /host say <message>");
        addObject("api.hostSetUsage", "Usage: /host set <player>");
        addObject("api.hostPromoteUsage", "Usage: /host promote <player>");
        addObject("api.hostDemoteUsage", "Usage: /host demote <player>");
        addObject("api.cannotDemotePlayer", "The player %player% cannot be demoted !");
        addObject("api.cannotPromotePlayer", "The player %player% cannot be promoted !");
        addObject("api.playerDoesNotExist", "The player %player% does not exist!");
        addObject("api.hostSayPrefix", ChatColor.RED + "Rqndom" + ChatColor.GOLD + "UHC");
        addObject("api.onlyHostCommand", "You have to be an host to execute this command!");
        addObject("api.hostSetSuccessfully", "You have successfully set the player %player% as the new game host!");
        addObject("api.demotedSuccessfully", "You have successfully demoted the player %player%!");
        addObject("api.promotedSuccessfully", "You have successfully promoted the player %player%!");
        addObject("api.nowHost", "You are now the new game host!");
        addObject("api.nowCoHost", "You are now part of the game co-hosts!");
        addObject("api.notAnymoreCoHost", "You are no longer part of the game co-hosts!");
    }

}
