package io.rqndomhax.rqndomuhc.listeners;

import io.rqndomhax.uhcapi.events.GameBuildEvent;
import io.rqndomhax.uhcapi.UHCAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class GameLock implements Listener {

    private final UHCAPI api;
    int builds = 0;

    public GameLock(UHCAPI api) {
        this.api = api;
    }

    @EventHandler
    public void onConnect(PlayerLoginEvent event) {
        if((boolean) api.getRules().getGameInfos().getObject("api.isServerLocked"))
            event.setKickMessage((String) api.getRules().getGameInfos().getObject("api.serverLockedKickMessage"));
    }

    @EventHandler
    public void onBuild(GameBuildEvent event) {
        if (!event.getName().equals("MBuilder"))
            return;
        if (event.getType().equals(GameBuildEvent.Type.STARTED)) {
            api.getRules().getGameInfos().addObject("api.isServerLocked", true);
            builds++;
        }
        if (event.getType().equals(GameBuildEvent.Type.ENDED) && builds-- == 0) {
            api.getRules().getGameInfos().addObject("api.isServerLocked", false);
            if (api.getGameTaskManager().getGameState().equals("LOBBY"))
                api.getWorldManager().setLobby(event.getCenter().add(0, 1d, 0));
        }
    }

}
