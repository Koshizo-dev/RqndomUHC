package io.rqndomhax.rqndomuhc.listeners;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.IGamePlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ELobby implements Listener {

    private final UHCAPI api;

    public ELobby(UHCAPI api) {
        this.api = api;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (!isLobby())
            return;
        event.getPlayer().teleport(api.getWorldManager().getLobby());
    }
    @EventHandler
    public void onBucket(PlayerBucketEmptyEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (isLobby())
            e.blockList().clear();
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (!e.isCancelled())
            e.setCancelled(isLobby());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
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
    private void onRain(WeatherChangeEvent e){
        if(e.toWeatherState())
            e.setCancelled(true);
    }

    private boolean isLobby() {
        return api.getGameTaskManager().getGameState().startsWith("LOBBY");
    }
}
