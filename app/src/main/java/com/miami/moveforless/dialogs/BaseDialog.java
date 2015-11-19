package com.miami.moveforless.dialogs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import com.miami.moveforless.R;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by klim on 26.10.15.
 */
public abstract class BaseDialog extends DialogFragment {

    private int mContentResource = getLayoutResource();

    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        super.onCreateView(_inflater, _container, _savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View rootView = _inflater.inflate(R.layout.dialog_base_layout, _container, false);
        if (mContentResource != 0) {
            FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.content_container_BDL);
            frameLayout.removeAllViews();
            frameLayout.addView(_inflater.inflate(mContentResource, frameLayout, false));
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View _view, Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        setupViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions);
    }

    @Override
    public void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(mSubscriptions);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected void addSubscription(Subscription _subscription) {
        mSubscriptions.add(_subscription);
    }

    protected void removeSubscription(Subscription _subscription) {
        mSubscriptions.remove(_subscription);
    }

    protected abstract int getLayoutResource();

    protected abstract void setupViews();

}
