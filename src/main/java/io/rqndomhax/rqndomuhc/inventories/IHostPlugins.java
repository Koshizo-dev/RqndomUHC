package io.rqndomhax.rqndomuhc.inventories;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.ItemBuilder;
import io.rqndomhax.uhcapi.utils.inventory.PageController;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import io.rqndomhax.uhcapi.utils.inventory.RInventoryData;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IHostPlugins extends RInventory {

    public IHostPlugins(UHCAPI api) {
        super(api, IInfos.MAIN_HOST_NAME, 9*6);

        int[] bars = new int[]{3, 5, 19, 28, 25, 34, 48, 50};
        for (Integer i : bars)
            setItem(i, IInfos.BARS);

        setItem(4, new ItemBuilder(IInfos.MAIN_HOST_HOST.clone()).addUnsafeEnchantment(Enchantment.DURABILITY, 1).hideEnchants().toItemStack());
        IInfos.placeInvBorders(getInventory());
        setItem(49, IInfos.RETURN_ITEM, e -> {
            getApi().getInventories().openInventory("api.host", (Player) e.getWhoClicked());
        });

        refreshInventory();
    }

    @Override
    public void refreshInventory() {
        int[] slots = new int[]{12, 13, 14, 20, 21, 23, 24, 29, 30, 31, 32, 33, 39, 40, 41};

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

        for (JavaPlugin plugin : getApi().getHostManager().getPluginsConfigInventory().keySet())
            items.add(new RInventoryData(new ItemBuilder(Material.ENCHANTED_BOOK).setName(plugin.getName()).toItemStack(), openConfig(plugin)));
        return items;
    }

    private Consumer<InventoryClickEvent> openConfig(JavaPlugin plugin) {
        return e ->  {
            getApi().getInventories().openInventory(getApi().getHostManager().getPluginConfigInventory(plugin), (Player) e.getWhoClicked());
        };
    }

}
