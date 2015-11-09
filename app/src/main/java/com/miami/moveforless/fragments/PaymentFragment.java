package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.PaymentView;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by klim on 29.10.15.
 */
public class PaymentFragment extends BaseJobDetailFragment implements PaymentView.PaymentAction{
    @BindString(R.string.amount_error_message)
    String strAmountMessage;
    @Bind(R.id.payments_container_FP)
    LinearLayout mContainer;
    @Bind(R.id.tvTotalAmount_FP)
    TextView tvTotalAmount;


    public static PaymentFragment newInstance() {
        Bundle args = new Bundle();
        PaymentFragment fragment = new PaymentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_payment;
    }

    @Override
    protected void setupViews() {
        addPaymentRow();
    }

    private void addPaymentRow() {
        PaymentView view = new PaymentView(getContext());
        view.setOnPaymentListner(this);

        mContainer.addView(view);
    }

    @Override
    public void onConfirm(View _view, float _value) {
        if (_value > 0) {
            float totalAmount = Float.valueOf(tvTotalAmount.getText().toString());
            tvTotalAmount.setText("" + (totalAmount += _value));
        } else {
            showInfoDialog(strAmountMessage);
        }
    }

    @Override
    public void onPaymentTypeSelected(View _view, String _type) {
        addPaymentRow();
    }

    @Override
    public void onDelete(View _view) {
        mContainer.removeView(_view);
    }

    @Override
    protected boolean isAllowGoHome() {
        return false;
    }
}
