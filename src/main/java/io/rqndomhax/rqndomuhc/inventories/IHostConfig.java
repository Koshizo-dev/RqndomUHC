package io.rqndomhax.rqndomuhc.inventories;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.ItemBuilder;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class IHostConfig extends RInventory {


    public IHostConfig(UHCAPI api) {
        super(api, IInfos.MAIN_HOST_NAME, 9*6);
        refreshInventory();
    }

    @Override
    public void refreshInventory() {
        int[] bars = new int[]{3, 5, 48, 50};
        for (Integer i : bars)
            setItem(i, IInfos.BARS);

        setItem(4, new ItemBuilder(IInfos.MAIN_HOST_HOST.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());
        IInfos.placeInvBorders(getInventory());

        setItem(13, getSpectators(), changeSpectators());

        setItem(29, getBorderDisconnects(), changeBorderDisconnects());

        setItem(31, new ItemBuilder(Material.PLAYER_HEAD, 1, (byte) 3).setName("Host: " + getApi().getHostManager().getHostName()).setSkullOwner(getApi().getHostManager().getHostName()).toItemStack());

        setItem(33, getWhitelist(), changeWhitelist());

        setItem(49, IInfos.RETURN_ITEM, e -> {
            getApi().getInventories().openInventory("api.host", (Player) e.getWhoClicked());
        });
        super.refreshInventory();
    }

    private ItemStack getBorderDisconnects() {
        if ((boolean) getApi().getRules().getGameInfos().getObject("api.hasSpectatorsAfterBorder"))
            return new ItemBuilder(IInfos.HOST_SPECTATORS_AFTER_BORDER.clone()).setName("Déconnexion bordure " + ChatColor.GREEN + " ✔").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
        return new ItemBuilder(IInfos.HOST_SPECTATORS_AFTER_BORDER.clone()).setName("Déconnexion bordure " + ChatColor.DARK_RED + " ✘").toItemStack();
    }

    private Consumer<InventoryClickEvent> changeBorderDisconnects() {
        return e -> {
            getApi().getRules().getGameInfos().addObject("api.hasSpectatorsAfterBorder", !((boolean) getApi().getRules().getGameInfos().getObject("api.hasSpectatorsAfterBorder")));
            setItem(29, getBorderDisconnects(), changeBorderDisconnects());
            getApi().getInventories().updateInventory(this);
        };
    }

    private ItemStack getSpectators() {
        if ((boolean) this.getApi().getRules().getGameInfos().getObject("api.hasSpectators"))
            return new ItemBuilder(IInfos.HOST_SPECTATORS.clone()).setName("Spectateurs " + ChatColor.GREEN + " ✔").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
        return new ItemBuilder(IInfos.HOST_SPECTATORS.clone()).setName("Spectateurs " + ChatColor.DARK_RED + " ✘").toItemStack();
    }

    private Consumer<InventoryClickEvent> changeSpectators() {
        return e -> {
            getApi().getRules().getGameInfos().addObject("api.hasSpectators", !((boolean) getApi().getRules().getGameInfos().getObject("api.hasSpectators")));
            setItem(13, getSpectators(), changeSpectators());
            getApi().getInventories().updateInventory(this);
        };
    }

    private ItemStack getWhitelist() {
        if ((boolean) getApi().getRules().getGameInfos().getObject("api.hasWhitelist"))
            return new ItemBuilder(IInfos.HOST_WHITELIST.clone()).setName("Whitelist " + ChatColor.GREEN + " ✔").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
        return new ItemBuilder(IInfos.HOST_WHITELIST.clone()).setName("Whitelist " + ChatColor.DARK_RED + " ✘").toItemStack();
    }

    private Consumer<InventoryClickEvent> changeWhitelist() {
        return e -> {
            getApi().getRules().getGameInfos().addObject("api.hasWhitelist", !((boolean) getApi().getRules().getGameInfos().getObject("api.hasWhitelist")));
            setItem(33, getWhitelist(), changeWhitelist());
            getApi().getInventories().updateInventory(this);
        };
    }

}
