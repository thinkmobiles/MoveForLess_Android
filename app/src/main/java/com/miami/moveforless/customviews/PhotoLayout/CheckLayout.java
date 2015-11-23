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
 * Created by klim on 17.11.15.
 */
public class CheckLayout extends GridLayout {
    private final int HORIZONTAL_SPACING = 20;
    private final int VERTICAL_SPACING = 20;
    private final int MIN_SIZE = 200;

    public CheckLayout(Context context) {
        this(context, null);
    }

    public CheckLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addImage(File _file) {
//        final int width = calculateSize();
        PhotoPlaceholder placeholder = new PhotoPlaceholder(getContext(), this);
        try {

            DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

                    placeholder.setPhoto(BitmapUtils.handleSamplingAndRotationBitmap(getContext(), Uri
                            .fromFile(_file), width /2 , height / 2));
//            placeholder.setLayoutParams(new FrameLayout.LayoutParams(width, width));
            removeAllViews();
            addView(placeholder);
//            calculateMargins();
            placeholder.setEventListener(this::deletePhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImage() {
        PhotoPlaceholder holder = (PhotoPlaceholder) getChildAt(0);
        return holder.getPhoto();
    }

    private int calculateSize() {

        int size = getMeasuredHeight();
        if (size < MIN_SIZE) {
            setColumnCount(getColumnCount() -1 );
            size = calculateSize();
        }
        return size;
    }

    private void deletePhoto(View _view) {
        removeView(_view);
//        calculateMargins();
    }

    private void calculateMargins() {

        for (int i = 0; i < getChildCount(); i++) {
            PhotoPlaceholder holder = (PhotoPlaceholder) getChildAt(i);
            LayoutParams params = (LayoutParams) holder.getLayoutParams();

            if ((i+1) % getChildCount() != 0) {
                params.setMargins(0, VERTICAL_SPACING, HORIZONTAL_SPACING, 0);
            } else {
                params.setMargins(0, VERTICAL_SPACING, 0, 0);
            }
            holder.setLayoutParams(params);
        }
    }
}
