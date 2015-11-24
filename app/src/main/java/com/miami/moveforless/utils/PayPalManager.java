package com.miami.moveforless.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.managers.IntentManager;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * pay pal manager
 * Created by SetKrul on 17.11.2015.
 */
public class PayPalManager {

    public void onCreate(Activity _activity) {

        _activity.startService(IntentManager.getPayPalIntent(_activity));
    }

    public void onBuyPressed(Fragment _resultFragment, String _title, float _amount) {

        PayPalPayment thingToBuy = getThingToBuy(_title, String.valueOf(_amount), "USD", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(_resultFragment.getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Const.config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        _resultFragment.getParentFragment().startActivityForResult(intent, Const.REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String _payInformation, String _sum, String _currency, String _paymentIntent) {
        return new PayPalPayment(new BigDecimal(_sum), _currency, _payInformation, _paymentIntent);
    }

    public boolean onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        if (_requestCode == Const.REQUEST_CODE_PAYMENT) {
            if (_resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = _data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.d("pay", confirm.toJSONObject().toString(4));
                        Log.d("pay", confirm.getPayment().toJSONObject().toString(4));
                        return true;
                    } catch (JSONException e) {
                        Log.e("pay", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (_resultCode == Activity.RESULT_CANCELED) {
                Log.d("pay", "The user canceled.");
            } else if (_resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.d("pay", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
        return false;
    }

    private void sendAuthorizationToServer(PayPalAuthorization _authorization) {
        /**
         * TODO: Send the authorization response to your server, where it can
         * exchange the authorization code for OAuth access and refresh tokens.
         *
         * Your server must then store these tokens, so that your server code
         * can execute payments for this user in the future.
         *
         * A more complete example that includes the required app-server to
         * PayPal-server integration is available from
         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
         */
    }

    public void onDestroy(Activity _activity) {
        _activity.stopService(new Intent(_activity, PayPalService.class));
    }
}
