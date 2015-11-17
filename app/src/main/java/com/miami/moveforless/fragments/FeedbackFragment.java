package com.miami.moveforless.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.Helvetica.HelveticaEditText;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.BindString;
import rx.Subscription;

/**
 * Created by klim on 12.11.15.
 */
public class FeedbackFragment extends BaseJobDetailFragment {
    @BindString(R.string.send_feedback_successfull)      String strSendSuccessfull;
    @Bind(R.id.btnSend_FF)                                  Button btnSend;
    @Bind(R.id.btnClaim_FF)                                 Button btnClaim;
    @Bind(R.id.etFeedBack_FF)                               HelveticaEditText etFeedback;
    @Bind(R.id.ratingbar_FF)                                RatingBar mRatingBar;

    private Subscription sendSubscription;

    public static FeedbackFragment newInstance() {

        Bundle args = new Bundle();

        FeedbackFragment fragment = new FeedbackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {
        RxUtils.click(btnSend, o -> btnSendClicked());
        RxUtils.click(btnClaim, o -> btnOpenClaim());
    }

    private void btnSendClicked() {

        showLoadingDialog();

        if (sendSubscription != null) removeSubscription(sendSubscription);
        sendSubscription = RestClient.getInstance().sendFeedback().subscribe(aBoolean -> onSendSuccess(), this::onSendError);
        addSubscription(sendSubscription);
    }

    private void btnOpenClaim() {
        BusProvider.getInstance().post(new SwitchJobDetailsEvent());
    }

    private void onSendSuccess() {
        hideLoadingDialog();
        showInfoDialog(strSendSuccessfull, view -> {
            clearAll();
            getParentFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
            getActivity().onBackPressed();
        });
    }

    private void onSendError(Throwable _throwable) {
        hideLoadingDialog();
        showErrorDialog(ErrorParser.parse(_throwable));
    }

    @Override
    protected boolean isAllowGoHome() {
        return false;
    }

    private void clearAll() {
        etFeedback.setText("");
        mRatingBar.setRating(0f);
    }
}
