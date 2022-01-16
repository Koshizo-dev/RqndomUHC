package io.rqndomhax.rqndomuhc.listeners;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.events.GameHostChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EHostChange implements Listener {

    private final UHCAPI api;

    public EHostChange(UHCAPI api) {
        this.api = api;
    }

    @EventHandler
    private void onHostUpdate(GameHostChangeEvent event) {
        if (!event.isCancelled())
            api.getInventories().getInventory("api.hostConfig").refreshInventory();
    }

}
