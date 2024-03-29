package com.miami.moveforless.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.miami.moveforless.fragments.BaseJobDetailFragment;
import com.miami.moveforless.fragments.ClaimFragment;
import com.miami.moveforless.fragments.CongratulationFragment;
import com.miami.moveforless.fragments.ContractFragment;
import com.miami.moveforless.fragments.FeedbackFragment;
import com.miami.moveforless.fragments.InvoiceFragment;
import com.miami.moveforless.fragments.JobDetailsFragment;
import com.miami.moveforless.fragments.PaymentFragment;
import com.miami.moveforless.fragments.QuestionnaireFragment;
import com.miami.moveforless.fragments.eventbus.FragmentType;
import com.miami.moveforless.globalconstants.Const;

/**
 * Created by klim on 22.10.15.
 */
public class JobPageAdapter extends FragmentStatePagerAdapter{

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

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
            case SIGN_CONTRACT:
                return ContractFragment.newInstance();
            case SIGN_INVOICE:
                return InvoiceFragment.newInstance();
            case PAYMENT:
                return PaymentFragment.newInstance();
            case CONFIRMATION:
                return CongratulationFragment.newInstance();
            case FEEDBACK:
                return FeedbackFragment.newInstance();
            case CLAIM:
                return ClaimFragment.newInstance();
            default:
                return JobDetailsFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return Const.JOB_DETAILS_ORDER.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

}
