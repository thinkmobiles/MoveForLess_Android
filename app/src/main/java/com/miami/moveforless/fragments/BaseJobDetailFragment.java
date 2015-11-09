package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by klim on 06.11.15.
 */
public abstract class BaseJobDetailFragment extends BaseFragment {

    protected abstract boolean isAllowGoHome();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return  super.onCreateView(_inflater, _container, savedInstanceState);
    }

}
