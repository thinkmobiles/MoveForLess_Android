package com.miami.moveforless.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.miami.moveforless.R;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import rx.Subscription;

/**
 * feedback screen
 * Created by klim on 12.11.15.
 */
public class FeedbackFragment extends BaseJobDetailFragment implements TextWatcher {
    @BindString(R.string.send_feedback_successfull)
    String strSendSuccessfull;
    @BindColor(R.color.cyan_dark)
    int cyanDark;
    @BindColor(R.color.cyan_light)
    int cyanLight;
    @Bind(R.id.btnSend_FF)
    Button btnSend;
    @Bind(R.id.btnClaim_FF)
    Button btnClaim;
    @Bind(R.id.etFeedBack_FF)
    EditText etFeedback;
    @Bind(R.id.ratingbar_FF)
    RatingBar mRatingBar;

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
        mRatingBar.setOnRatingBarChangeListener((ratingBar, v, b) -> changeSendButton(true));
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
            getParentFragment().onActivityResult(Const.FEEDBACK_REQUEST_ID, Activity.RESULT_OK, null);
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

    @Override
    public void onPageSelected() {

    }

    private void clearAll() {
        etFeedback.setText("");
        mRatingBar.setRating(0f);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        changeSendButton(charSequence.length() != 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void changeSendButton(boolean _isEnabled) {
         btnSend.setEnabled(_isEnabled);
    }

}