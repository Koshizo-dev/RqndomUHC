/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.utils.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RInventoryTaskData {

    private final Map<UUID, Integer> runnableMap;
    public RInventoryTaskData() {
        this.runnableMap = new HashMap<>();
    }

    public Map<UUID, Integer> getRunnableMap() {
        return runnableMap;
    }
}