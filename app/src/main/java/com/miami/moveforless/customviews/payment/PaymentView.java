package com.miami.moveforless.customviews.payment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.CustomSpinner;
import com.miami.moveforless.customviews.quickActionMenu.PaymentActionItem;
import com.miami.moveforless.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * custom view for payment
 * Created by klim on 29.10.15.
 */
public class PaymentView extends LinearLayout implements PaymentCallback{
    @BindString(R.string.choose_payment_type) String strChoosePayment;
    @Bind(R.id.btnPaymentDelete_row)
    ImageView ivDelete;
    @Bind(R.id.btnConfirm_row)
    TextView tvConfirm;
    @Bind(R.id.spinnerSelector_row)
    CustomSpinner mSpinner;
    @Bind(R.id.amount_dollar_row)
    TextView tvAmountDollar;
    @Bind(R.id.tvAmount_row)
    EditText etAmount;
    @Bind(R.id.ivDone_row)
    ImageView ivDone;

    private PaymentAction mActionListener;
    private List<PaymentActionItem> mDropDownList;
    private PaymentType mSelectedType;

    public PaymentView(Context context) {
        this(context, null);
    }

    public PaymentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaymentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.payment_row_layout, this);

        ButterKnife.bind(this);

        RxUtils.click(ivDelete, o1 -> onDeleteClicked());
        RxUtils.click(tvConfirm, o -> onConfirmClicked());

        mDropDownList = generateDropDownList();
        mSpinner.setDropDownItems(mDropDownList);
        mSpinner.setOnDropDownItemClicked(_position -> {
            tvAmountDollar.setVisibility(View.VISIBLE);
            etAmount.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
            tvConfirm.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            mActionListener.onPaymentTypeSelected();
            mSelectedType = mDropDownList.get(_position).getType();
        });
        mSpinner.setHint(strChoosePayment);

    }

    private void onDeleteClicked() {
        mActionListener.onDelete(this);
    }

    private void onConfirmClicked() {
        float amount = parseAmountInput(etAmount.getText().toString());
        int position = ((ViewGroup)getParent()).indexOfChild(this);
        mActionListener.onConfirmNeeded(position, mSelectedType, amount);
    }

    public void setOnPaymentListener(PaymentAction _action) {
        mActionListener = _action;
    }

    @Override
    public void onConfirmed() {
        ivDelete.setVisibility(View.INVISIBLE);
        ivDone.setVisibility(View.VISIBLE);
        tvConfirm.setVisibility(View.GONE);
        tvAmountDollar.setEnabled(false);
        etAmount.setEnabled(false);
        mSpinner.setEnabled(false);
    }

    public interface PaymentAction {
        void onConfirmNeeded(int position, PaymentType _type, float _value);

        void onPaymentTypeSelected();

        void onDelete(View _view);
    }

    private float parseAmountInput(String _stringAmount) {
        if (_stringAmount.isEmpty()) return 0;

        final Float amount = Float.valueOf(_stringAmount);
        if (amount <= 0) return 0;

        try {
            return Float.valueOf(_stringAmount);
        } catch (Exception e) {
            return -1;
        }
    }

    private List<PaymentActionItem> generateDropDownList() {
        List<PaymentActionItem> items = new ArrayList<>();
        items.add(new PaymentActionItem(0, "Cash", R.drawable.icn_quote, PaymentType.CASH));
        items.add(new PaymentActionItem(1, "Check", R.drawable.icn_check_book, PaymentType.CHECK));
        items.add(new PaymentActionItem(2, "MasterCard", R.drawable.icn_mastercard, PaymentType.MASTERCARD));
        items.add(new PaymentActionItem(3, "Visa", R.drawable.icn_visa, PaymentType.VISA));
        items.add(new PaymentActionItem(4, "AmericanExpress", R.drawable.icn_american_express, PaymentType.AMERICANEXPRESS));
        items.add(new PaymentActionItem(5, "Discover", R.drawable.icn_discover, PaymentType.DISCOVER));
        items.add(new PaymentActionItem(6, "PayPal", R.drawable.icn_paypal, PaymentType.PAYPAL));
        items.add(new PaymentActionItem(7, "Terminal", R.drawable.icn_terminal, PaymentType.TERMINAL));
        return items;
    }

}
