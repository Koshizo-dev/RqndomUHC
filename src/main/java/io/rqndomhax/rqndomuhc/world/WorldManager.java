package io.rqndomhax.rqndomuhc.world;

import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.world.RWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class WorldManager extends RValue implements RWorldManager {

    @Override
    public World createWorld(String key, File newDir, File file) throws IOException {
        if (file == null || !file.exists() || !file.isDirectory())
            return null;

        if (!newDir.createNewFile())
            return null;

        File worldDir = new File(newDir, file.getName());

        FileUtils.copyDirectory(file, worldDir);
        if (!worldDir.exists())
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

    public World getWorld(String key) {
        Object result = getObjects().get(key);
        if (result == null)
            return null;
        return (World) result;
    }

    private String getKey(World world) {
        return getObjects().entrySet().stream().filter(target -> target.getValue().equals(world)).map(Map.Entry::getKey).findAny().orElse(null);
    }

}
