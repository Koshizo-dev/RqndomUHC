package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.world.RWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WorldManager extends RValue implements RWorldManager {

    public WorldManager() {
        try {
            createWorld("io.rqndomhax.rqndomuhc.world.meetup", new File("Meetup"), new File("original/Meetup"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public World createWorld(String key, File newDir, File file) throws IOException {
        if (file == null || !file.exists() || !file.isDirectory())
            return null;

        FileUtils.copyDirectory(file, newDir);
        if (!newDir.exists())
            return null;

        World newWorld = new WorldCreator(newDir.getPath()).createWorld();

        addObject(key, newWorld);

        return newWorld;
    }

    @Override
    public World createWorld(String key, File newDir, String filePath) throws IOException {
        return createWorld(key, newDir, new File(filePath));
    }

    @Override
    public void deleteWorld(String key, boolean saveChunks) throws IOException {
        deleteWorld(getWorld(key), saveChunks);
    }

    @Override
    public void deleteWorld(World world, boolean saveChunks) throws IOException {
        if (world == null)
            return;

        File worldFolder = world.getWorldFolder();
        Bukkit.unloadWorld(world, saveChunks);
        FileUtils.deleteDirectory(worldFolder);
        removeObject(world);
    }

    @Override
    public World getWorld(String key) {
        Object result = getObjects().get(key);
        if (result == null)
            return null;
        return (World) result;
    }

    @Override
    public HashMap<String, World> getWorlds() {
        return null;
        // TODO
    }
}
