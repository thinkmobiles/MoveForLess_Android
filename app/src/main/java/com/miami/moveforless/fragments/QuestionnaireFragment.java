package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * questionnaire screen
 * Created by klim on 22.10.15.
 */
public class QuestionnaireFragment extends BaseJobDetailFragment {
    @Bind(R.id.btnNext_FQ)
    Button btnNext;
    @Bind(R.id.questions_container_FQ)
    LinearLayout mQuestionsContainer;

    private Subscription mJobDetailsSubscription;

    public static Fragment newInstance() {
        return new QuestionnaireFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_questionnaire;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {
        RxUtils.click(btnNext)
                .map(o -> checkAnswer())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onValidationResult);

    }

    private List<Integer> checkAnswer() {
        List<Integer> checkList = new ArrayList<>();
        for (int i = 0; i < mQuestionsContainer.getChildCount(); i++) {
            final View vContainer = mQuestionsContainer.getChildAt(i);
            final RadioButton yes = (RadioButton) vContainer.findViewById(R.id.cbYes);
            final RadioButton no = (RadioButton) vContainer.findViewById(R.id.cbNo);
            yes.setOnCheckedChangeListener((compoundButton, b) -> {
                yes.setButtonDrawable(R.drawable.radiobutton_button);
                no.setButtonDrawable(R.drawable.radiobutton_button);
            });
            no.setOnCheckedChangeListener((compoundButton, b) -> {
                yes.setButtonDrawable(R.drawable.radiobutton_button);
                no.setButtonDrawable(R.drawable.radiobutton_button);
            });
            if (!yes.isChecked() && !no.isChecked()) {
                checkList.add(i);
            }

        }
        return checkList;
    }

    private void onValidationResult(List<Integer> uncheckedAnswer) {

        if (uncheckedAnswer.size() > 0) {
            Toast.makeText(getActivity(), "Check answer", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < uncheckedAnswer.size(); i++) {
                final View vContainer = mQuestionsContainer.getChildAt(uncheckedAnswer.get(i));
                RadioButton yes = (RadioButton) vContainer.findViewById(R.id.cbYes);
                RadioButton no = (RadioButton) vContainer.findViewById(R.id.cbNo);
                yes.setButtonDrawable(R.drawable.icn_checkbox_error);
                no.setButtonDrawable(R.drawable.icn_checkbox_error);
            }
        } else {
            sendJobDetails();
        }
    }

    private void sendJobDetails() {
        showLoadingDialog();
        if (mJobDetailsSubscription != null) removeSubscription(mJobDetailsSubscription);
        mJobDetailsSubscription = RestClient.getInstance().sendJobDetails()
                .subscribe(aBoolean -> onSendJobDetailsSuccess(), this::onSendJobDetailsError);
        addSubscription(mJobDetailsSubscription);
    }

    @Override
    protected boolean isAllowGoHome() {
        return false;
    }

    @Override
    public void onPageSelected() {

    }

    private void onSendJobDetailsSuccess() {
        hideLoadingDialog();
        storeJobDetails();
        BusProvider.getInstance().post(new SwitchJobDetailsEvent());
    }

    private void onSendJobDetailsError(Throwable _throwable) {
        hideLoadingDialog();
        showErrorDialog(ErrorParser.parse(_throwable));
    }

    private void storeJobDetails() {
        // TODO: add storing job details to DB
    }
}
