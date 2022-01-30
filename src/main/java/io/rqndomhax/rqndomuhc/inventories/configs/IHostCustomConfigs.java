package io.rqndomhax.rqndomuhc.inventories.configs;

import io.rqndomhax.rqndomuhc.inventories.IInfos;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.HostConfig;
import io.rqndomhax.uhcapi.utils.ItemBuilder;
import io.rqndomhax.uhcapi.utils.PlayerUtils;
import io.rqndomhax.uhcapi.utils.inventory.PageController;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import io.rqndomhax.uhcapi.utils.inventory.RInventoryData;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IHostCustomConfigs extends RInventory {

    public IHostCustomConfigs(UHCAPI api) {
        super(api, IInfos.MAIN_HOST_NAME, 9*6);

        int[] bars = new int[]{3, 5, 11, 15, 38, 40, 42, 48, 50};
        for (Integer i : bars)
            setItem(i, IInfos.BARS);

        setItem(4, new ItemBuilder(IInfos.MAIN_HOST_CONFIGS.clone()).addUnsafeEnchantment(Enchantment.DURABILITY, 1).hideEnchants().toItemStack());
        IInfos.placeInvBorders(getInventory());

        ItemStack stand = new ItemBuilder(Material.ARMOR_STAND).setName(" ").toItemStack();
        setItem(12, stand);
        setItem(14, stand);

        setItem(49, IInfos.RETURN_ITEM, e -> {
            getApi().getInventories().openInventory("api.host", (Player) e.getWhoClicked());
        });

        setItem(39, IInfos.HOST_CREATE, e -> {
            HostConfig newConfig = getApi().getRules().getHostConfig().duplicate();

            getApi().getHostConfigManager().saveConfig(newConfig, true);
            getApi().getRules().setHostConfig(newConfig);
            refreshInventory();
            getApi().getInventories().updateInventory(this);
        });

        setItem(41, IInfos.HOST_SAVE, e -> {
            HostConfig newConfig = getApi().getRules().getHostConfig();

            getApi().getHostConfigManager().saveConfig(newConfig, true);
        });

        refreshInventory();
    }

    @Override
    public void refreshInventory() {
        setItem(13, new ItemBuilder(Material.GOLD_INGOT)
                .setName(getApi().getRules().getHostConfig().getName())
                .setLore(ChatColor.GOLD + "ID: " + getApi().getRules().getHostConfig().getFilePath().replaceAll("configs/", "").replaceAll(".cfg", ""))
                .addUnsafeEnchantment(Enchantment.DURABILITY, 1)
                .hideEnchants()
                .toItemStack(), updateConfig(getApi().getRules().getHostConfig()));

        int[] slots = new int[]{19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
        for (Integer tmp : slots)
            setItem(tmp, null);

        setPageController(pageController -> {
            pageController.setBoard(slots);
            pageController.setItemStacks(generateBoard());
        });

        update(() -> {
            PageController pageController = getPageController();
            setItem(47, new ItemBuilder(Material.ARROW).setName("("+(pageController.getPage()+1)+"/"+pageController.getMaxPage()+")").toItemStack(), e -> {
                pageController.previousPage();
            });

            setItem(51, new ItemBuilder(Material.ARROW).setName("("+(pageController.getPage()+1)+"/"+pageController.getMaxPage()+")").toItemStack(), e -> {
                pageController.nextPage();
            });
        }, 1);

        super.refreshInventory();
    }

    private List<RInventoryData> generateBoard() {
        List<RInventoryData> items = new ArrayList<>();

        getApi().getHostConfigManager().loadConfigs(getApi().getPlugin().getDataFolder());
        for (HostConfig config : getApi().getHostConfigManager().getConfiguration()) {
            if (getApi().getRules().getHostConfig().getFilePath().equals(config.getFilePath()))
                continue;

            items.add(new RInventoryData(new ItemBuilder(Material.GOLD_NUGGET)
                    .setName(config.getName())
                    .setLore(ChatColor.GOLD + "ID: " + config.getFilePath().replaceAll("configs/", "").replaceAll(".cfg", ""))
                    .toItemStack(), updateConfig(config)));
        }
        return items;
    }

    private Consumer<InventoryClickEvent> updateConfig(HostConfig config) {
        return e -> {
            if (config.getName().equals("default") && !e.getClick().equals(ClickType.LEFT)) {
                ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                e.getWhoClicked().sendMessage((String) getApi().getGameMessages().getObject("api.defaultConfigEdit"));
                return;
            }

            if (e.getClick().equals(ClickType.CONTROL_DROP)) {
                getApi().getHostConfigManager().deleteConfig(config);
                refreshInventory();
                getApi().getInventories().updateInventory(this);
                return;
            }

            if (e.getClick().equals(ClickType.LEFT) && !config.getFilePath().equals(getApi().getRules().getHostConfig().getFilePath())) {
                config.getGameInfos().addObject("api.activeRoles", getApi().getRules().getRolesManager().getActiveRoles().keySet());
                config.getGameInfos().addObject("api.activeScenarios", getApi().getRules().getScenariosManager().getActiveScenarios().keySet());
                getApi().getRules().setHostConfig(config);

                /* Reset player editing config inventory */
                Player tmp = Bukkit.getPlayer(getApi().getHostManager().getStartInventory());
                Player tmp2 = Bukkit.getPlayer(getApi().getHostManager().getDeathInventory());
                getApi().getHostManager().setStartInventory(null);
                getApi().getHostManager().setDeathInventory(null);

                if (tmp != null) {
                    tmp.setGameMode(GameMode.ADVENTURE);
                    PlayerUtils.giveInventory(getApi().getHostManager().getHostLobbyInventory(), tmp);
                }

                if (tmp2 != null) {
                    tmp2.setGameMode(GameMode.ADVENTURE);
                    PlayerUtils.giveInventory(getApi().getHostManager().getHostLobbyInventory(), tmp2);
                }

                refreshInventory();
                getApi().getInventories().updateInventory(this);
                return;
            }

            if (e.getClick().equals(ClickType.RIGHT))
                e.getWhoClicked().openInventory(new IHostCustomConfigEditor(getApi(), config).getInventory());
        };
    }

}
