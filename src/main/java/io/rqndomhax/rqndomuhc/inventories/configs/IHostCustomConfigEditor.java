package io.rqndomhax.rqndomuhc.inventories.configs;


import io.rqndomhax.rqndomuhc.inventories.IInfos;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.HostConfig;
import io.rqndomhax.uhcapi.utils.ItemBuilder;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHostCustomConfigEditor extends RInventory {

    private final HostConfig selected;

    public IHostCustomConfigEditor(UHCAPI api, HostConfig selected) {
        super(api, selected.getName(), 9 * 6);
        this.selected = selected;
        updateInventory();
    }

    private void updateInventory() {

        IInfos.placeInvBorders(getInventory());

        setItem(4, new ItemBuilder(Material.GOLD_BLOCK)
                .setName(selected.getName())
                .setLore(ChatColor.GOLD + "ID: " + selected.getFilePath().replaceAll("configs/", "").replaceAll(".cfg", ""))
                .addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1)
                .hideEnchants()
                .toItemStack());

        int[] bars = new int[]{3, 5, 48, 50};

        for (Integer i : bars)
            setItem(i, IInfos.BARS);

        setItem(49, IInfos.RETURN_ITEM, e -> {
            getApi().getInventories().openInventory("api.host", (Player) e.getWhoClicked());
        });

        setItem(20, IInfos.HOST_RENAME);
        //TODO setItem(24, IInfos.HOST_ICONS, updateIcon());
        setItem(31, IInfos.HOST_DELETE, delete());
    }

    private Consumer<InventoryClickEvent> delete() {
        return e -> {
            getApi().getHostConfigManager().deleteConfig(selected);
            getApi().getInventories().getInventory("api.hostCustomConfigs").refreshInventory();
            getApi().getInventories().openInventory("api.hostCustomConfigs", (Player) e.getWhoClicked());
        };
    }
}
