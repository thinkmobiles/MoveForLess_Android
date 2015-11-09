package com.miami.moveforless.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.utils.ScreenUtils;

import butterknife.Bind;

/**
 * Created by klim on 26.10.15.
 */
public class SignatureDialog extends BaseDialog implements GestureOverlayView.OnGestureListener{
    public static final int REQUEST_CODE = 1000;
    public static final String SIGNATURE_BITMAP_KEY = "SIGNATURE_BITMAP_KEY";
    private boolean isGestured;

    @Bind(R.id.gesture_SL)
    GestureOverlayView mGestureView;

    @Override
    protected int getLayoutResource() {
        return R.layout.signature_layout;
    }

    @Override
    protected void setupViews() {
        int width = ScreenUtils.getScreenWidth(App.getAppContext());
        int height = width * 9 /16;
        mGestureView.getLayoutParams().width = width;
        mGestureView.getLayoutParams().height = height;

        setTitle(getString(R.string.signature_title));
        setDescription(getString(R.string.signature_description));
        setNegativeTitle(getString(R.string.close));
        setPositiveTitle(getString(R.string.ok));
        setPositiveVisibility(false);
        setCancelable(true);
        isGestured = false;
        mGestureView.addOnGestureListener(this);
    }

    @Override
    public void onNegativeClicked() {
        if (isGestured) {
            mGestureView.cancelClearAnimation();
            mGestureView.clear(true);
            mGestureView.addOnGestureListener(this);
            setNegativeTitle(getString(R.string.close));
            setPositiveVisibility(false);
            isGestured = false;
        } else {
            dismiss();
        }
    }

    @Override
    public void onPositiveClicked() {
        try {
            mGestureView.setDrawingCacheEnabled(true);
            Bitmap bm = Bitmap.createBitmap(mGestureView.getDrawingCache());
            Intent data = new Intent();
            data.putExtra(SIGNATURE_BITMAP_KEY, bm);
            if (getTargetFragment() != null)
                getTargetFragment().onActivityResult(REQUEST_CODE, Activity.RESULT_OK, data);
        } catch (Exception e) {
            getTargetFragment().onActivityResult(REQUEST_CODE, Activity.RESULT_CANCELED, null);
        }

        super.onPositiveClicked();
    }

    @Override
    public void onGestureStarted(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
        mGestureView.removeAllOnGestureListeners();
        setPositiveVisibility(true);
        isGestured = true;
        setNegativeTitle(getString(R.string.clear));
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
