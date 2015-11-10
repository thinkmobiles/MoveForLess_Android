package com.miami.moveforless.customviews;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miami.moveforless.R;

import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * Created by klim on 22.10.15.
 */
public class CustomTabLayout extends TabLayout {
    @BindColor(R.color.cyan_light)
    int cyan_200;
    @BindColor(R.color.cyan_dark)
    int cyan_800;
    @BindColor(R.color.yellow)
    int yellow;

    public CustomTabLayout(Context context) {
        this(context, null, 0);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ButterKnife.bind(this);
    }

    public void customizeTabs(ViewPager _viewPager) {

        final String[] titles = getResources().getStringArray(R.array.tabs_titles);
        for (int i = 0; i < getTabCount(); i++) {
            if (i < titles.length) {
                getTabAt(i).setCustomView(getTabView(titles[i], _viewPager.getCurrentItem() == i));
            }
        }

        LinearLayout tabStrip = ((LinearLayout)getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(_viewPager) {
            @Override
            public void onTabSelected(Tab tab) {
                super.onTabSelected(tab);
                final View tabView = tab.getCustomView();
                if (tabView != null) {
                    TextView title = (TextView) tabView.findViewById(R.id.tvTitle_TL);
                    tabView.setBackgroundColor(yellow);
                    title.setTextColor(cyan_800);
                    tab.setCustomView(tabView);
                }

            }

            @Override
            public void onTabUnselected(Tab tab) {
                super.onTabUnselected(tab);
                final View tabView = tab.getCustomView();
                if (tabView != null) {
                    TextView title = (TextView) tabView.findViewById(R.id.tvTitle_TL);
                    tabView.setBackgroundColor(cyan_800);
                    title.setTextColor(cyan_200);
                    tab.setCustomView(tabView);
                }
            }
        });

    }

    private View getTabView(String _title, boolean _isSelected) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_layout, this, false);
        TextView title = (TextView) tabView.findViewById(R.id.tvTitle_TL);
//        ImageView arrow = (ImageView) tabView.findViewById(R.id.ivTitleIcon_TL);
        title.setText(_title);
//        arrow.setImageResource(_isSelected? R.drawable.icn_right : R.drawable.icn_right_green);
        tabView.setBackgroundColor(_isSelected? yellow : cyan_800);
        title.setTextColor(_isSelected ? cyan_800: cyan_200);
        return tabView;
    }

}
