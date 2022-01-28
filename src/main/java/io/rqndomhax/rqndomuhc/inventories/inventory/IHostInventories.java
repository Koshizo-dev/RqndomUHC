package io.rqndomhax.rqndomuhc.inventories.inventory;

import io.rqndomhax.rqndomuhc.inventories.IInfos;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.ItemBuilder;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHostInventories extends RInventory {

    public IHostInventories(UHCAPI api) {
        super(api, IInfos.MAIN_HOST_NAME, 6*9);

        IInfos.placeInvBorders(getInventory());

        for (int i = 3 ; i <= 5 ; setItem(i, IInfos.BARS), i += 2);

        setItem(4, new ItemBuilder(IInfos.MAIN_HOST_INVENTORIES.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());

        setItem(30, IInfos.INVENTORIES_HOST_BEGINNING, getStartInventory());

        setItem(31, IInfos.BARS);

        setItem(32, IInfos.INVENTORIES_HOST_DEATH, getDeathInventory());

        setItem(49, IInfos.RETURN_ITEM, e -> {
            getApi().getInventories().openInventory("api.host", (Player) e.getWhoClicked());
        });
    }

    private Consumer<InventoryClickEvent> getDeathInventory() {
        return e -> {
            getApi().getInventories().openInventory("api.deathInventory", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> getStartInventory() {
        return e -> {
            getApi().getInventories().openInventory("api.startInventory", (Player) e.getWhoClicked());
        };
    }

}
