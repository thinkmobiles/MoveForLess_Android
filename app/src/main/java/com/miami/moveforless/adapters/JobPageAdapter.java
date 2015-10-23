package com.miami.moveforless.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.miami.moveforless.fragments.QuestionnaireFragment;

/**
 * Created by klim on 22.10.15.
 */
public class JobPageAdapter extends FragmentStatePagerAdapter {

    public JobPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return QuestionnaireFragment.newInstance();

    }

    @Override
    public int getCount() {
        return 6;
    }
}
