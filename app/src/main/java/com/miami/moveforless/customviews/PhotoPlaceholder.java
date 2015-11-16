package com.miami.moveforless.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.miami.moveforless.R;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by klim on 12.11.15.
 */
public class PhotoPlaceholder extends FrameLayout {

    @Bind(R.id.ivImagePlaceholder_PP)       ImageView ivPhoto;
    @Bind(R.id.btnDelete_PP)                ImageView btnDelete;

    public PhotoPlaceholder(Context context) {
        this(context, null);
    }

    public PhotoPlaceholder(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoPlaceholder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.photo_placeholder, this);
        ButterKnife.bind(this);
        RxUtils.click(btnDelete, o -> deleteClicked());
    }

    private void deleteClicked() {
        ((ViewGroup)getParent()).removeView(this);
    }

    public void setPhoto(Bitmap _bitmap) {
        ivPhoto.setImageBitmap(_bitmap);
    }
}
