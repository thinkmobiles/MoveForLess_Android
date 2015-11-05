package com.miami.moveforless.fragments.eventbus;

/**
 * Created by klim on 05.11.15.
 */
public class OpenJobDetailsEvent {
    FragmentType mType;
    int mJobId;

    public OpenJobDetailsEvent(FragmentType _type, int _jobId) {
        mType = _type;
        mJobId = _jobId;
    }

    public FragmentType getType() {
        return mType;
    }

    public int getId(){
        return mJobId;
    }
}
