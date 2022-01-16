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

    private final RValue worlds = new RValue();
    private World meetup = null;
    private World preparation = null;
    private World lobby = null;
    private final Set<World> deleteOnDisable = new HashSet<>();

    public WorldManager() {
        lobby = Bukkit.getWorlds().get(0);
        preparation = lobby;
        meetup = lobby;
    }

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
    public World generateWorld(String world) {
        return null;
    }

    @Override
    public void setLobby(String key) {
        setLobby(getWorld(key));
    }

    @Override
    public void setLobby(World world) {
        this.lobby = world;
    }

    @Override
    public void setMeetupWorld(String key) {
        setMeetupWorld(getWorld(key));
    }

    @Override
    public void setMeetupWorld(World world) {
        this.meetup = world;
    }

    @Override
    public void setPreparationWorld(String key) {
        setPreparationWorld(getWorld(key));
    }

    @Override
    public void setPreparationWorld(World world) {
        this.preparation = world;
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
    public World getLobby() {
        return lobby;
    }

    @Override
    public World getMeetupWorld() {
        return meetup;
    }

    @Override
    public World getPreparationWorld() {
        return preparation;
    }

    @Override
    public HashMap<String, World> getWorlds() {
        return (HashMap<String, World>) worlds.castObjects(World.class);
    }

}
