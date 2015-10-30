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

import com.jakewharton.rxbinding.view.RxView;
import com.miami.moveforless.R;
import com.miami.moveforless.utils.RxUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by klim on 26.10.15.
 */
public abstract class BaseDialog extends DialogFragment {
    @Bind(R.id.content_container_BDL)
    FrameLayout mContainerLayout;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        super.onCreateView(_inflater, _container, _savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View rootView = _inflater.inflate(R.layout.base_dialog_layout, _container, false);
        if (mContentResource != 0) {
            FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.content_container_BDL);
            frameLayout.addView(_inflater.inflate(mContentResource, frameLayout, false));
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View _view, Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        ButterKnife.bind(this, _view);
        if (!mTitle.isEmpty()) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(mTitle);
        }
        if (!mDescription.isEmpty()) {
            tvDescription.setVisibility(View.VISIBLE);
            tvDescription.setText(mDescription);
        }

        setupViews();
        RxUtils.click(btnPositive, o -> onPositiveClicked());
        RxUtils.click(btnNegative, o -> onNegativeClicked());
    }

    protected abstract int getLayoutResource();
    protected abstract void setupViews();

    public void onPositiveClicked() {
        if (positiveOnClick != null) positiveOnClick.onClick();
        dismiss();
    }

    public void onNegativeClicked(){
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
        btnPositive.setVisibility(View.VISIBLE);
        btnPositive.setText(_title);
    }

    public void setNegativeTitle(String _title) {
        btnNegative.setVisibility(View.VISIBLE);
        btnNegative.setText(_title);
    }

    public void setDescription(String _description) {
        mDescription = _description;
    }

    public interface DialogListener {
        void onClick();
    }
}
