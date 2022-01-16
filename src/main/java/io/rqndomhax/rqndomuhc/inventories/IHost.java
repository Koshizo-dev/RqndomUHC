package io.rqndomhax.rqndomuhc.inventories;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHost extends RInventory {

    private final UHCAPI api;

    public IHost(UHCAPI api) {
        super(null, IInfos.MAIN_HOST_NAME, 9*6);
        this.api = api;
    }

    @Override
    public void refreshInventory() {
        IInfos.placeInvBorders(this.getInventory());
        this.setItem(4, IInfos.MAIN_HOST_CONFIGS, openHostConfig());
        this.setItem(11, IInfos.MAIN_HOST_SCENARIOS, openScenariosConfig());
        this.setItem(15, IInfos.MAIN_HOST_BORDER_CONFIG, openBorderConfig());
        this.setItem(21, IInfos.MAIN_HOST_INVENTORIES, openInventoriesConfig());
        this.setItem(23, IInfos.MAIN_HOST_WORLD, openWorldConfig());
        this.setItem(38, IInfos.MAIN_HOST_TIMERS, openTimerConfig());
        this.setItem(42, IInfos.MAIN_HOST_HOST, openHostConfig());
        super.refreshInventory();
    }

    private Consumer<InventoryClickEvent> openScenariosConfig() {
        return e -> {
            api.getInventories().openInventory("api.scenario", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> openHostConfig() {
        return e -> {
            api.getInventories().openInventory("api.hostConfig", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> openTimerConfig() {
        return e -> {
            api.getInventories().openInventory("api.hostConfig", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> openBorderConfig() {
        return e -> {
            api.getInventories().openInventory("api.hostConfig", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> openInventoriesConfig() {
        return e -> {
            api.getInventories().openInventory("api.hostConfig", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> openWorldConfig() {

        return e ->  {
            api.getInventories().openInventory("api.hostConfig", (Player) e.getWhoClicked());
        };
    }
}
