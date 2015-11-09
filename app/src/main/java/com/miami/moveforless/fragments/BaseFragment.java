package com.miami.moveforless.fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.miami.moveforless.R;
import com.miami.moveforless.dialogs.ConfirmDialog;
import com.miami.moveforless.dialogs.InfoDialog;
import com.miami.moveforless.utils.BitmapUtils;

import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by SetKrul on 14.07.2015.
 */
public abstract class BaseFragment extends Fragment {

    protected abstract int getLayoutResource();
    protected abstract void setupViews();
    protected LayoutInflater mLayoutInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle savedInstanceState) {
        super.onCreateView(_inflater, _container, savedInstanceState);
        mLayoutInflater = _inflater;
        View view = _inflater.inflate(getLayoutResource(), _container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupViews();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    protected void showInfoDialog(String _message) {
        InfoDialog dialog = new InfoDialog();
        dialog.setTitle(_message);
        dialog.show(getChildFragmentManager(), "");
    }

    public void onBackPressed() {

    }

    protected final void addFragment(final @IdRes int _containerId, final Fragment _fragment) {
        getChildFragmentManager().beginTransaction().add(_containerId, _fragment).commit();
    }

    public final void replaceFragmentWithBackStack(final @IdRes int _containerId, final Fragment _fragment) {
        getChildFragmentManager().beginTransaction().addToBackStack(null).replace(_containerId, _fragment).commit();
    }

    public final void replaceFragmentWithoutBackStack(final @IdRes int _containerId, final Fragment _fragment) {
        getChildFragmentManager().beginTransaction().replace(_containerId, _fragment).commit();
    }


    public final void destroyFragment(final Fragment _fragment) {
        getChildFragmentManager().beginTransaction().remove(_fragment).commit();
    }

    @SuppressWarnings("unchecked")
    public final <T extends Fragment> T getFragmentById(final @IdRes int _containerId) {
        return (T) getChildFragmentManager().findFragmentById(_containerId);
    }

    public final void popBackStack() {
        getFragmentManager().popBackStack();
    }

    public final void popBackStackImmediate() {
        getFragmentManager().popBackStackImmediate();
    }

    protected void hideKeyboard(Context _context) {
        InputMethodManager inputManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View v = ((Activity) _context).getCurrentFocus();
        if (v == null) return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    protected void showKeyboard(Context _context, View _view) {
        InputMethodManager inputMethodManager = (InputMethodManager) _context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        //  if (!inputMethodManager.isAcceptingText()) {
        inputMethodManager.showSoftInput(_view, 0);
    }
//    }
//
}