package com.miami.moveforless.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.miami.moveforless.R;
import com.miami.moveforless.adapters.JobPageAdapter;
import com.miami.moveforless.customviews.CustomTabLayout;
import com.miami.moveforless.dialogs.ConfirmDialog;
import com.miami.moveforless.dialogs.RouteDialog;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.FragmentEvent;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.rest.response.JobResponse;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;

/**
 * Created by klim on 22.10.15.
 */
public class JobFragment extends BaseFragment {
    @BindString(R.string.back__pressed_confirm_message)
    String strBackPressed;
    @BindColor(R.color.cyan_dark)
    int clrCyanDark;

    @Bind(R.id.tabLayout)
    CustomTabLayout mTabLayout;
    @Bind(R.id.viewPager_FJ)
    ViewPager mViewPager;

    private int selectedTab = 0;
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
    protected void setupViews(Bundle _savedInstanceState) {
        setHasOptionsMenu(true);

        mTabLayout.setBackgroundColor(clrCyanDark);
        mAdapter = new JobPageAdapter(getChildFragmentManager());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null)
                    fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStack();
            return;
        }

        Fragment currFragment = mAdapter.getItem(mViewPager.getCurrentItem());
        final int position = mViewPager.getCurrentItem();
        if (position == 0 || currFragment instanceof CongratulationFragment) {
            getFragmentManager().popBackStack();
        } else {
            mViewPager.setCurrentItem(position - 1);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_job_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Fragment fragment = mAdapter.getItem(mViewPager.getCurrentItem());
                if (fragment != null && fragment instanceof BaseJobDetailFragment) {
                    if (((BaseJobDetailFragment) fragment).isAllowGoHome()) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        ConfirmDialog dialog = new ConfirmDialog();
                        dialog.setMessage(strBackPressed);
                        dialog.setOnPositiveListener(view -> getActivity().getSupportFragmentManager().popBackStack());
                        dialog.show(getActivity().getSupportFragmentManager(), "");
                    }
                }
                break;
            case R.id.menu_navigation: {
                RouteDialog dialog = new RouteDialog();
                dialog.show(getChildFragmentManager(), "");
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void SwitchFragment(FragmentEvent _event) {
        if (_event.getFragment() != null) {
            if (_event.isAddToBackStack())
                replaceFragmentWithBackStack(R.id.job_container_FJ, _event.getFragment());
            else
                replaceFragmentWithoutBackStack(R.id.job_container_FJ, _event.getFragment());
        }
    }

    @Subscribe
    public void switchViewPager(SwitchJobDetailsEvent _event) {

        final int position = mViewPager.getCurrentItem();
        if (position == mAdapter.getCount() - 1) {
            getFragmentManager().popBackStack();
        } else {
            mViewPager.setCurrentItem(position + 1);
        }
    }

}
