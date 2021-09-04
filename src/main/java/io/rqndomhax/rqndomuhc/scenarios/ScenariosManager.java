/*
 * Copyright (c) 2021.
 *  Github: https://github.com/RqndomHax
 */

package io.rqndomhax.rqndomuhc.scenarios;

import io.rqndomhax.uhcapi.utils.RScenarios;

public class ScenariosManager extends RScenarios {

    public ScenariosManager() {

        registerScenario("io.rqndomhax.rqndomuhc.scenarios.no_fall", new SNoFall());

    }
}
