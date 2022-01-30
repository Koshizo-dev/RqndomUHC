package io.rqndomhax.rqndomuhc.commands;

import io.rqndomhax.rqndomuhc.inventories.enchant.IEnchant;
import io.rqndomhax.uhcapi.UHCAPI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CEnchant implements CommandExecutor {

    private final UHCAPI api;

    public CEnchant(UHCAPI api) {
        this.api = api;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (!(sender instanceof Player) || (!api.getHostManager().isCoHost(sender) && !api.getHostManager().isHost(sender))) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.onlyCoHostCommand"));
            return true;
        }
        if (!api.getGameTaskManager().getGameState().equals("LOBBY")) {
            sender.sendMessage((String) api.getGameMessages().getObject("api.notInGame"));
            return false;
        }

        Player player = (Player) sender;

        if ((api.getHostManager().getStartInventory() != null && api.getHostManager().getStartInventory().equals(player.getUniqueId()))
                || (api.getHostManager().getDeathInventory() != null && api.getHostManager().getDeathInventory().equals(player.getUniqueId()))) {
            if (player.getItemInHand() == null || player.getItemInHand().getType() == null || player.getItemInHand().getType().equals(Material.AIR)) {
                player.sendMessage((String) api.getGameMessages().getObject("api.needItemInHand"));
                return false;
            }
            player.openInventory(new IEnchant(player).getInventory());
            return true;
        }
        player.sendMessage((String) api.getGameMessages().getObject("api.notEditingAnyInventory"));
        return true;
    }

}
