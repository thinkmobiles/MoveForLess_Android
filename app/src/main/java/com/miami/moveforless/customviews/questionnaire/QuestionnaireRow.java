package com.miami.moveforless.customviews.questionnaire;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.CustomSpinner;
import com.miami.moveforless.customviews.quickActionMenu.ActionItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * question row
 * Created by klim on 26.11.15.
 */
public class QuestionnaireRow extends LinearLayout {

    @Nullable @Bind(R.id.questionText_QR)
    TextView tvText;
    @Nullable @Bind(R.id.rgCheckBoxes)
    RadioGroup rgCheckboxContainer;
    @Nullable @Bind(R.id.cbYes)
    RadioButton rbYes;
    @Nullable @Bind(R.id.cbNo)
    RadioButton rbNo;
    @Nullable @Bind(R.id.inputnumber_QR)
    EditText etInputNumber;
    @Nullable @Bind(R.id.spinner_QR)
    CustomSpinner sSpinner;
    @Nullable @Bind(R.id.etNotes_QR)
    EditText etNotes;

    private QuestionnaireModel mModel;
    private QuestionnaireCallBack mCallBack;
    private boolean isChecked = false;

    public QuestionnaireRow(Context context, QuestionnaireModel _model) {
        this(context, null, _model);
    }

    public QuestionnaireRow(Context context, AttributeSet attrs, QuestionnaireModel _model) {
        this(context, attrs, 0, _model);
    }

    public QuestionnaireRow(Context context, AttributeSet attrs, int defStyleAttr,  QuestionnaireModel _model) {
        super(context, attrs, defStyleAttr);
        mModel = _model;
        switch (_model.getType()) {
            case CHECKBOX:
                inflate(getContext(), R.layout.questionnaire_row_checkbox, this);
                break;
            case SPINNER:
                inflate(getContext(), R.layout.questionnaire_row_spinner, this);
                break;

            case INPUTNUMBER:
                inflate(getContext(), R.layout.questionnaire_row_inputnumber, this);
                break;
            case NOTES:
                inflate(getContext(), R.layout.questionnaire_row_text, this);
                break;
        }
        ButterKnife.bind(this);



        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                final int position = ((ViewGroup)getParent()).indexOfChild(QuestionnaireRow.this);

                SpannableStringBuilder builder = new SpannableStringBuilder();
                String mainText = (position + 1) + ". " + mModel.getQuestionText();
                builder.append(mainText);

                if (mModel.isRequired()) {
                    SpannableString redSpan = new SpannableString("*");
                    redSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, redSpan.length(), 0);
                    builder.append(redSpan);
                }

                tvText.setText(builder, TextView.BufferType.SPANNABLE);
            }
        });


        if (rgCheckboxContainer != null) {
            if (mModel.isHasDefaultAnswer()) {
                isChecked = true;
                if (mModel.getDefaultAnswer()) rbYes.setChecked(true);
                else rbNo.setChecked(true);
            }
            rbYes.setOnCheckedChangeListener((compoundButton, b) -> CheckBoxClicked());
            rbNo.setOnCheckedChangeListener((compoundButton, b) -> CheckBoxClicked());
        }

        if (sSpinner != null) {
            sSpinner.setText(mModel.getDefaultSpinnerText());
            List<ActionItem> items = new ArrayList<>();
            for (int i = 0; i <mModel.getDropdownItems().size(); i++) {
                items.add(new ActionItem(i, mModel.getDropdownItems().get(i), 0));
            }
            sSpinner.setDropDownItems(items);
        }

        if (etInputNumber != null) {
            etInputNumber.setHint("" + mModel.getDefaultNumber());
        }
    }

    public boolean isChecked() {
        return isChecked;
    }

    private void CheckBoxClicked() {
        isChecked = true;
        if (mCallBack != null) mCallBack.onChecked();
    }


    public void setCallBack(QuestionnaireCallBack _callBack) {
        mCallBack = _callBack;
    }

    public interface QuestionnaireCallBack {
        void onChecked();
    }

}
