package io.rqndomhax.rqndomuhc.inventories.inventory;

import io.rqndomhax.rqndomuhc.game.GameRules;
import io.rqndomhax.rqndomuhc.inventories.IInfos;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.PlayerUtils;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class IHostStartInventory extends RInventory {

    public IHostStartInventory(UHCAPI api) {
        super(api, IInfos.MAIN_HOST_NAME, 9*6);

        for (int i = 0 ; i < getInventory().getSize() ; setItem(i, null), i++);

        int[] orange = new int[]{0, 2, 6, 8};
        int[] black = new int[]{1, 7};

        for (Integer glass : orange)
            setItem(glass, IInfos.ORANGE_GLASS_BORDER);
        for (Integer glass : black)
            setItem(glass, IInfos.BLACK_GLASS_BORDER);

        for (int i = 3 ; i <= 5 ; setItem(i, IInfos.BARS), i += 2);

        setItem(4, IInfos.INVENTORIES_HOST_BEGINNING);

        for (int i = 49 ; i <= 51 ; setItem(i, IInfos.BARS), i++);

        setItem(52, IInfos.INVENTORIES_EDIT, editInventory());

        setItem(53, IInfos.RETURN_ITEM, e -> {
            api.getInventories().openInventory("api.hostInventories", (Player) e.getWhoClicked());
        });

    }

    @Override
    public void refreshInventory() {
        ItemStack[] items = (ItemStack[]) getApi().getRules().getGameInfos().getObject("api.startInventory");
        for (int slots = 0; slots < 36; slots++)
            setItem(slots + 9, items[slots]);
        setItem(45, items[36]);
        setItem(46, items[37]);
        setItem(47, items[38]);
        setItem(48, items[39]);
    }

    private Consumer<InventoryClickEvent> editInventory() {

        return e -> {

            if (getApi().getHostManager().getStartInventory() != null) {
                e.getWhoClicked().sendMessage((String) getApi().getGameMessages().getObject("api.inventoryAlreadyEdit"));
                return;
            }

            getApi().getHostManager().setStartInventory(e.getWhoClicked().getUniqueId());
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().setGameMode(GameMode.CREATIVE);
            PlayerUtils.giveInventory((ItemStack[]) getApi().getRules().getGameInfos().getObject("api.startInventory"), (Player) e.getWhoClicked());
            e.getWhoClicked().sendMessage((String) getApi().getGameMessages().getObject("api.inventoryEditing"));
        };
    }

}
