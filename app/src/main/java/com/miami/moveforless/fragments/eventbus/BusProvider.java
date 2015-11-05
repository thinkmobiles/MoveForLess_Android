package com.miami.moveforless.fragments.eventbus;

import com.squareup.otto.Bus;

/**
 * Created by klim on 05.11.15.
 */
public class BusProvider {

    private static Bus mInstance;

    public static Bus getInstance() {
        if (mInstance == null) {
            mInstance = new Bus();
        }
        return mInstance;
    }
}
