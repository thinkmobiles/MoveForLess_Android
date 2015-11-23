package com.miami.moveforless.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.payment.PaymentCallback;
import com.miami.moveforless.customviews.payment.PaymentView;
import com.miami.moveforless.customviews.payment.PaymentType;
import com.miami.moveforless.dialogs.CheckDialog;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;

/**
 * Created by klim on 29.10.15.
 */
public class PaymentFragment extends BaseJobDetailFragment implements PaymentView.PaymentAction {
    @BindString(R.string.amount_error_message)
    String strAmountMessage;
    @BindString(R.string.payment_error)
    String strPaymentError;
    @BindColor(R.color.cyan_dark)
    int cyanDark;

    @Bind(R.id.payments_container_FP)
    LinearLayout mContainer;
    @Bind(R.id.tvTotalAmount_FP)
    TextView tvTotalAmount;
    @Bind(R.id.tvRequiredAmount_FP)
    TextView tvRequiredAmount;
    @Bind(R.id.btnNext_FP)
    Button btnNext;

    private List<PaymentView> payments;
    private String mCheckPhotoPath;
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
        payments = new ArrayList<>();
        RxUtils.click(btnNext).subscribe(o -> nextClicked());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Const.CHECK_DIALOG_RESULT_KEY:
                    float amount = data.getFloatExtra(Const.AMOUNT_KEY, 0);
                    addConfirmedAmount(amount);
                    int paymentId = data.getIntExtra(Const.PAYMENT_ID_KEY, -1);
                    ((PaymentCallback)mContainer.getChildAt(paymentId)).onConfirmed();
                    mCheckPhotoPath = data.getStringExtra(Const.CHECK_PHOTO_KEY);
                    break;
            }
        }
    }

    private void addPaymentRow() {
        PaymentView view = new PaymentView(getContext());
        view.setOnPaymentListener(this);
        mContainer.addView(view);
    }

    @Override
    public void onConfirmNeeded(int _position, PaymentType _type, float _value) {
        switch (_type) {
            case CASH:
            case TERMINAL:
                if (validateSimplePayment(_value)) {
                    addConfirmedAmount(_value);
                    ((PaymentCallback)mContainer.getChildAt(_position)).onConfirmed();
                }
                break;
            case CHECK:
                if (validateSimplePayment(_value)) {
                    showCheckDialog(_position, _value);
                }
                break;
            case VISA:
            case MASTERCARD:
            case AMERICANEXPRESS:
            case DISCOVER:

                break;
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
    public void onPaymentTypeSelected() {
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

    @Override
    public void onPageSelected() {

    }

    private boolean validateSimplePayment(float _value) {
        if (_value > 0) {
        } else {
            showErrorDialog(strAmountMessage);
        }
        return true;
    }

    private void addConfirmedAmount(float _value){
        float totalAmount = Float.valueOf(tvTotalAmount.getText().toString());
        tvTotalAmount.setText("" + (totalAmount += _value));
        if (totalAmount >= requiredAmount) {
            btnNext.setBackgroundResource(R.drawable.button_yellow);
            btnNext.setTextColor(cyanDark);
        }

    }

    private void showCheckDialog(int _paymentId, float _amount) {
        Intent intent = new Intent(getActivity(), CheckDialog.class);
        intent.putExtra(Const.PAYMENT_ID_KEY, _paymentId);
        intent.putExtra(Const.AMOUNT_KEY, _amount);
        getParentFragment().startActivityForResult(intent, Const.CHECK_DIALOG_RESULT_KEY);
    }

}
