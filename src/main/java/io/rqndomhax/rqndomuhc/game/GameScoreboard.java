/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.fastboard.FastBoard;
import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.IScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameScoreboard implements IScoreboard {

    final UHCAPI api;
    final HashMap<UUID, FastBoard> boards = new HashMap<>();

    public GameScoreboard(UHCAPI api) {
        this.api = api;
        runBoard();
    }

    private void runBoard() {

        AtomicBoolean doClear = new AtomicBoolean(false);

        Bukkit.getServer().getScheduler().runTaskTimer(api.getPlugin(), () -> {

            for (FastBoard board : boards.values()) {
                if (doClear.get())
                    board.updateLines(new ArrayList<>());

                updateBoard(board);
            }
            doClear.set(false);
        }, 0, 20);
    }

    @Override
    public FastBoard newGameScoreboard(Player player) {
        FastBoard fb = new FastBoard(player);
        fb.updateTitle((String) api.getRules().getGameInfos().getObject("api.gameTitle"));

        boards.put(player.getUniqueId(), fb);
        return fb;
    }

    @Override
    public void removeGameScoreboard(Player player) {
        boards.remove(player.getUniqueId());
    }

    @Override
    public void updateBoard(FastBoard board) {

    }

    @Override
    public Set<FastBoard> getBoards() {
        return new HashSet<>(boards.values());
    }
}
