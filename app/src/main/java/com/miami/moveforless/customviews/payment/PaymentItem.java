package com.miami.moveforless.customviews.payment;

/**
 * Created by klim on 20.11.15.
 */
public class PaymentItem {

    private int mResIcon;
    private String mTitle;
    private PaymentType mType;

    public PaymentItem(PaymentType _type, String _title, int _icon) {
        mResIcon = _icon;
        mTitle = _title;
        mType = _type;
    }

    public int getIcon() {
        return mResIcon;
    }

    public String getTitle() {
        return mTitle;
    }

    public PaymentType getType() {
        return mType;
    }
}
