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
import android.widget.TextView;

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
    @Bind(R.id.tvTitle_BDL)
    TextView tvTitle;
    @Bind(R.id.tvDescription_BDL)
    TextView tvDescription;
    @Bind(R.id.btnPositive_BDL)
    Button btnPositive;
    @Bind(R.id.btnNegative_BDL)
    Button btnNegative;

    private DialogListener positiveOnClick;
    private DialogListener negativeOnClick;

    private int mContentResource = getLayoutResource();

    private String mTitle = "";
    private String mDescription = "";

    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        super.onCreateView(_inflater, _container, _savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View rootView = _inflater.inflate(R.layout.base_dialog_layout, _container, false);
        if (mContentResource != 0) {
            FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.content_container_BDL);
            frameLayout.removeAllViews();
            frameLayout.addView(_inflater.inflate(mContentResource, frameLayout, false));
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View _view, Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        ButterKnife.bind(this, _view);
        setupViews();

        if (!mTitle.isEmpty()) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(mTitle);
        }
        if (!mDescription.isEmpty()) {
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(mDescription);
        }

        RxUtils.click(btnPositive, o -> onPositiveClicked());
        RxUtils.click(btnNegative, o -> onNegativeClicked());
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

    public void onPositiveClicked() {
        if (positiveOnClick != null) positiveOnClick.onClick();
        dismiss();
    }

    public void onNegativeClicked() {
        if (negativeOnClick != null) negativeOnClick.onClick();
        dismiss();
    }

    public void setPositiveClickListener(DialogListener _listener) {
        positiveOnClick = _listener;
    }

    public void setNegativeClickListener(DialogListener _listener) {
        negativeOnClick = _listener;
    }

    public void setTitle(String _title) {
        mTitle = _title;
    }

    public void setPositiveTitle(String _title) {
        if (btnPositive != null) {
            btnPositive.setText(_title);
            btnPositive.setVisibility(View.VISIBLE);
        }
    }

    public void setNegativeTitle(String _title) {
        if (btnNegative != null) {
            btnNegative.setText(_title);
            btnNegative.setVisibility(View.VISIBLE);
        }
    }

    public void setPositiveVisibility(boolean isVisible) {
        if (btnPositive != null)
            btnPositive.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setNegativeVisibility(boolean isVisible) {
        if (btnNegative != null)
            btnNegative.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


    public void setDescription(String _description) {
        mDescription = _description;
    }

    public interface DialogListener {
        void onClick();
    }
}
