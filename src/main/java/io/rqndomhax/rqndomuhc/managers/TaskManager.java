/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.managers;

import io.rqndomhax.rqndomuhc.tasks.TEpisode;
import io.rqndomhax.rqndomuhc.tasks.TStart;
import io.rqndomhax.rqndomuhc.tasks.TTeleportation;
import io.rqndomhax.uhcapi.events.GameStateEvent;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.game.IGameTask;
import io.rqndomhax.uhcapi.game.ITask;
import io.rqndomhax.uhcapi.utils.RValue;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaskManager extends BukkitRunnable implements IGameTask {

    final UHCAPI api;
    public boolean lastTaskFinished = true;
    final RValue gameInfos;
    private final List<ITask> beforeTasks = new ArrayList<>();
    private final List<ITask> afterTasks = new ArrayList<>();
    private final List<Class<? extends ITask>> tasks = new ArrayList<>();
    private ITask currentTask = null;
    private String gameState;

    public TaskManager(UHCAPI api) {
        this.api = api;
        this.gameInfos = new RValue();
        gameInfos.addObject("api.elapsedRawTime", 0);
        gameInfos.addObject("api.elapsedTime",0);
        gameInfos.addObject("api.episode",0);
        gameInfos.addObject("api.episodeLength", 20*60);
        gameInfos.addObject("api.hasGameStarted", false);
        setGameState("LOBBY");
        tasks.add(TStart.class);
        tasks.add(TTeleportation.class);
        beforeTasks.add(new TEpisode(api));
        runTaskTimer(api.getPlugin(), 0, 20);
    }

    @Override
    public void run() {
        runTasks(beforeTasks);
        if (lastTaskFinished) {
            try {
                if (!startNextTask())
                    cancel();
            } catch (Exception e) {
                e.printStackTrace();
                Bukkit.broadcastMessage("[RqndomUHC] TaskManager >> An error happened during the initialization of a GameTask, please contact a dev !");
                Bukkit.broadcastMessage("[RqndomUHC] GameTask >> " + tasks.get(0));
                cancel();
                return;
            }
        }
        else
            if (currentTask != null)
                currentTask.loop();
        getGameInfos().addObject("api.elapsedRawTime", (Integer) getGameInfos().getObject("api.elapsedRawTime") + 1);
        runTasks(afterTasks);
    }

    @Override
    public List<Class<? extends ITask>> getTasks() {
        return tasks;
    }

    @Override
    public ITask getCurrentTask() {
        return currentTask;
    }

    @Override
    public RValue getGameInfos() {
        return gameInfos;
    }

    @Override
    public boolean startNextTask() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (tasks.isEmpty())
            return false;
        currentTask = (ITask) tasks.get(0).getDeclaredConstructors()[0].newInstance(api);
        tasks.remove(0);
        this.lastTaskFinished = false;
        return true;
    }

    @Override
    public void endCurrentTask() {
        this.lastTaskFinished = true;
    }

    @Override
    public void setGameState(String gameState) {
        this.gameState = gameState;
        Bukkit.getPluginManager().callEvent(new GameStateEvent(gameState));
    }

    @Override
    public String getGameState() {
        return gameState;
    }

    private void runTasks(Collection<ITask> tasks) {
        for (ITask task : tasks)
            task.loop();
    }
}
