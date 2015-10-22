package com.miami.moveforless.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.CustomTabLayout;

import butterknife.Bind;

/**
 * Created by klim on 22.10.15.
 */
public class JobFragment extends BaseFragment {
    @Bind(R.id.tabLayout_FJ)
    CustomTabLayout tabLayout;
    @Bind(R.id.viewPager_FJ)
    ViewPager viewPager;


    public static Fragment newInstance() {
        return new Fragment();
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_job;
    }

    @Override
    protected void setupViews() {

    }



}
