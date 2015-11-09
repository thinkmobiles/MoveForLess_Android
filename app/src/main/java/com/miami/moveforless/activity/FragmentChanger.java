package com.miami.moveforless.activity;

import android.support.v4.app.Fragment;

/**
 * Created by klim on 06.11.15.
 */
public interface FragmentChanger {

    void switchFragment(Fragment _fragment, boolean _isAddToBackStack);
}
