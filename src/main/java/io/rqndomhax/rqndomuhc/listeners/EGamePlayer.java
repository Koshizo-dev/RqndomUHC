package io.rqndomhax.rqndomuhc.listeners;

import io.rqndomhax.rqndomuhc.game.GamePlayer;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.events.GamePlayerAddEvent;
import io.rqndomhax.uhcapi.events.GamePlayerRemoveEvent;
import io.rqndomhax.uhcapi.game.IGamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EGamePlayer implements Listener {

    private final UHCAPI api;

    public EGamePlayer(UHCAPI api) {
        this.api = api;
    }

    @EventHandler
    private void onPlayerLoginEvent(PlayerLoginEvent event) {
        if (!api.getGameTaskManager().getGameState().equals("LOBBY_START"))
            return;
        if (event.getPlayer().isOp()) {
            api.getGameTaskManager().setGameState("LOBBY");
            api.getHostManager().sendToHost(((String) api.getGameMessages().getObject("api.operatorCancelledGameStart")).replaceAll("%player%", event.getPlayer().getName()));
        }
        else {
            event.setKickMessage((String) api.getGameMessages().getObject("api.loginWhileGameStart"));
            event.setResult(PlayerLoginEvent.Result.KICK_FULL);
            api.getHostManager().sendToHost(((String) api.getGameMessages().getObject("api.playerTriedJoiningGameStart")).replaceAll("%player%", event.getPlayer().getName()));
        }
    }

    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event) {
        if (!isLobby())
            return;

        /* GamePlayerAdd */
        GamePlayerAddEvent playerAdd = new GamePlayerAddEvent(new GamePlayer(event.getPlayer().getUniqueId(), event.getPlayer().getName()));
        Bukkit.getPluginManager().callEvent(playerAdd);
        if (!playerAdd.isCancelled())
            api.getGamePlayers().add(playerAdd.getGamePlayer());

        event.getPlayer().teleport(api.getWorldManager().getLobby());
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        if (!isLobby())
            return;
        IGamePlayer gamePlayer = api.getGamePlayer(event.getPlayer().getUniqueId());
        if (gamePlayer == null)
            return;

        /* GamePlayerRemove */
        GamePlayerRemoveEvent playerRemoved = new GamePlayerRemoveEvent(gamePlayer);
        Bukkit.getPluginManager().callEvent(playerRemoved);
        if (!playerRemoved.isCancelled())
            api.getGamePlayers().remove(playerRemoved.getGamePlayer());

        if (api.getHostManager().getStartInventory().equals(event.getPlayer().getUniqueId()))
            api.getHostManager().setStartInventory(null);
        if (api.getHostManager().getDeathInventory().equals(event.getPlayer().getUniqueId()))
            api.getHostManager().setDeathInventory(null);

    }

    private boolean isLobby() {
        return api.getGameTaskManager().getGameState().startsWith("LOBBY");
    }
}
