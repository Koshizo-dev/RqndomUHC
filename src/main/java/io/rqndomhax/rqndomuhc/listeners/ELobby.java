package io.rqndomhax.rqndomuhc.listeners;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.utils.HostItemStack;
import io.rqndomhax.uhcapi.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class ELobby implements Listener {

    private final UHCAPI api;

    public ELobby(UHCAPI api) {
        this.api = api;
    }

    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (!isLobby())
            return;

        /* If the player is a host / co-host we are giving him his host's toolbar */
        if (api.getHostManager().isHost(event.getPlayer()) || api.getHostManager().isCoHost(event.getPlayer()))
            PlayerUtils.giveInventory(api.getHostManager().getHostLobbyInventory(), event.getPlayer());

        event.getPlayer().teleport(api.getWorldManager().getLobby());
    }
    @EventHandler
    private void onBucket(PlayerBucketEmptyEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    private void onBlockDestroy(BlockBreakEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    private void onBlockExplode(BlockExplodeEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    private void onEntityExplode(EntityExplodeEvent e) {
        if (isLobby())
            e.blockList().clear();
    }

    @EventHandler
    private void onBlockBurn(BlockBurnEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    private void onThrow(ProjectileLaunchEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    private void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    private void onMove(PlayerMoveEvent e) {
        if (!isLobby())
            return;

        if (Objects.requireNonNull(e.getTo()).getY() > 220)
            return;

        IGamePlayer player = api.getGamePlayer(e.getPlayer().getUniqueId());
        if (player == null) return;
        Object platformLocation = player.getPlayerInfos().getObject("platformLocation");
        if (platformLocation instanceof Location)
            e.setTo((Location) platformLocation);
        else
            e.setTo(api.getWorldManager().getLobby().clone());
    }

    @EventHandler
    private void onThunder(ThunderChangeEvent e){
        if(e.toThunderState())
            e.setCancelled(true);
    }

    @EventHandler
    private void onRain(WeatherChangeEvent e) {
        if (e.toWeatherState())
            e.setCancelled(true);
    }

    @EventHandler
    private void onHostItem(PlayerInteractEvent e) {
        if (!isLobby())
            return;

        if (!api.getHostManager().isHost(e.getPlayer()) && !api.getHostManager().isCoHost((e.getPlayer())))
            return;

        HostItemStack item = PlayerUtils.getHostItemStackInInventory(api.getHostManager().getHostLobbyInventory(), e.getItem());

        if (item == null) // If it's not a HostItemStack there is no need to continue
            return;

        if (!item.getActions().contains(e.getAction())) // If the action is not respected according to the HostItemStack we have to deny
            return;
        e.setCancelled(item.doesCancelAction());
        item.getItemEvent().accept(e); // Calls the consumer
    }

    private boolean isLobby() {
        return api.getGameTaskManager().getGameState().startsWith("LOBBY");
    }
}
