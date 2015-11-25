package com.miami.moveforless.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.utils.RxUtils;
import com.miami.moveforless.utils.ScreenUtils;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by klim on 26.10.15.
 */
public class SignatureDialog extends BaseDialog implements GestureOverlayView.OnGestureListener {
    private boolean isGestured;

    @BindString(R.string.ok)
    String strOk;
    @BindString(R.string.close)
    String strClose;
    @BindString(R.string.clear)
    String strClear;

    @Bind(R.id.gesture_DSL)
    GestureOverlayView mGestureView;
    @Bind(R.id.tvTitle_DSL)
    TextView tvTitle;
    @Bind(R.id.tvDescription_DSL)
    TextView tvDescription;
    @Bind(R.id.gesture_container_SL)
    FrameLayout gestureContainer;
    @Bind(R.id.btnClose_DSL)
    TextView btnClose;
    @Bind(R.id.btnClear_DSL)
    TextView btnClear;


    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_signature_layout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        View view = super.onCreateView(_inflater, _container, _savedInstanceState);

        int width = ScreenUtils.getScreenWidth(App.getAppContext()) / 3;
        int height = width * 9 / 16;
        gestureContainer.getLayoutParams().width = width;
        gestureContainer.getLayoutParams().height = height;
        return view;
    }

    @Override
    protected void setupViews() {
        RxUtils.click(btnClose, o -> onCloseClicked());
        RxUtils.click(btnClear, o -> onClearClicked());

        tvTitle.setText(getString(R.string.signature_title));
        tvDescription.setText(getString(R.string.signature_description));
        btnClear.setText(strClear);
        btnClose.setText(strClose);

        setCancelable(true);
        isGestured = false;
        mGestureView.addOnGestureListener(this);
    }

    public void onClearClicked() {
            mGestureView.cancelClearAnimation();
            mGestureView.clear(true);
            mGestureView.addOnGestureListener(this);
            btnClose.setText(strClose);
            btnClear.setVisibility(View.GONE);
            isGestured = false;
    }

    public void onCloseClicked() {
        if (isGestured) {
            try {
                mGestureView.setDrawingCacheEnabled(true);
                Bitmap bm = Bitmap.createBitmap(mGestureView.getDrawingCache());
                Intent data = new Intent();
                data.putExtra(Const.SIGNATURE_BITMAP_KEY, bm);
                getParentFragment().onActivityResult(Const.REQUEST_CODE_SIGN, Activity.RESULT_OK, data);
            } catch (Exception e) {
                dismiss();
            }
        }
        dismiss();
    }

    @Override
    public void onGestureStarted(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
        mGestureView.removeAllOnGestureListeners();
        btnClear.setVisibility(View.VISIBLE);
        btnClose.setText(strOk);
        isGestured = true;
    }

    @Override
    public void onGesture(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {

    }

    @Override
    public void onGestureEnded(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {

    }

    @Override
    public void onGestureCancelled(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {

    }

}
