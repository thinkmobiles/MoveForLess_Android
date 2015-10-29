package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.HelveticaEditText;
import com.miami.moveforless.customviews.HintTextAdapter;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by klim on 29.10.15.
 */
public class PaymentFragment extends BaseFragment {
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

        mContainer.addView(addRow());

    }

    private View addRow() {
        final String[] mPaymentType = getResources().getStringArray(R.array.payments_type);

        View view = mLayoutInflater.inflate(R.layout.payment_row_layout, mContainer, false);
        final ImageView ivDelete = (ImageView) view.findViewById(R.id.btnPaymentDelete_row);
        final TextView tvConfirm = (TextView) view.findViewById(R.id.btnConfirm_row);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerSelector_row);
        final TextView tvAmountDollar = (TextView) view.findViewById(R.id.amount_dollar_row);
        final HelveticaEditText etAmount = (HelveticaEditText) view.findViewById(R.id.tvAmount_row);
        final ImageView ivDone = (ImageView) view.findViewById(R.id.ivDone_row);

        etAmount.setUnderlineColor(R.color.cyan_800);
        ivDelete.setOnClickListener(view1 -> mContainer.removeView(view));
        tvConfirm.setOnClickListener((View v) -> {
            String validError = validateAmountInput(etAmount.getText().toString());

            if (validError.isEmpty()) {
                float totalAmount = Float.valueOf(tvTotalAmount.getText().toString());
                final float amount = Float.valueOf(etAmount.getText().toString());
                tvTotalAmount.setText("" + (totalAmount += amount));

                ivDelete.setVisibility(View.INVISIBLE);
                ivDone.setVisibility(View.VISIBLE);
                tvConfirm.setVisibility(View.GONE);
                tvAmountDollar.setEnabled(false);
                etAmount.setEnabled(false);
                spinner.setEnabled(false);
            } else {
                showInfoDialog(validError);
            }

        });

        final HintTextAdapter<String> adapter = new HintTextAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mPaymentType);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position < adapter.getCount()) {
                    Toast.makeText(getActivity(), "Selected: " + mPaymentType[position], Toast.LENGTH_SHORT).show();
                    tvAmountDollar.setVisibility(View.VISIBLE);
                    etAmount.setVisibility(View.VISIBLE);
                    ivDelete.setVisibility(View.VISIBLE);
                    tvConfirm.setVisibility(View.VISIBLE);
                    mContainer.addView(addRow());
                    etAmount.requestFocus();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private String validateAmountInput(String _stringAmount) {
        if (_stringAmount.isEmpty()) return "Please, enter amount";

        final Float amount = Float.valueOf(_stringAmount);
        if (amount <= 0) return "Please, enter amount";

        return "";
    }
}
