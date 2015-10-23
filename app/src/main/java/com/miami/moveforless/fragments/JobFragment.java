package com.miami.moveforless.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.miami.moveforless.R;
import com.miami.moveforless.adapters.JobPageAdapter;
import com.miami.moveforless.customviews.CustomTabLayout;

import butterknife.Bind;

/**
 * Created by klim on 22.10.15.
 */
public class JobFragment extends BaseFragment {
    @Bind(R.id.tabLayout_FJ)
    CustomTabLayout mTabLayout;
    @Bind(R.id.viewPager_FJ)
    ViewPager mViewPager;


    private int selectedTab = 1;

    public static Fragment newInstance() {
        return new JobFragment();
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_job;
    }

    @Override
    protected void setupViews() {
        JobPageAdapter adapter = new JobPageAdapter(getChildFragmentManager());

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.customizeTabs(mViewPager);
        mViewPager.setCurrentItem(2);


    }

}
