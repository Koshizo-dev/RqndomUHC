package io.rqndomhax.rqndomuhc.listeners;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.events.GameEndEvent;
import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.game.IRole;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ERole implements Listener {

    final UHCAPI api;

    public ERole(UHCAPI api) {
        this.api = api;
    }

    @EventHandler
    private void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return;
        if (!api.getGameTaskManager().getGameState().startsWith("GAME"))
            return;
        if (!(event.getEntity() instanceof Player))
            return;
        IGamePlayer gamePlayer = api.getGamePlayer((Player) event.getEntity());
        if (gamePlayer == null)
            return;

        if (event.getDamager() instanceof Player) {
            IGamePlayer tmp = api.getGamePlayer((Player) event.getDamager());
            if (tmp != null) {
                for (IGamePlayer target : api.getGamePlayers())
                    if (target.getPlayerInfos().getObject("role") instanceof IRole)
                        ((IRole) target.getPlayerInfos().getObject("role")).onPlayerAttacked(tmp, gamePlayer);
            }
        }

        for (IGamePlayer target : api.getGamePlayers())
            if (target.getPlayerInfos().getObject("role") instanceof IRole)
                ((IRole) target.getPlayerInfos().getObject("role")).onPlayerAttacked(event.getDamager(), gamePlayer);
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        IGamePlayer gamePlayer = api.getGamePlayer(event.getEntity());
        if (gamePlayer == null)
            return;
        gamePlayer.setAlive(false);
        for (IGamePlayer player : api.getGamePlayers()) {
            Object role = player.getPlayerInfos().getObject("role");
            if (role instanceof IRole)
                ((IRole) role).onPlayerDeath(gamePlayer);
        }
    }

}
