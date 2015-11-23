package com.miami.moveforless.fragments;

/**
 * Created by klim on 06.11.15.
 */
public abstract class BaseJobDetailFragment extends BaseFragment {

    protected abstract boolean isAllowGoHome();

    public abstract void onPageSelected();
}
