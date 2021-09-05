/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.game;

import io.rqndomhax.fastboard.FastBoard;
import io.rqndomhax.rqndomuhc.managers.GameManager;
import io.rqndomhax.uhcapi.utils.RScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameScoreboard implements RScoreboard {

    final GameManager gameManager;
    final HashMap<UUID, FastBoard> boards = new HashMap<>();

    public GameScoreboard(GameManager gameManager) {
        this.gameManager = gameManager;
        runBoard();
    }

    private void runBoard() {

        AtomicBoolean doClear = new AtomicBoolean(false);

        Bukkit.getServer().getScheduler().runTaskTimer(gameManager.getPlugin(), () -> {

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
        fb.updateTitle(gameManager.getRules().getGameTitle());

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
