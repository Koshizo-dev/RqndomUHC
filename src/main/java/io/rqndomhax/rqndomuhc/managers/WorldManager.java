package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.world.IWorldManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class WorldManager implements IWorldManager {

    private RValue worlds = new RValue();
    private final Set<World> deleteOnDisable = new HashSet<>();

    @Override
    public World createWorld(String key, File newDir, File file) throws IOException {
        if (file == null || !file.exists() || !file.isDirectory())
            return null;

        FileUtils.copyDirectory(file, newDir);
        if (!newDir.exists())
            return null;

        World newWorld = new WorldCreator(newDir.getPath()).createWorld();

        worlds.addObject(key, newWorld);

        return newWorld;
    }

    @Override
    public World createWorld(String key, File newDir, String filePath) throws IOException {
        return createWorld(key, newDir, new File(filePath));
    }

    @Override
    public void deleteWorld(String key) throws IOException {
        deleteWorld(getWorld(key));
    }

    @Override
    public void deleteWorld(World world) throws IOException {
        if (world == null)
            return;

        File worldFolder = world.getWorldFolder();
        for (Player player : world.getPlayers())
            player.kickPlayer("RqndomUHC > World deleting");
        Bukkit.unloadWorld(world, false);
        FileUtils.deleteDirectory(worldFolder);
        worlds.removeObject(world);
    }

    @Override
    public World getWorld(String key) {
        Object result = worlds.getObjects().get(key);
        if (result == null)
            return null;
        return (World) result;
    }

    @Override
    public HashMap<String, World> getWorlds() {
        return (HashMap<String, World>) worlds.castObjects(World.class);
    }

}
