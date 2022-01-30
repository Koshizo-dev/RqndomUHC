package io.rqndomhax.rqndomuhc.tasks.teleportation;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.utils.Colors;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TGenerate extends BukkitRunnable {

    final List<Location> locations = new ArrayList<>();
    final Set<IGamePlayer> players;
    final UHCAPI api;
    final World world;
    double delta;
    double angle = 0;
    int radius;
    final int xCenter;
    final int zCenter;
    int i = 0;

    public TGenerate(World world, int size, Set<IGamePlayer> players, int xCenter, int zCenter, UHCAPI api) {
        this.world = world;
        this.players = players;
        this.xCenter = xCenter;
        this.zCenter = zCenter;
        this.api = api;
        delta = (2 * Math.PI) / players.size();
        radius = size / 2;
        runTaskTimer(api.getPlugin(), 0, 5);
    }

    @Override
    public void run() {

        if (players.size() == i) {
            new TTeleport(players, locations, api);
            cancel();
            return;
        }

        locations.add(new Location(world,
                xCenter + (radius * Math.sin(angle) + 0.500), 230, zCenter + (radius * Math.cos(angle) + 0.500)));
        angle += delta;

        placePlatform(locations.get(i));
        i++;
    }

    public static void placePlatform(Location center) {
        byte color = (byte) (new Random().nextInt(14) + 1);
        World world = center.getWorld();
        for (double z = center.getZ() - 2 ; z < center.getZ() + 3 ; z++) {
            for (double x = center.getX() - 2 ; x < center.getX() + 3 ; x++) {
                Block block = world.getBlockAt(new Location(world, x, center.getY() - 1, z));
                block.setType(Colors.getGlassById((new Random().nextInt(14))));
                block.getState().update();
            }
        }
    }
}
