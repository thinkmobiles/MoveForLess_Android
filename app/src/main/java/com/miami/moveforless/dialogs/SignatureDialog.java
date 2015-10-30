package com.miami.moveforless.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.miami.moveforless.R;

import butterknife.Bind;

/**
 * Created by klim on 26.10.15.
 */
public class SignatureDialog extends BaseDialog {
    public static final int REQUEST_CODE = 1000;
    public static final String SIGNATURE_BITMAP_KEY = "SIGNATURE_BITMAP_KEY";

    @Bind(R.id.gesture_SL)
    GestureOverlayView mGestureView;

    @Override
    protected int getLayoutResource() {
        return R.layout.signature_layout;
    }

    @Override
    protected void setupViews() {
        setTitle(getString(R.string.signature_title));
        setDescription(getString(R.string.signature_description));
        setNegativeTitle(getString(R.string.clear));
        setCancelable(true);

        mGestureView.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
            @Override
            public void onGestureStarted(GestureOverlayView gestureOverlayView, MotionEvent motionEvent) {
                mGestureView.removeAllOnGestureListeners();
                setPositiveTitle(getString(R.string.ok));
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
        });
    }

    @Override
    public void onNegativeClicked() {
        mGestureView.clear(true);
    }

    @Override
    public void onPositiveClicked() {
        try {

            mGestureView.setDrawingCacheEnabled(true);
            Bitmap bm = Bitmap.createBitmap(mGestureView.getDrawingCache());
            Intent data = new Intent();
            data.putExtra(SIGNATURE_BITMAP_KEY, bm);
            getTargetFragment().onActivityResult(REQUEST_CODE, Activity.RESULT_OK, data);
        } catch (Exception e) {
            getTargetFragment().onActivityResult(REQUEST_CODE, Activity.RESULT_CANCELED, null);
        }

        super.onPositiveClicked();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
