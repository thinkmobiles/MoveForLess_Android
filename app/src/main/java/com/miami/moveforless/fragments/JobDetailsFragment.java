package com.miami.moveforless.fragments;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.ParallaxScrollView;

import butterknife.Bind;

/**
 * Created by SetKrul on 03.11.2015.
 */
public class JobDetailsFragment extends BaseJobDetailFragment {
    @Bind(R.id.scroll_view)
    ParallaxScrollView scrollView;

    public static JobDetailsFragment newInstance() {
        return new JobDetailsFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_job_details;
    }

    @Override
    protected void setupViews() {
        scrollView.parallaxViewBackgroundBy(scrollView, getResources().getDrawable(R.drawable.job_details_background), .2f);
    }

    @Override
    protected boolean isAllowGoHome() {
        return false;
    }
}
