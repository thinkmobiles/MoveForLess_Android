package com.miami.moveforless.adapters;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.miami.moveforless.fragments.BaseJobDetailFragment;
import com.miami.moveforless.fragments.CongratulationFragment;
import com.miami.moveforless.fragments.FeedbackFragment;
import com.miami.moveforless.fragments.JobDetailsFragment;
import com.miami.moveforless.fragments.PaymentFragment;
import com.miami.moveforless.fragments.QuestionnaireFragment;
import com.miami.moveforless.fragments.eventbus.FragmentType;
import com.miami.moveforless.globalconstants.Const;

/**
 * Created by klim on 22.10.15.
 */
public class JobPageAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener{

    public JobPageAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        final FragmentType type = Const.JOB_DETAILS_ORDER[position];

        switch (type) {
            case JOB_DETAILS:
                return JobDetailsFragment.newInstance();
            case QUESTIONNAIRE:
                return QuestionnaireFragment.newInstance();
            case PAYMENT:
                return PaymentFragment.newInstance();
            case CONFIRMATION:
                return CongratulationFragment.newInstance();
            case FEEDBACK:
                return FeedbackFragment.newInstance();
            default:
                return JobDetailsFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return Const.JOB_DETAILS_ORDER.length;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
/*
        if (getItem(position) instanceof BaseJobDetailFragment) {
            ((BaseJobDetailFragment)getItem(position)).restoreFragment();
        }
*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
