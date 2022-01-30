/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.tasks.teleportation;

import io.rqndomhax.rqndomuhc.game.GamePlayer;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.game.IScenario;
import io.rqndomhax.uhcapi.game.ITask;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Set;

public class TTeleportation implements ITask {

    final String taskName = "api.teleportation";
    final UHCAPI api;
    int remainingTime;

    public TTeleportation(UHCAPI api) {
        this.api = api;
        remainingTime = (Integer) api.getRules().getGameInfos().getObject("api.teleportationDuration");
        api.getGameTaskManager().setGameState("LOBBY_TELEPORTATION");
    }

    @Override
    public void loop() {
        Bukkit.broadcastMessage("Starting in " + remainingTime + " seconds");
        remainingTime--;
        if (remainingTime == 0) {
            api.getRules().getScenariosManager().getScenarios().values().forEach(this::registerScenario);
            api.getGameTaskManager().endCurrentTask();
            api.getGameTaskManager().getGameInfos().addObject("api.episode", 1); // We start the first episode
            removePlatform(api.getGamePlayers());
        }
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    private void registerScenario(IScenario scenario) {
        scenario.init();
        if (scenario instanceof Listener)
            Bukkit.getPluginManager().registerEvents((Listener) scenario, api.getPlugin());
    }

    public static void removePlatform(Set<IGamePlayer> players) {

        for (IGamePlayer gamePlayer : players) {
            if (gamePlayer == null) continue;

            Location center = (Location) gamePlayer.getPlayerInfos().getObject("platformLocation");

            World world = center.getWorld();
            for (double z = center.getZ() - 2 ; z < center.getZ() + 3 ; z++) {
                for (double x = center.getX() - 2 ; x < center.getX() + 3 ; x++) {
                    Block block = world.getBlockAt(new Location(world, x, center.getY() - 1, z));
                    block.setType(Material.AIR);
                    block.getState().update();
                }
            }

            Player player = gamePlayer.getPlayer();
            if (player != null)
                player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 1);
        }
    }
}
