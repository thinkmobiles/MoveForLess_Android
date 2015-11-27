package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.ParallaxScrollView;
import com.miami.moveforless.customviews.questionnaire.QuestionType;
import com.miami.moveforless.customviews.questionnaire.QuestionnaireModel;
import com.miami.moveforless.customviews.questionnaire.QuestionnaireRow;
import com.miami.moveforless.database.DatabaseController;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.rest.response.MoveSizeResponse;
import com.miami.moveforless.rest.response.NumberMenResponse;
import com.miami.moveforless.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscription;

/**
 * questionnaire screen
 * Created by klim on 22.10.15.
 */
public class QuestionnaireFragment extends BaseJobDetailFragment implements QuestionnaireRow.QuestionnaireCallBack{
    @Bind(R.id.btnNext_FQ)
    Button btnNext;
    @Bind(R.id.questions_container_FQ)
    LinearLayout mQuestionsContainer;
    @Bind(R.id.scroll_view_FQ)
    ParallaxScrollView scrollView;

    private Subscription mJobDetailsSubscription;
    private List<QuestionnaireModel> questions;

    public static Fragment newInstance() {
        return new QuestionnaireFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_questionnaire;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {

        scrollView.parallaxViewBackgroundBy(scrollView, ContextCompat.getDrawable(getContext(), R.drawable
                .job_details_background), .2f);

        questions = createQuestionnaireList();

        for (QuestionnaireModel item : questions) {
            QuestionnaireRow row = new QuestionnaireRow(getContext(), item);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 10);
            row.setLayoutParams(params);
            row.setCallBack(this);
            mQuestionsContainer.addView(row);
        }

        RxUtils.click(btnNext, o -> nextClicked());

    }

    private void nextClicked() {
        sendJobDetails();
    }
///*    private List<Integer> checkAnswer() {
//        List<Integer> checkList = new ArrayList<>();
//        for (int i = 0; i < mQuestionsContainer.getChildCount(); i++) {
//            final View vContainer = mQuestionsContainer.getChildAt(i);
//            final RadioButton yes = (RadioButton) vContainer.findViewById(R.id.cbYes);
//            final RadioButton no = (RadioButton) vContainer.findViewById(R.id.cbNo);
//            yes.setOnCheckedChangeListener((compoundButton, b) -> {
//                yes.setButtonDrawable(R.drawable.radiobutton_button);
//                no.setButtonDrawable(R.drawable.radiobutton_button);
//            });
//            no.setOnCheckedChangeListener((compoundButton, b) -> {
//                yes.setButtonDrawable(R.drawable.radiobutton_button);
//                no.setButtonDrawable(R.drawable.radiobutton_button);
//            });
//            if (!yes.isChecked() && !no.isChecked()) {
//                checkList.add(i);
//            }
//
//        }
//        return checkList;
//    }*/
///*
//
//    private void onValidationResult(List<Integer> uncheckedAnswer) {
//
//        if (uncheckedAnswer.size() > 0) {
//            Toast.makeText(getActivity(), "Check answer", Toast.LENGTH_SHORT).show();
//            for (int i = 0; i < uncheckedAnswer.size(); i++) {
//                final View vContainer = mQuestionsContainer.getChildAt(uncheckedAnswer.get(i));
//                RadioButton yes = (RadioButton) vContainer.findViewById(R.id.cbYes);
//                RadioButton no = (RadioButton) vContainer.findViewById(R.id.cbNo);
//                yes.setButtonDrawable(R.drawable.icn_checkbox_error);
//                no.setButtonDrawable(R.drawable.icn_checkbox_error);
//            }
//        } else {
//            sendJobDetails();
//        }
//    }
//*/

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

    private List<QuestionnaireModel> createQuestionnaireList() {
        final String [] questions = getResources().getStringArray(R.array.questionnaire);

        List<MoveSizeResponse> moveSizes = DatabaseController.getInstance().getListMoveSize();
        List<NumberMenResponse> numberMenResponses = DatabaseController.getInstance().getListNumberMen();

        List<String> moves = new ArrayList<>();
        for (MoveSizeResponse move : moveSizes) {
            moves.add(move.name);
        }

        List<String> mensnumber = new ArrayList<>();
        for (NumberMenResponse men: numberMenResponses) {
            mensnumber.add(men.number_men);
        }

        List<String> buildings = new ArrayList<>();
        buildings.add("Highrise");
        buildings.add("not Highrise");

        List<QuestionnaireModel> list = new ArrayList<>();
        list.add(new QuestionnaireModel(QuestionType.CHECKBOX, questions[0], false));
        list.add(new QuestionnaireModel(QuestionType.CHECKBOX, questions[1], false));
        list.add(new QuestionnaireModel(QuestionType.INPUTNUMBER, questions[2], false, 0)); // how many stairs
        list.add(new QuestionnaireModel(QuestionType.CHECKBOX, questions[3], false));
        list.add(new QuestionnaireModel(QuestionType.CHECKBOX, questions[4], false));
        list.add(new QuestionnaireModel(QuestionType.CHECKBOX, questions[5], false));
        list.add(new QuestionnaireModel(QuestionType.SPINNER, questions[6], false, moves, moves.get(0))); //move sizes
        list.add(new QuestionnaireModel(QuestionType.SPINNER, questions[7], false, mensnumber, mensnumber.get(0))); // how many movers
        list.add(new QuestionnaireModel(QuestionType.SPINNER, questions[8], false, mensnumber, mensnumber.get(0))); //how many trucks
        list.add(new QuestionnaireModel(QuestionType.CHECKBOX, questions[9], true));
        list.add(new QuestionnaireModel(QuestionType.NOTES, questions[10], false));
        list.add(new QuestionnaireModel(QuestionType.CHECKBOX, questions[11], true));
        list.add(new QuestionnaireModel(QuestionType.SPINNER, questions[12], false, buildings, buildings.get(0))); // kind of building
        list.add(new QuestionnaireModel(QuestionType.CHECKBOX, questions[13], true));
        list.add(new QuestionnaireModel(QuestionType.CHECKBOX, questions[14], true));
        list.add(new QuestionnaireModel(QuestionType.CHECKBOX, questions[15], true, false));
        return list;
    }

    private boolean validateAnswers() {
        boolean isFullyChecked = true;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).isRequired()) {
                QuestionnaireRow item = (QuestionnaireRow) mQuestionsContainer.getChildAt(i);
                if (!item.isChecked()) {
                    isFullyChecked = false;
                    break;
                }
            }
        }
        return isFullyChecked;
    }

    @Override
    public void onChecked() {
        if (validateAnswers()) btnNext.setEnabled(true);
    }
}
