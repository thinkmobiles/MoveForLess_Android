package com.miami.moveforless.customviews.PhotoLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
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
    @Bind(R.id.placeholder_container)       FrameLayout mPhotoContainer;

    private HolderEvent mEvent;

    public PhotoPlaceholder(Context context, GridLayout _container) {
        this(context, null, _container);
    }

    public PhotoPlaceholder(Context context, AttributeSet attrs, GridLayout _container) {
        this(context, attrs, 0, _container);
    }

    public PhotoPlaceholder(Context context, AttributeSet attrs, int defStyleAttr, GridLayout _container) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.photo_placeholder, this);
        ButterKnife.bind(this);
        RxUtils.click(btnDelete, o -> deleteClicked());
    }

    public void setEventListener(HolderEvent _event) {
        mEvent = _event;
    }

    private void deleteClicked() {
        if (mEvent != null) {
            mEvent.delete(this);
        }
    }

    public void setPhoto(Bitmap _bitmap) {
        ivPhoto.setImageBitmap(_bitmap);
    }

    public interface HolderEvent {
        void delete(View _view);
    }

    public Bitmap getPhoto() {
        return ((BitmapDrawable)ivPhoto.getDrawable()).getBitmap();
    }
}
