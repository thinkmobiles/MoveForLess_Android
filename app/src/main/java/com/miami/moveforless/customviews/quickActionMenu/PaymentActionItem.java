package com.miami.moveforless.customviews.quickActionMenu;

import com.miami.moveforless.customviews.payment.PaymentType;

/**
 * Action item, displayed as menu with icon and text.
 *
 * @author Lorensius. W. L. T <lorenz@londatiga.net>
 *         <p>
 *         Contributors:
 *         - Kevin Peck <kevinwpeck@gmail.com>
 */
public class PaymentActionItem extends ActionItem{

    private PaymentType mType;
    /**
     * Constructor
     *
     * @param actionId Action id for case statements
     * @param title    Title
     * @param icon     Icon to use
     */
    public PaymentActionItem(int actionId, String title, int icon, PaymentType _type) {
        super(actionId, title, icon);
        mType = _type;
    }

    public PaymentType getType() {
        return mType;
    }

}