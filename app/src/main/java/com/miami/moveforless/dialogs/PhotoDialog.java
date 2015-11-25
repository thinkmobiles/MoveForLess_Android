package com.miami.moveforless.dialogs;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;

/**
 * Created by SetKrul on 12.11.2015.
 */
public class PhotoDialog extends BaseDialog {

    @Bind(R.id.btnGallery_DP)
    TextView btnGallery;
    @Bind(R.id.btnCamera_DP)
    TextView btnCamera;

    private View.OnClickListener mListenerCamera;
    private View.OnClickListener mListenerGallery;

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_photo;
    }

    @Override
    protected void setupViews() {
        setCancelable(true);

        RxUtils.click(btnGallery).subscribe(o -> {
            if (mListenerGallery != null) mListenerGallery.onClick(null);
            dismiss();
        });
        RxUtils.click(btnCamera).subscribe(o -> {
            if (mListenerCamera != null) mListenerCamera.onClick(null);
            dismiss();
        });


    }

    public void setOnCameraListener(View.OnClickListener _listener) {
        mListenerCamera = _listener;
    }

    public void setOnGalleryListener(View.OnClickListener _listener) {
        mListenerGallery = _listener;
    }
}
