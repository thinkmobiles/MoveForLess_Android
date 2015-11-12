package com.miami.moveforless.fragments;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.FragmentType;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.utils.BitmapUtils;
import com.miami.moveforless.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by klim on 22.10.15.
 */
public class QuestionnaireFragment extends BaseJobDetailFragment {
    @Bind(R.id.btnNext_FQ)
    Button btnNext;
    @Bind(R.id.questions_container_FQ)
    LinearLayout mQuestionsContainer;

    public static Fragment newInstance() {
        return new QuestionnaireFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_questionnaire;
    }

    @Override
    protected void setupViews() {
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
            BusProvider.getInstance().post(new SwitchJobDetailsEvent(FragmentType.SIGN_CONTRACT));
        }
    }

    @Override
    protected boolean isAllowGoHome() {
        return false;
    }
}
