package io.rqndomhax.rqndomuhc.commands;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class CHostSaveInventory implements CommandExecutor {

    final UHCAPI api;

    public CHostSaveInventory(UHCAPI api) {
        this.api = api;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getLogger().log(Level.WARNING, (String) api.getGameMessages().getObject("api.onlyPlayerCommand"));
            return (false);
        }

        Player player = ((Player) sender);

        if (api.getHostManager().getStartInventory() != null && api.getHostManager().getStartInventory().equals(player.getUniqueId())) {
            PlayerUtils.saveInventory((ItemStack[]) api.getRules().getGameInfos().getObject("api.startInventory"), player, false);
            api.getHostManager().setStartInventory(null);
            player.sendMessage((String) api.getGameMessages().getObject("api.hostInventorySaved"));
            PlayerUtils.giveInventory(api.getHostManager().getHostLobbyInventory(), player);
            api.getInventories().getInventory("api.startInventory").refreshInventory();
            api.getInventories().openInventory("api.startInventory", player);
            return (true);
        }

        if (api.getHostManager().getDeathInventory() != null && api.getHostManager().getDeathInventory().equals(player.getUniqueId())) {
            PlayerUtils.saveInventory((ItemStack[]) api.getRules().getGameInfos().getObject("api.deathInventory"), player, false);
            api.getHostManager().setDeathInventory(null);
            player.sendMessage((String) api.getGameMessages().getObject("api.hostInventorySaved"));
            PlayerUtils.giveInventory(api.getHostManager().getHostLobbyInventory(), player);
            api.getInventories().getInventory("api.deathInventory").refreshInventory();
            api.getInventories().openInventory("api.deathInventory", player);
            return (true);
        }

        player.sendMessage((String) api.getGameMessages().getObject("api.notEditingAnyInventory"));
        return (false);
    }

}
