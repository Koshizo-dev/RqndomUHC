package io.rqndomhax.rqndomuhc.utils;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.HostItemStack;
import io.rqndomhax.uhcapi.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

public abstract class Defaults {

    public static UUID HOST_UNIQUE_ID = UUID.fromString("9f7be940-8a94-497b-a963-f4af0691c005");
    public static String PLAYER_DEATH = "The player %player% is dead !";

    public static ItemStack[] HOST_LOBBY_INVENTORY(UHCAPI api) {
        ItemStack[] inventory = new ItemStack[40];
        for (int i = 0; i < 40; inventory[i++] = null); // Set all to null

        inventory[4] = new HostItemStack(new ItemBuilder(Material.COMMAND_BLOCK).setName(ChatColor.DARK_RED + "Game Settings").toItemStack(), e -> {
            api.getInventories().openInventory("api.host", e.getPlayer());
        }, new HashSet<>(Arrays.asList(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK)), true);

        return (inventory);
    }

}
