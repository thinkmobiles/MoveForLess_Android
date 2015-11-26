package com.miami.moveforless.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.payment.PaymentCallback;
import com.miami.moveforless.customviews.payment.PaymentView;
import com.miami.moveforless.customviews.payment.PaymentType;
import com.miami.moveforless.activity.CheckActivity;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.utils.PayPalManager;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import rx.Subscription;

/**
 * payment fragment
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

    private String mCheckPhotoPath;
    private float mRequiredAmount = 150;
    private PayPalManager mPayPalManager;
    private int mConfirmPosition;
    private float mConfirmAmount;
    private PaymentType mConfirmType;
    private String mConfirmDescription;
    private Subscription mPaymentSubscription;

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
        mPayPalManager = new PayPalManager();
        mPayPalManager.onCreate(getActivity());

        addPaymentRow();
        tvRequiredAmount.setText("" + mRequiredAmount);
        RxUtils.click(btnNext).subscribe(o -> nextClicked());
    }

    @Override
    public void onDestroyView() {
        mPayPalManager.onDestroy(getActivity());
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Const.CHECK_DIALOG_RESULT_KEY:

                    addConfirmedAmount(mConfirmAmount);
                    storeConfirmedPayment(mConfirmType, mConfirmDescription, mConfirmAmount);
                    ((PaymentCallback) mContainer.getChildAt(mConfirmPosition)).onConfirmed();
                    mCheckPhotoPath = data.getStringExtra(Const.CHECK_PHOTO_KEY);
                    break;
                case Const.REQUEST_CODE_PAYMENT:
                    if (mPayPalManager.onActivityResult(requestCode, resultCode, data)) {
                        addConfirmedAmount(mConfirmAmount);
                        storeConfirmedPayment(mConfirmType, mConfirmDescription, mConfirmAmount);
                        ((PaymentCallback) mContainer.getChildAt(mConfirmPosition)).onConfirmed();
                    }
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
        mConfirmPosition = _position;
        mConfirmAmount = _value;
        mConfirmType = _type;
        mConfirmDescription = "Payment ID LD0000111";

        if (_value > 0) {
            switch (_type) {
                case CASH:
                case TERMINAL:
                    addConfirmedAmount(_value);
                    storeConfirmedPayment(mConfirmType, mConfirmDescription, mConfirmAmount);
                    ((PaymentCallback) mContainer.getChildAt(mConfirmPosition)).onConfirmed();
                    break;
                case CHECK:
                    showCheckDialog();
                    break;
                case VISA:
                case MASTERCARD:
                case AMERICANEXPRESS:
                case DISCOVER:
                    showPayPalActivity(mConfirmDescription, mConfirmAmount);
                    break;
            }
        } else {
            showErrorDialog(strAmountMessage);
        }
    }

    private void nextClicked() {
        showLoadingDialog();
        if (mPaymentSubscription != null) removeSubscription(mPaymentSubscription);
        mPaymentSubscription = RestClient.getInstance().sendPayment()
                .subscribe(aBoolean -> onSendPaymentSuccess(), this::onSendPaymentError);
        addSubscription(mPaymentSubscription);
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

    private void addConfirmedAmount(float _value) {
        float totalAmount = Float.valueOf(tvTotalAmount.getText().toString());
        tvTotalAmount.setText("" + (totalAmount += _value));
        if (totalAmount >= mRequiredAmount) {
            btnNext.setEnabled(true);
        }

    }

    private void showCheckDialog() {
        Intent intent = new Intent(getActivity(), CheckActivity.class);
        getParentFragment().startActivityForResult(intent, Const.CHECK_DIALOG_RESULT_KEY);
    }

    private void showPayPalActivity(String _title, float _amount) {
        mPayPalManager.onBuyPressed(this, _title, _amount);
    }

    private void storeConfirmedPayment(PaymentType _type, String _description, float _amount) {
        // TODO: add storing confirmed payment to DB
    }

    private void onSendPaymentSuccess() {
        hideLoadingDialog();
        BusProvider.getInstance().post(new SwitchJobDetailsEvent());
    }

    private void onSendPaymentError(Throwable _throwable) {
        hideLoadingDialog();
        showErrorDialog(ErrorParser.parse(_throwable));
    }

}
