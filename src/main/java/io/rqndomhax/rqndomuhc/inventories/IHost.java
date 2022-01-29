package io.rqndomhax.rqndomuhc.inventories;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHost extends RInventory {

    public IHost(UHCAPI api) {
        super(api, IInfos.MAIN_HOST_NAME, 9*6);
        IInfos.placeInvBorders(this.getInventory());
        this.setItem(4, IInfos.MAIN_HOST_CONFIGS, openCustomConfigs());
        this.setItem(11, IInfos.MAIN_HOST_SCENARIOS, openScenariosConfig());
        this.setItem(15, IInfos.MAIN_HOST_INVENTORIES, openInventoriesConfig());
        this.setItem(22, IInfos.MAIN_HOST_PLUGIN, openPluginsConfig());
        this.setItem(38, IInfos.MAIN_HOST_TIMERS, openTimerConfig());
        this.setItem(42, IInfos.MAIN_HOST_HOST, openHostConfig());
        refreshInventory();
    }

    @Override
    public void refreshInventory() {
        if (!getApi().getGameTaskManager().getGameState().equals("LOBBY_START"))
            this.setItem(49, IInfos.MAIN_HOST_START, onButtonClick(false));
        else
            this.setItem(49, IInfos.MAIN_HOST_STOP, onButtonClick(true));
        super.refreshInventory();
    }

    private Consumer<InventoryClickEvent> openScenariosConfig() {
        return e -> {
            getApi().getInventories().openInventory("api.hostScenarios", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> openPluginsConfig() {
        return e -> {
            getApi().getInventories().openInventory("api.hostPlugins", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> openCustomConfigs() {
        return e -> {
            if (!getApi().getHostManager().isHost(e.getWhoClicked())) {
                e.getWhoClicked().sendMessage((String) getApi().getGameMessages().getObject("api.onlyHostCommand"));
                ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1f, 2f);
            }
            getApi().getInventories().openInventory("api.hostCustomConfigs", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> openHostConfig() {
        return e -> {
            getApi().getInventories().openInventory("api.hostConfig", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> openTimerConfig() {
        return e -> {
            getApi().getInventories().openInventory("api.hostTimers", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> openInventoriesConfig() {
        return e -> {
            getApi().getInventories().openInventory("api.hostInventories", (Player) e.getWhoClicked());
        };
    }

    private Consumer<InventoryClickEvent> onButtonClick(boolean isStarting) {
        return e -> {
            if (isStarting) {
                getApi().getGameTaskManager().setGameState("LOBBY");
                this.setItem(49, IInfos.MAIN_HOST_START, onButtonClick(false));
                getApi().getInventories().updateInventory(this);
                return;
            }
            if (Bukkit.getOnlinePlayers().size() != getApi().getRules().getRolesManager().getActiveRoles().size()) {
                e.getWhoClicked().sendMessage((String) getApi().getGameMessages().getObject("api.gameStartNeedMorePlayer"));
                ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return;
            }
            if (!getApi().getRules().getRolesManager().activeRolesDifferentTeam()) {
                e.getWhoClicked().sendMessage((String) getApi().getGameMessages().getObject("api.gameStartNeedAnotherTeam"));
                ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return;
            }
            getApi().getGameTaskManager().setGameState("LOBBY_START");
            this.setItem(49, IInfos.MAIN_HOST_STOP, onButtonClick(true));
            getApi().getInventories().updateInventory(this);
        };
    }
}
