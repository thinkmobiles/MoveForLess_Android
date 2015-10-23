package com.miami.moveforless.fragments;

import android.support.v4.app.Fragment;

import com.miami.moveforless.R;

/**
 * Created by klim on 22.10.15.
 */
public class QuestionnaireFragment extends BaseFragment {

    public static Fragment newInstance() {
        return new QuestionnaireFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_questionnaire;
    }

    @Override
    protected void setupViews() {

    }
}
