package com.miami.moveforless.fragments.eventbus;

import android.support.v4.app.Fragment;

/**
 * Created by klim on 13.11.15.
 */
public class FragmentEvent {

    Fragment mFragment;
    boolean isAddToBackStack;

    public FragmentEvent(Fragment _fragment, boolean _isAddToBackStack) {
        mFragment = _fragment;
        isAddToBackStack = _isAddToBackStack;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public boolean isAddToBackStack() {
        return isAddToBackStack;
    }
}
