package io.rqndomhax.rqndomuhc.inventories;

import io.rqndomhax.uhcapi.UHCAPI;
import io.rqndomhax.uhcapi.utils.inventory.RInventory;

public class IHostCustomConfigs extends RInventory {
    public IHostCustomConfigs(UHCAPI api) {
        super(api, IInfos.MAIN_HOST_NAME, 9*6);
    }
}
