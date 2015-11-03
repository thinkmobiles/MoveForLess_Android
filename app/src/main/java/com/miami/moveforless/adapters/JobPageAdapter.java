package com.miami.moveforless.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.miami.moveforless.fragments.CongratulationFragment;
import com.miami.moveforless.fragments.JobDetailsFragment;
import com.miami.moveforless.fragments.PaymentFragment;
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
        switch (position) {
            case 0:
                return JobDetailsFragment.newInstance();
            case 5:
                return PaymentFragment.newInstance();
            case 6:
                return CongratulationFragment.newInstance();

        }
        return QuestionnaireFragment.newInstance();

    }

    @Override
    public int getCount() {
        return 7;
    }
}
