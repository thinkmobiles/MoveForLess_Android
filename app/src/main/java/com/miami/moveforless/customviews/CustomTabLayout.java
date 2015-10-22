package com.miami.moveforless.customviews;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miami.moveforless.R;

/**
 * Created by klim on 22.10.15.
 */
public class CustomTabLayout extends TabLayout {

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void addTab(@DrawableRes int _icon, @StringRes int _title) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_layout, null);
        TextView tvTitle = (TextView) tabView.findViewById(R.id.tvTitle_TL);
        tvTitle.setText(_title);
        ImageView ivIcon = (ImageView) tabView.findViewById(R.id.ivTitleIcon_TL);
        ivIcon.setImageResource(_icon);
        Tab tab = newTab();
        addTab(tab);
    }

}
