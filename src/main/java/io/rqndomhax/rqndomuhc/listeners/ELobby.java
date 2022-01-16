package io.rqndomhax.rqndomuhc.listeners;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.IGamePlayer;
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
import org.bukkit.scheduler.BukkitRunnable;

public class ELobby implements Listener {

    private final UHCAPI api;

    public ELobby(UHCAPI api) {
        this.api = api;
    }

    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (!isLobby())
            return;
        event.getPlayer().teleport(api.getWorldManager().getLobby());
        new BukkitRunnable() {
            @Override
            public void run() {
                api.getInventories().openInventory("api.host", event.getPlayer());
            }
        }.runTaskLater(api.getPlugin(), 2);
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

        if (e.getTo().getY() > 220)
            return;

        IGamePlayer player = api.getGamePlayer(e.getPlayer().getUniqueId());
        if (player == null) return;
        e.setTo((Location) player.getPlayerInfos().getObject("platformLocation"));
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

    private boolean isLobby() {
        return api.getGameTaskManager().getGameState().startsWith("LOBBY");
    }
}
