package com.miami.moveforless.customviews.PhotoLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.miami.moveforless.utils.BitmapUtils;

import java.io.File;
import java.io.IOException;

/**
 * layout for adding check`s photo
 * Created by klim on 17.11.15.
 */
public class CheckLayout extends GridLayout {
    private CheckCallBack mCallBack;

    public CheckLayout(Context context) {
        this(context, null);
    }

    public CheckLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface CheckCallBack {
        void onDelete();
    }

    public void addImage(File _file) {
        PhotoPlaceholder placeholder = new PhotoPlaceholder(getContext(), this);
        try {

            DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

                    placeholder.setPhoto(BitmapUtils.handleSamplingAndRotationBitmap(getContext(), Uri
                            .fromFile(_file), width /2 , height / 2));
            removeAllViews();
            addView(placeholder);
            placeholder.setEventListener(this::deletePhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImage() {
        PhotoPlaceholder holder = (PhotoPlaceholder) getChildAt(0);
        return holder.getPhoto();
    }

    public void setCheckCallBack(CheckCallBack _callBack) {
        mCallBack = _callBack;
    }

    private void deletePhoto(View _view) {
        removeView(_view);
        if (mCallBack != null) mCallBack.onDelete();
    }
}
