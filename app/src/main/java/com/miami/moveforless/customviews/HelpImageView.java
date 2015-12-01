package com.miami.moveforless.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by klim on 27.11.15.
 */
public class HelpImageView extends ImageView implements View.OnLongClickListener{
    public HelpImageView(Context context) {
        this(context, null);
    }

    public HelpImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HelpImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (getTag() != null)
            setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        DetailInfoPopupWindow popup = new DetailInfoPopupWindow(getContext(), getTag().toString());
        popup.show(view);
        return true;
    }
}
