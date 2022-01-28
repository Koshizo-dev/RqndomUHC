package io.rqndomhax.rqndomuhc.utils;

import io.rqndomhax.uhcapi.events.GameBuildEvent;
import io.rqndomhax.uhcapi.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class MBuilder extends BukkitRunnable {

    final Material material;
    final int boundaries;
    final Location center;
    boolean updateData;
    boolean hasRoof;
    int maxY;
    double x;
    double z;

    public MBuilder(Material material, int boundaries, Location center, boolean updateData, boolean hasRoof, int maxY, JavaPlugin plugin) {
        this.material = material;
        this.boundaries = boundaries;
        this.center = center;
        this.updateData = updateData;
        this.hasRoof = hasRoof;
        this.maxY = maxY;
        x = center.getX() - boundaries;
        z = center.getZ() - boundaries;
        Bukkit.getPluginManager().callEvent(new GameBuildEvent(GameBuildEvent.Type.STARTED, "MBuilder", center));
        runTaskTimer(plugin, 0, 2);
    }

    @Override
    public void run() {
        for (int i = 0; i <= 150; i++) {
            if (!place()) {
                Bukkit.getPluginManager().callEvent(new GameBuildEvent(GameBuildEvent.Type.ENDED, "MBuilder", center));
                cancel();
            }
        }
    }

    private boolean place() {
        if (x == center.getX() + boundaries + 1 && z == center.getZ() + boundaries + 1)
            return false;

        if (z == center.getZ() + boundaries + 1) {
            x++;
            z = center.getZ() - boundaries;
        }

        if (hasRoof) {
            Block block = center.getWorld().getBlockAt(new Location(center.getWorld(), x, maxY, z));
            block.setType(material);
            block.getState().update();
        }

        if (z == center.getZ() - boundaries || z == center.getZ() + boundaries
                || x == center.getX() - boundaries || x == center.getX() + boundaries)
            for (int y = (int) center.getY(); y < maxY; y++) {
                Block block = center.getWorld().getBlockAt(new Location(center.getWorld(), x, y, z));
                block.setType(material);
                if (updateData)
                    block.setType(Colors.getGlassById((new Random().nextInt(14))));
                block.getState().update();
            }

        Block block = center.getWorld().getBlockAt(new Location(center.getWorld(), x, center.getY() - 1, z));
        block.setType(material);
        if (updateData)
            block.setType(Colors.getGlassById((new Random().nextInt(14))));
        block.getState().update();
        z++;
        return true;
    }

}