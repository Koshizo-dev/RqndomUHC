package io.rqndomhax.rqndomuhc.tasks.teleportation;

import io.rqndomhax.rqndomuhc.game.GamePlayer;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.utils.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TTeleport extends BukkitRunnable {

    final Iterator<IGamePlayer> players;
    final List<Location> locations;
    final int size;
    int i = 0;
    UHCAPI api;

    public TTeleport(Set<IGamePlayer> players, List<Location> locations, UHCAPI api) {
        this.players = players.iterator();
        this.locations = locations;
        size = players.size();
        this.api = api;
        runTaskTimer(api.getPlugin(), 0, 5);
    }

    @Override
    public void run() {
        if (!players.hasNext() || size - i == 0) {
            for (IGamePlayer gamePlayer : api.getGamePlayers()) {
                Player player = gamePlayer.getPlayer();
                if (player != null)
                    PlayerUtils.giveInventory((ItemStack[]) api.getRules().getGameInfos().getObject("api.startInventory"), player);
            }
            api.getGameTaskManager().endCurrentTask();
            cancel();
            return;
        }

        IGamePlayer gamePlayer = players.next();
        Location location = locations.get(0);

        if (locations.get(0) == null) return;

        if (gamePlayer == null)
            return;

        gamePlayer.getPlayerInfos().addObject("platformLocation", location);

        Player player = gamePlayer.getPlayer();

        // if (player == null)
            // MVillagers.createVillager(location, gamePlayer); // TODO create villager

        location.getChunk().load(true);

        player.teleport(locations.get(0));

        if (gamePlayer.isAlive())
            player.setGameMode(GameMode.SURVIVAL);

        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);

        locations.remove(location);

        i++;
    }
}
