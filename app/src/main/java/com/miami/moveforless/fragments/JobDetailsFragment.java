package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.ParallaxScrollView;
import com.miami.moveforless.dialogs.RouteDialog;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;

/**
 * job details screen
 * Created by SetKrul on 03.11.2015.
 */
public class JobDetailsFragment extends BaseJobDetailFragment {
    @Bind(R.id.scroll_view_FJD)
    ParallaxScrollView scrollView;
    @Bind(R.id.etNotes_FJD)
    EditText etNotes;
    @Bind(R.id.btnEditNotes_FJD)
    View btnEditNotes;
    @Bind(R.id.btnShowRoute_FJD)
    Button btnShowRoute;
    @Bind(R.id.btnStartJob_FJD)
    Button btnStartJob;
    @Bind(R.id.btnSave_FJD)
    Button btnSaveJob;


    public static JobDetailsFragment newInstance() {
        return new JobDetailsFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_job_details;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {
        scrollView.parallaxViewBackgroundBy(scrollView, ContextCompat.getDrawable(getContext(), R.drawable.job_details_background), .2f);
        RxUtils.click(btnEditNotes, o -> switchNotes());
        RxUtils.click(btnShowRoute, o -> showRoute());
        RxUtils.click(btnStartJob, o -> startJob());
        RxUtils.click(btnSaveJob, o -> saveJob());
    }

    @Override
    protected boolean isAllowGoHome() {
        return false;
    }

    @Override
    public void onPageSelected() {

    }

    private void showRoute() {
        RouteDialog dialog = new RouteDialog();
        dialog.show(getChildFragmentManager(), "");
    }

    private void startJob() {
        BusProvider.getInstance().post(new SwitchJobDetailsEvent());
    }

    private void saveJob() {
        // TODO: add updating job to database and on server
    }

    private void switchNotes() {
        if (etNotes.isEnabled()) {
            etNotes.setEnabled(false);
            etNotes.clearFocus();
        } else {
            etNotes.setEnabled(true);
            etNotes.requestFocus();
        }
    }
}
