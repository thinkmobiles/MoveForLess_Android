package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.miami.moveforless.R;
import com.miami.moveforless.adapters.JobPageAdapter;
import com.miami.moveforless.customviews.CustomTabLayout;
import com.miami.moveforless.dialogs.ConfirmDialog;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.FragmentType;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.globalconstants.Const;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;

/**
 * Created by klim on 22.10.15.
 */
public class JobFragment extends BaseFragment {
    @BindString(R.string.back__pressed_confirm_message) String strBackPressed;
    @Bind(R.id.tabLayout_FJ)
    CustomTabLayout mTabLayout;
    @Bind(R.id.viewPager_FJ)
    ViewPager mViewPager;

    private int selectedTab = 5;
    private JobPageAdapter mAdapter;

    public static Fragment newInstance(int _id) {
        Bundle bundle = new Bundle();
        bundle.putInt(Const.JOB_ID_KEY, _id);
        JobFragment fragment = new JobFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_job;
    }

    @Override
    protected void setupViews() {

//        try {
//            new CreatePdf(this).createPdf();
        setHasOptionsMenu(true);

        mAdapter = new JobPageAdapter(getChildFragmentManager());
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.customizeTabs(mViewPager);
        mViewPager.setCurrentItem(selectedTab);
    }

    @Override
    public void onPause() {
        BusProvider.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = mAdapter.getItem(mViewPager.getCurrentItem());
        if (fragment != null && fragment instanceof BaseJobDetailFragment) {
            if (((BaseJobDetailFragment)fragment).isAllowGoHome()) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                ConfirmDialog dialog = new ConfirmDialog();
                dialog.setDescription(strBackPressed);
                dialog.setPositiveClickListener(() -> getActivity().getSupportFragmentManager().popBackStack());
                dialog.show(getActivity().getSupportFragmentManager(), "");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Subscribe
    public void switchFragment(SwitchJobDetailsEvent _event) {
        int position = -1;


        if (_event.getType() == FragmentType.SCHEDULE) {
            getFragmentManager().popBackStack();
            return;
        }

        for (int i = 0; i < Const.JOB_DETAILS_ORDER.length; i++) {
            if (_event.getType() == Const.JOB_DETAILS_ORDER[i]) {
                position = i;
                break;
            }
        }
        mViewPager.setCurrentItem(position);
    }

}
