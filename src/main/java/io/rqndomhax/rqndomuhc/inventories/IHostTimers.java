package io.rqndomhax.rqndomuhc.inventories;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.IScenario;
import io.rqndomhax.uhcapi.utils.Banners;
import io.rqndomhax.uhcapi.utils.ItemBuilder;
import io.rqndomhax.uhcapi.utils.inventory.PageController;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import io.rqndomhax.uhcapi.utils.inventory.RInventoryData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class IHostTimers extends RInventory {

    public IHostTimers(UHCAPI api) {
        super(api, IInfos.MAIN_HOST_NAME, 9*6);
        refreshInventory();
    }

    @Override
    public void refreshInventory() {
        int[] bars = new int[]{3, 5, 48, 50};
        int[] row = new int[]{19, 20, 21, 23, 24, 25};

        for (Integer i : bars)
            setItem(i, IInfos.BARS);

        for (int i = 10 ; i < 17 ; setItem(i, IInfos.BARS), i++);
        for (int i = 37 ; i < 45 ; setItem(i, IInfos.BARS), i++);

        setItem(4, new ItemBuilder(IInfos.MAIN_HOST_HOST.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());

        for (int i = 0 ; i < 2 ; i++) {
            int n = 0;
            for (Banners banner : Banners.values()) {
                setItem(row[n]+(i*9), new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(retrieveItem(i, n)).toItemStack(), updateTimer(i, n));
                n++;
            }
        }

        updateRoleItem();
        updatePreparationItem();

        setItem(49, IInfos.RETURN_ITEM, e -> {
            getApi().getInventories().openInventory("api.host", (Player) e.getWhoClicked());
        });

        super.refreshInventory();
    }

    private String retrieveItem(int i, int n) {
        StringBuilder name = new StringBuilder();
        if (n < 3)
            name.append("- ");
        else
            name.append("+ ");
        if (n == 0 || n == 5)
            name.append("10 mins");
        if (n == 1 || n == 4)
            name.append("5 mins");
        if (n == 2 || n == 3)
            name.append("1 min");
        return (name.toString());
    }

    private void updateRoleItem() {
        if ((int) getApi().getRules().getGameInfos().getObject("api.rolesAnnounce") > 60)
            setItem(22, new ItemBuilder(IInfos.HOST_TIMER_ROLES.clone()).setName(ChatColor.GREEN + "Annonce des rôles: " + ChatColor.DARK_AQUA + (int) getApi().getRules().getGameInfos().getObject("api.rolesAnnounce") / 60 + ChatColor.GREEN + " mins").toItemStack());
        else
            setItem(22, new ItemBuilder(IInfos.HOST_TIMER_ROLES.clone()).setName(ChatColor.GREEN + "Annonce des rôles: " + ChatColor.DARK_AQUA + (int) getApi().getRules().getGameInfos().getObject("api.rolesAnnounce") / 60 + ChatColor.GREEN + " min").toItemStack());
    }

    private void updatePreparationItem() {
        if ((int) getApi().getRules().getGameInfos().getObject("api.preparationDuration") > 60)
            setItem(31, new ItemBuilder(IInfos.HOST_TIMER_TELEPORT.clone()).setName(ChatColor.GREEN + "Préparation: " + ChatColor.DARK_AQUA + (int) getApi().getRules().getGameInfos().getObject("api.preparationDuration") / 60 + ChatColor.GREEN + " mins").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());
        else
            setItem(31, new ItemBuilder(IInfos.HOST_TIMER_TELEPORT.clone()).setName(ChatColor.GREEN + "Préparation: " + ChatColor.DARK_AQUA + (int) getApi().getRules().getGameInfos().getObject("api.preparationDuration") / 60 + ChatColor.GREEN + " min").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());
    }

    private Consumer<InventoryClickEvent> updateTimer(int i, int n) {
        return e -> {

            if (i == 0) {
                if (n == 0)
                    getApi().getRules().getGameInfos().addObject("api.rolesAnnounce", Math.max((int) getApi().getRules().getGameInfos().getObject("api.rolesAnnounce") - 10 * 60, 60));
                if (n == 1)
                    getApi().getRules().getGameInfos().addObject("api.rolesAnnounce", Math.max((int) getApi().getRules().getGameInfos().getObject("api.rolesAnnounce") - 5 * 60, 60));
                if (n == 2)
                    getApi().getRules().getGameInfos().addObject("api.rolesAnnounce", Math.max((int) getApi().getRules().getGameInfos().getObject("api.rolesAnnounce") - 60, 60));
                if (n == 3)
                    getApi().getRules().getGameInfos().addObject("api.rolesAnnounce", Math.min((int) getApi().getRules().getGameInfos().getObject("api.rolesAnnounce") + 60, 120 * 60));
                if (n == 4)
                    getApi().getRules().getGameInfos().addObject("api.rolesAnnounce", Math.min((int) getApi().getRules().getGameInfos().getObject("api.rolesAnnounce") + 5 * 60, 120 * 60));
                if (n == 5)
                    getApi().getRules().getGameInfos().addObject("api.rolesAnnounce", Math.min((int) getApi().getRules().getGameInfos().getObject("api.rolesAnnounce") + 10 * 60, 120 * 60));

                updateRoleItem();
                getApi().getInventories().updateInventory(this);
                return;
            }

            if (n == 0)
                getApi().getRules().getGameInfos().addObject("api.preparationDuration", Math.max((int) getApi().getRules().getGameInfos().getObject("api.preparationDuration") - 10 * 60, 60));
            if (n == 1)
                getApi().getRules().getGameInfos().addObject("api.preparationDuration", Math.max((int) getApi().getRules().getGameInfos().getObject("api.preparationDuration") - 5 * 60, 60));
            if (n == 2)
                getApi().getRules().getGameInfos().addObject("api.preparationDuration", Math.max((int) getApi().getRules().getGameInfos().getObject("api.preparationDuration") - 60, 60));
            if (n == 3)
                getApi().getRules().getGameInfos().addObject("api.preparationDuration", Math.min((int) getApi().getRules().getGameInfos().getObject("api.preparationDuration") + 60, 120 * 60));
            if (n == 4)
                getApi().getRules().getGameInfos().addObject("api.preparationDuration", Math.min((int) getApi().getRules().getGameInfos().getObject("api.preparationDuration") + 5 * 60, 120 * 60));
            if (n == 5)
                getApi().getRules().getGameInfos().addObject("api.preparationDuration", Math.min((int) getApi().getRules().getGameInfos().getObject("api.preparationDuration") + 10 * 60, 120 * 60));
            updatePreparationItem();
            getApi().getInventories().updateInventory(this);
        };
    }

}
