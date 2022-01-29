package io.rqndomhax.rqndomuhc.tasks;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.events.GameEndEvent;
import io.rqndomhax.uhcapi.game.IGamePlayer;
import io.rqndomhax.uhcapi.game.IRole;
import io.rqndomhax.uhcapi.game.ITask;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TEndChecker implements ITask {

    UHCAPI api;

    public TEndChecker(UHCAPI api) {
        this.api = api;
    }

    private Set<IRole> getWinners(String teamName) {
        Set<IRole> winners = new HashSet<>();

        for (IGamePlayer player : api.getGamePlayers()) {
            Object tmp = player.getPlayerInfos().getObject("role");
            if (!(tmp instanceof IRole))
                continue;

            String name = (String) ((IRole) tmp).getRoleInfos().getObject("teamName");

            if (name.equals(teamName))
                winners.add((IRole) tmp);
        }
        return winners;
    }

    @Override
    public void loop() {
        if (!(api.getGameTaskManager().getGameState().startsWith("GAME")))
            return;
        IRole solo = null;
        String teamName = null;
        for (IGamePlayer player : api.getGamePlayers()) {
            if (!player.isAlive())
                continue;
            Object tmp = player.getPlayerInfos().getObject("role");
            if (!(tmp instanceof IRole))
                continue;

            String name = (String) ((IRole) tmp).getRoleInfos().getObject("teamName");

            if (teamName == null)
                teamName = name;

            if (name.equals("solo")) {
                System.out.println("solo = " + solo);
                if (solo == null)
                    solo = (IRole) tmp;
                if (!tmp.equals(solo))
                    return;
                System.out.println("team =  " + teamName);
            }

            if (!(teamName.equals(name)))
                return;
        }
        api.getGameTaskManager().setGameState("END");
        if (solo == null)
            Bukkit.getPluginManager().callEvent(new GameEndEvent(getWinners(teamName), teamName));
        else
            Bukkit.getPluginManager().callEvent(new GameEndEvent(new HashSet<>(Arrays.asList(solo)), teamName));
    }

    @Override
    public String getTaskName() {
        return "api.endChecker";
    }
}
