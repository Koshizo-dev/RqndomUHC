package io.rqndomhax.rqndomuhc.commands;

import io.rqndomhax.uhcapi.UHCAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.misc.resources.Messages;

public class CHost implements CommandExecutor {

    final UHCAPI api;

    public CHost(UHCAPI api) {
        this.api = api;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (sender instanceof Player && (!api.getHostManager().isCoHost(sender) && !api.getHostManager().isHost(sender))) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.onlyCoHostCommand"));
            return true;
        }

        if (args.length <= 1)
            return false;

        switch(args[0].toLowerCase()) {
            case "ban":
                return onPlayerBan(sender, args);
            case "unban":
                return onPlayerUnban(sender, args);
            case "kick":
                return onPlayerKick(sender, args);
            case "promote":
                return onPromoteCommand(sender, args);
            case "demote":
                return onDemoteCommand(sender, args);
            case "say:":
                return onSayCommand(sender, args);
            case "set":
                return onSetCommand(sender, args);
            default:
                return false;
        }
    }

    // TODO Implement
    private boolean onPlayerBan(CommandSender sender, String[] args) {
        return true;
    }

    // TODO Implement
    private boolean onPlayerUnban(CommandSender sender, String[] args) {
        return true;
    }

    // TODO Implement
    private boolean onPlayerKick(CommandSender sender, String[] args) {

        return true;
    }

    private boolean onPromoteCommand(CommandSender sender, String[] args) {
        if (api.getHostManager().isCoHost(sender)) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.onlyHostCommand"));
            return false;
        }
        if (args.length == 1) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.hostPromoteUsage"));
            return false;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(((String) api.getGameMessages().getObject("api.playerDoesNotExist")).replaceAll("%player%", args[1]));
            return false;
        }
        if (api.getHostManager().isCoHost(player) || api.getHostManager().isHost(player)) {
            sender.sendMessage(((String) api.getGameMessages().getObject("api.cannotPromotePlayer")).replaceAll("%player%", player.getName()));
            return false;
        }
        api.getHostManager().addCoHost(player);
        sender.sendMessage(((String) api.getGameMessages().getObject("api.promotedSuccessfully")).replaceAll("%player%", player.getName()));
        player.sendMessage((String) api.getGameMessages().getObject("api.nowCoHost"));
        return true;
    }

    private boolean onDemoteCommand(CommandSender sender, String[] args) {
        if (api.getHostManager().isCoHost(sender)) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.onlyHostCommand"));
            return false;
        }
        if (args.length == 1) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.hostDemoteUsage"));
            return false;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(((String) api.getGameMessages().getObject("api.playerDoesNotExist")).replaceAll("%player%", args[1]));
            return false;
        }
        if (!api.getHostManager().isCoHost(player) || api.getHostManager().isHost(player)) {
            sender.sendMessage(((String) api.getGameMessages().getObject("api.cannotDemotePlayer")).replaceAll("%player%", player.getName()));
            return false;
        }
        api.getHostManager().removeCoHost(player);
        sender.sendMessage(((String) api.getGameMessages().getObject("api.demotedSuccessfully")).replaceAll("%player%", player.getName()));
        player.sendMessage((String) api.getGameMessages().getObject("api.notAnymoreCoHost"));
        return true;
    }

    private boolean onSetCommand(CommandSender sender, String[] args) {
        if (api.getHostManager().isCoHost(sender)) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.onlyHostCommand"));
            return false;
        }

        if (args.length == 1) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.hostSetUsage"));
            return false;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(((String) api.getGameMessages().getObject("api.playerDoesNotExist")).replaceAll("%player%", args[1]));
            return false;
        }
        api.getHostManager().setHost(player);
        sender.sendMessage(((String) api.getGameMessages().getObject("api.hostSetSuccessfully")).replaceAll("%player%", player.getName()));
        player.sendMessage((String) api.getGameMessages().getObject("api.nowHost"));
        return true;
    }

    private boolean onSayCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.hostSayUsage"));
            return false;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1 ; i < args.length ; stringBuilder.append(args[i]).append(" "), i++);
        Bukkit.broadcastMessage("\n" + api.getGameMessages().getObject("api.hostSayPrefix") + stringBuilder);
        Bukkit.broadcastMessage("");

        return true;
    }
}
