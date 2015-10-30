package com.miami.moveforless.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.miami.moveforless.R;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by klim on 29.10.15.
 */
public class PaymentView extends LinearLayout {

    @Bind(R.id.btnPaymentDelete_row)
    ImageView ivDelete;
    @Bind(R.id.btnConfirm_row)
    TextView tvConfirm;
    @Bind(R.id.spinnerSelector_row)
    Spinner mSpinner;
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
        etAmount.setUnderlineColor(R.color.cyan_800);

        final String[] mPaymentType = getResources().getStringArray(R.array.payments_type);

        RxView.clicks(ivDelete).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(o -> onDeleteClicked());
        RxView.clicks(tvConfirm).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(o -> onConfirmClicked());

        final HintTextAdapter<String> adapter = new HintTextAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mPaymentType);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(adapter.getCount());
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position < adapter.getCount()) {
                    tvAmountDollar.setVisibility(View.VISIBLE);
                    etAmount.setVisibility(View.VISIBLE);
                    ivDelete.setVisibility(View.VISIBLE);
                    tvConfirm.setVisibility(View.VISIBLE);
                    etAmount.requestFocus();
                    mActionListener.onPaymentTypeSelected(PaymentView.this, mPaymentType[position]);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void onDeleteClicked() {
        mActionListener.onDelete(this);
    }

    private void onConfirmClicked() {
        float amount = parseAmountInput(etAmount.getText().toString());
            ivDelete.setVisibility(View.INVISIBLE);
            ivDone.setVisibility(View.VISIBLE);
            tvConfirm.setVisibility(View.GONE);
            tvAmountDollar.setEnabled(false);
            etAmount.setEnabled(false);
            mSpinner.setEnabled(false);
            mActionListener.onConfirm(this, amount);

    }

    public void setOnPaymentListner(PaymentAction _action) {
        mActionListener = _action;
    }

    public static interface PaymentAction {
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
