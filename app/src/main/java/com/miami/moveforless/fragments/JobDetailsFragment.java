package com.miami.moveforless.fragments;

import com.miami.moveforless.R;
import com.miami.moveforless.utils.ParallaxScrollView;

import butterknife.Bind;

/**
 * Created by SetKrul on 03.11.2015.
 */
public class JobDetailsFragment extends BaseFragment {
    @Bind(R.id.scroll_view)
    ParallaxScrollView scrollView;

    public static JobDetailsFragment newInstance() {
        return new JobDetailsFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.job_details_fragment;
    }

    @Override
    protected void setupViews() {
        scrollView.parallaxViewBackgroundBy(scrollView, getResources().getDrawable(R.drawable.job_details_background)
                , .2f);
    }
}
