package com.miami.moveforless.customviews.questionnaire;

import java.util.List;

/**
 * question model
 * Created by klim on 26.11.15.
 */
public class QuestionnaireModel {
    private String questionText;
    private boolean isRequired;
    private QuestionType type;
    private List<String> dropdownItems;
    private boolean hasDefaultAnswer;
    private boolean defaultAnswer;
    private String defaultSpinnerText;
    private int defaultNumber;
    private boolean isChecked;

    public QuestionnaireModel(QuestionType _type, String _text, boolean _isRequired) {
        questionText = _text;
        type = _type;
        isRequired = _isRequired;
    }

    public QuestionnaireModel(QuestionType _type, String _text, boolean _isRequired, List<String> _dropdownItems, String _defaultSpinnerText) {
        this(_type, _text, _isRequired);
        dropdownItems = _dropdownItems;
        defaultSpinnerText = _defaultSpinnerText;
    }

    public QuestionnaireModel(QuestionType _type, String _text, boolean _isRequired, boolean _defaultAnswer) {
        this(_type, _text, _isRequired);
        hasDefaultAnswer = true;
        defaultAnswer = _defaultAnswer;
    }

    public QuestionnaireModel(QuestionType _type, String _text, boolean _isRequired, int _defaultNumber) {
        this(_type, _text, _isRequired);
        defaultNumber = _defaultNumber;
    }

    public boolean getDefaultAnswer() {
        return defaultAnswer;
    }

    public int getDefaultNumber() {
        return defaultNumber;
    }

    public String getDefaultSpinnerText() {
        return defaultSpinnerText;
    }

    public List<String> getDropdownItems() {
        return dropdownItems;
    }

    public boolean isHasDefaultAnswer() {
        return hasDefaultAnswer;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public String getQuestionText() {
        return questionText;
    }

    public QuestionType getType() {
        return type;
    }
}
