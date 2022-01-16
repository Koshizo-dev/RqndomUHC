package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.rqndomuhc.utils.MBuilder;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.RValue;
import io.rqndomhax.uhcapi.world.IWorldManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WorldManager implements IWorldManager {

    private final RValue worlds = new RValue();
    private World meetup;
    private World preparation;
    private Location lobby;
    private final Set<World> deleteOnDisable = new HashSet<>();
    private final UHCAPI api;

    public WorldManager(UHCAPI api) {
        World def = Bukkit.getWorlds().get(0);
        lobby = new Location(def,0, def.getHighestBlockYAt(0, 0), 0);
        preparation = def;
        meetup = def;
        this.api = api;
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
    public void generatePreparationWorld() {
        // TODO
    }

    @Override
    public void generateDefaultEndGameLobby() {
        // TODO
    }

    @Override
    public void setEndGameLobby(Location location) {
        // TODO
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
    public void generateDefaultLobby(Location center, int radius) {
        new MBuilder(Material.GRAY_STAINED_GLASS, radius, center, true, false, 235, api.getPlugin());
        setLobby(center);
    }

    @Override
    public void destroyDefaultLobby(Location center, int radius) {
        new MBuilder(Material.AIR, radius, center, true, false, 235, api.getPlugin());
    }

    @Override
    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    @Override
    public Location getLobby() {
        return lobby;
    }

    @Override
    public HashMap<String, World> getWorlds() {
        return (HashMap<String, World>) worlds.castObjects(World.class);
    }

}
