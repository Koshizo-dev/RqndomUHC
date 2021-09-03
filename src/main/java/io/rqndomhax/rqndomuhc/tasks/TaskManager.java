/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.rqndomuhc.game.GameManager;
import io.rqndomhax.uhcapi.GameTask;
import io.rqndomhax.uhcapi.IGameTask;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class TaskManager extends BukkitRunnable implements IGameTask {

    private final GameManager gameManager;
    public boolean lastTaskFinished = true;
    private final HashMap<String, GameTask> beforeTasks = new HashMap<>();
    private final HashMap<String, GameTask> afterTasks = new HashMap<>();
    private final List<Class<? extends GameTask>> tasks = new ArrayList<>();
    private GameTask currentTask = null;
    int elapsedRawTime = 0;
    public int elapsedTime = 0;
    public int episode = 0;

    public TaskManager(GameManager gameManager) {
        this.gameManager = gameManager;
        tasks.add(TStart.class);
        beforeTasks.put("io.rqndomhax.rqndomuhc.check_episode", new TEpisode(this));
        runTaskTimer(gameManager.getPlugin(), 0, 20);
    }

    @Override
    public void run() {
        runTasks(beforeTasks.values());
        if (lastTaskFinished) {
            try {
                if (!startNextTask())
                    cancel();
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        else
        if (currentTask != null)
            currentTask.loop();
        elapsedRawTime++;
        runTasks(afterTasks.values());
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    @Override
    public List<Class<? extends GameTask>> getTasks() {
        return tasks;
    }

    @Override
    public GameTask getCurrentTask() {
        return currentTask;
    }

    @Override
    public int getEpisode() {
        return episode;
    }

    @Override
    public int getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public int getElapsedRawTime() {
        return elapsedRawTime;
    }

    @Override
    public boolean startNextTask() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (tasks.isEmpty())
            return false;
        currentTask = (GameTask) tasks.get(0).getDeclaredConstructors()[0].newInstance(this);
        tasks.remove(0);
        return true;
    }

    private void runTasks(Collection<GameTask> tasks) {
        for (GameTask task : tasks)
            task.loop();
    }
}
