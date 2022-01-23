package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.uhcapi.managers.IHostConfigManager;
import io.rqndomhax.uhcapi.utils.FileManager;
import io.rqndomhax.uhcapi.utils.HostConfig;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class HostConfigManager implements IHostConfigManager {

    public static final List<HostConfig> configurations = new ArrayList<>();
    private static FileManager manager;

    public HostConfigManager(FileManager fileManager, File dataFolder) {
        if (fileManager == null)
            return;

        File configs = new File(dataFolder.getPath(), "configs/");

        if (!configs.exists() && !configs.mkdirs())
            return;

        manager = fileManager;

        loadConfigs(dataFolder);
    }

    @Override
    public void loadConfigs(File dataFolder) {
        File[] entries = new File(dataFolder, "configs/").listFiles();

        if (entries == null)
            return;

        for (File entry : entries) {
            if (entry == null || !entry.exists() || entry.isDirectory())
                continue;
            if (entry.getName().endsWith(".cfg") && configurations.stream().noneMatch(c -> c.getFilePath().equals("configs/" + entry.getName())))
                loadConfig("configs/" + entry.getName());
        }
    }

    @Override
    public HostConfig loadConfig(String path) {
        if (path == null || manager == null)
            return null;
        YamlConfiguration config = manager.getConfig(path).get();

        if (config == null)
            return null;
        HostConfig tmp = new HostConfig(new RValue(), config.getString("configName"), path);

        tmp.getGameInfos().setObjects((HashMap<String, Object>) Objects.requireNonNull(config.getConfigurationSection("gameInfos")).getValues(false));
        configurations.add(tmp);
        return tmp;
    }

    @Override
    public void deleteConfig(HostConfig config) {
        if (manager == null || config == null)
            return;
        configurations.remove(config);
        manager.getConfig(config.getFilePath()).delete();
    }

    @Override
    public void saveConfig(HostConfig config, boolean save) {
        if (manager == null || config == null)
            return;
        if (!configurations.contains(config) && save)
            configurations.add(config);

        FileManager.Config configuration = manager.getConfig(config.getFilePath());
        configuration.set("gameInfos", config.getGameInfos().getObjects());
        configuration.save();
    }

}