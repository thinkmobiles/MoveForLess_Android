package com.miami.moveforless.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.miami.moveforless.R;
import com.miami.moveforless.SignatureDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by klim on 22.10.15.
 */
public class QuestionnaireFragment extends BaseFragment {
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
        RxView.clicks(btnNext)
                .throttleFirst(1, TimeUnit.SECONDS)
                .map(o -> checkAnswer())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integers -> onValidationResult(integers));
    }

    private List<Integer> checkAnswer() {
        List<Integer> checkList = new ArrayList<>();
        for (int i = 0; i < mQuestionsContainer.getChildCount(); i++) {
            final View vContainer = mQuestionsContainer.getChildAt(i);
            vContainer.setBackgroundColor(Color.TRANSPARENT);
            final RadioButton yes = (RadioButton) vContainer.findViewById(R.id.cbYes);
            final RadioButton no = (RadioButton) vContainer.findViewById(R.id.cbNo);
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
                mQuestionsContainer.getChildAt(uncheckedAnswer.get(i)).setBackgroundResource(R.drawable.unchecked_question_background);
            }
        } else {
            Toast.makeText(getActivity(), "Go to next screen", Toast.LENGTH_SHORT).show();
        }
    }

}
