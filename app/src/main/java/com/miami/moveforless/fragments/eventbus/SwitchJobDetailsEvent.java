package com.miami.moveforless.fragments.eventbus;

/**
 * Created by klim on 06.11.15.
 */
public class SwitchJobDetailsEvent {
    private FragmentType mType;

    public SwitchJobDetailsEvent(FragmentType _type) {
        mType = _type;
    }

    public FragmentType getType() {
        return mType;
    }
}
