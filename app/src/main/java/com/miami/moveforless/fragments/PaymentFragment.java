package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.PaymentView;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;

/**
 * Created by klim on 29.10.15.
 */
public class PaymentFragment extends BaseJobDetailFragment implements PaymentView.PaymentAction{
    @BindString(R.string.amount_error_message)
    String strAmountMessage;
    @BindString(R.string.payment_error) String strPaymentError;
    @BindColor(R.color.cyan_dark) int cyanDark;

    @Bind(R.id.payments_container_FP)
    LinearLayout mContainer;
    @Bind(R.id.tvTotalAmount_FP)
    TextView tvTotalAmount;
    @Bind(R.id.tvRequiredAmount_FP)
    TextView tvRequiredAmount;
    @Bind(R.id.btnNext_FP)
    Button btnNext;

    private float requiredAmount = 150;

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
    protected void setupViews(Bundle _savedInstanceState) {
        addPaymentRow();
        tvRequiredAmount.setText("" + requiredAmount);
        RxUtils.click(btnNext).subscribe(o -> nextClicked());
    }

    private void addPaymentRow() {
        PaymentView view = new PaymentView(getContext());
        view.setOnPaymentListener(this);

        mContainer.addView(view);
    }

    @Override
    public void onConfirm(View _view, float _value) {
        if (_value > 0) {
            float totalAmount = Float.valueOf(tvTotalAmount.getText().toString());
            tvTotalAmount.setText("" + (totalAmount += _value));
            if (totalAmount >= requiredAmount) {
                btnNext.setBackgroundResource(R.drawable.button_yellow);
                btnNext.setTextColor(cyanDark);
            }
        } else {
            showInfoDialog(strAmountMessage);
        }
    }

    private void nextClicked() {
        float totalAmount = Float.valueOf(tvTotalAmount.getText().toString());
        if (totalAmount >= requiredAmount) {
            BusProvider.getInstance().post(new SwitchJobDetailsEvent());
        } else {
            showErrorDialog(strPaymentError);
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
