package com.miami.moveforless.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.Helvetica.HelveticaEditText;
import com.miami.moveforless.customviews.quickActionMenu.ActionItem;
import com.miami.moveforless.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by klim on 29.10.15.
 */
public class PaymentView extends LinearLayout {
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
    HelveticaEditText etAmount;
    @Bind(R.id.ivDone_row)
    ImageView ivDone;

    private PaymentAction mActionListener;

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
        etAmount.setUnderlineColor(R.color.cyan_dark);

        RxUtils.click(ivDelete, o1 -> onDeleteClicked());
        RxUtils.click(tvConfirm, o -> onConfirmClicked());

        final String[] mPaymentType = getResources().getStringArray(R.array.payments_titles);
        final TypedArray icons = getResources().obtainTypedArray(R.array.payment_icons);


        List<ActionItem> items = new ArrayList<>();
        for (int i = 0; i < mPaymentType.length; i++) {
            items.add(new ActionItem(i, mPaymentType[i], icons.getResourceId(i, -1)));

        }
        mSpinner.setDropDownItems(items);
        mSpinner.setOnDropDownItemClicked(_position -> {
            tvAmountDollar.setVisibility(View.VISIBLE);
            etAmount.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
            tvConfirm.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            mActionListener.onPaymentTypeSelected(PaymentView.this, mPaymentType[_position]);
        });
        mSpinner.setHint(strChoosePayment);

    }

    private void onDeleteClicked() {
        mActionListener.onDelete(this);
    }

    private void onConfirmClicked() {
        float amount = parseAmountInput(etAmount.getText().toString());
        if (amount > 0) {
            ivDelete.setVisibility(View.INVISIBLE);
            ivDone.setVisibility(View.VISIBLE);
            tvConfirm.setVisibility(View.GONE);
            tvAmountDollar.setEnabled(false);
            etAmount.setEnabled(false);
            mSpinner.setEnabled(false);
        }
        mActionListener.onConfirm(this, amount);
    }

    public void setOnPaymentListener(PaymentAction _action) {
        mActionListener = _action;
    }

    public interface PaymentAction {
        void onConfirm(View _view, float _value);

        void onPaymentTypeSelected(View _view, String _type);

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

}
