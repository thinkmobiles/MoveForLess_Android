package com.miami.moveforless.fragments;

import android.graphics.drawable.Drawable;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.ParallaxScrollView;

import butterknife.Bind;
import butterknife.BindDrawable;

/**
 * Created by SetKrul on 03.11.2015.
 */
public class JobDetailsFragment extends BaseFragment {
    @Bind(R.id.scroll_view)
    ParallaxScrollView scrollView;
    @BindDrawable(R.drawable.job_details_background)
    Drawable mBackground;

    public static JobDetailsFragment newInstance() {
        return new JobDetailsFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_job_details;
    }

    @Override
    protected void setupViews() {
        scrollView.parallaxViewBackgroundBy(scrollView, mBackground, .2f);
    }
}
