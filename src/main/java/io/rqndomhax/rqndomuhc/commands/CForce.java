package io.rqndomhax.rqndomuhc.commands;

import io.rqndomhax.rqndomuhc.tasks.TPreparation;
import io.rqndomhax.uhcapi.UHCAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CForce implements CommandExecutor {

    private final UHCAPI api;

    public CForce(UHCAPI api) {
        this.api = api;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player && (!api.getHostManager().isCoHost(sender) && !api.getHostManager().isHost(sender))) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.onlyCoHostCommand"));
            return true;
        }
        if (!api.getGameTaskManager().getGameState().startsWith("GAME")) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.notInGame"));
            return false;
        }

        if (args.length != 1 || args[0].equalsIgnoreCase("help"))
            return showHelp(sender);

        if (args[0].equalsIgnoreCase("meetup"))
            return forceTeleport(sender, api.getGameTaskManager().getGameState());
        if (args[0].equalsIgnoreCase("role"))
            return forceRole(sender);
        return showHelp(sender);
    }

    private boolean showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "\n----- " + ChatColor.RED + "Rqndom " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----\n");
        sender.sendMessage(ChatColor.DARK_AQUA + "/force " + ChatColor.GOLD + "meetup " + ChatColor.DARK_AQUA + ": to force the meetup.");
        sender.sendMessage(ChatColor.DARK_AQUA + "/force " + ChatColor.GOLD + "role " + ChatColor.DARK_AQUA + ": to force the role attribution.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "\n----- " + ChatColor.RED + "Rqndom " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----");
        sender.sendMessage("");
        return false;
    }

    private boolean forceRole(CommandSender sender) {
        if ((boolean) api.getGameTaskManager().getGameInfos().getObject("api.hasRoleBeenAttributed")) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.rolesAlreadyAttributed"));
            return false;
        }

        if ((int) api.getGameTaskManager().getGameInfos().getObject("api.roleRemaining") <= 10 && (int) api.getGameTaskManager().getGameInfos().getObject("api.roleRemaining") >= 0) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.rolesAlreadyAttributing"));
            return false;
        }

        api.getGameTaskManager().getGameInfos().addObject("api.roleRemaining", 10);
        return true;
    }

    private boolean forceTeleport(CommandSender sender, String gameState) {
        if (gameState.equals("LOBBY_TELEPORTATION")) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.alreadyTeleporting"));
            return false;
        }

        if (!gameState.equals("GAME_PREPARATION") && !api.getGameTaskManager().getTasks().contains(TPreparation.class) && !api.getGameTaskManager().getCurrentTask().getClass().equals(TPreparation.class)) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.alreadyMeetupWorld"));
            return false;
        }

        int remainingTime = (int) api.getRules().getGameInfos().getObject("api.preparationDuration");

        if (remainingTime <= 10) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.preparationAlreadyEnding"));
            return false;
        }

        api.getRules().getGameInfos().addObject("api.preparationDuration", 10);
        return true;
    }
}
