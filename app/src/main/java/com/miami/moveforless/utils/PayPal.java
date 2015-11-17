package com.miami.moveforless.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.globalconstants.Const;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

/**
 * Created by SetKrul on 17.11.2015.
 */
public class PayPal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_test);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Const.config);
        startService(intent);
    }

    public void onBuyPressed(View pressed) {

        PayPalPayment thingToBuy = getThingToBuy("sample item", "1.75", "USD", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Const.config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, Const.REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String _payInformation, String _sum, String _currency, String _paymentIntent) {
        return new PayPalPayment(new BigDecimal(_sum), _currency, _payInformation, _paymentIntent);
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        if (_requestCode == Const.REQUEST_CODE_PAYMENT) {
            if (_resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = _data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.d("pay", confirm.toJSONObject().toString(4));
                        Log.d("pay", confirm.getPayment().toJSONObject().toString(4));
                        Toast.makeText(getApplicationContext(), "PaymentConfirmation info received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("pay", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (_resultCode == Activity.RESULT_CANCELED) {
                Log.d("pay", "The user canceled.");
            } else if (_resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.d("pay", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (_requestCode == Const.REQUEST_CODE_FUTURE_PAYMENT) {
            if (_resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = _data.getParcelableExtra(PayPalFuturePaymentActivity
                        .EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.d("pay", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.d("pay", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(), "Future Payment code received from PayPal", Toast
                                .LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("pay", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (_resultCode == Activity.RESULT_CANCELED) {
                Log.d("pay", "The user canceled.");
            } else if (_resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.d("pay", "Probably the attempt to previously start the PayPalService had an " + "invalid " +
                        "PayPalConfiguration. Please see the docs.");
            }
        } else if (_requestCode == Const.REQUEST_CODE_PROFILE_SHARING) {
            if (_resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = _data.getParcelableExtra(PayPalProfileSharingActivity
                        .EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.e("pay", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.e("pay", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(), "Profile Sharing code received from PayPal", Toast
                                .LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("pay", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (_resultCode == Activity.RESULT_CANCELED) {
                Log.e("pay", "The user canceled.");
            } else if (_resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.e("pay", "Probably the attempt to previously start the PayPalService had an " + "invalid " +
                        "PayPalConfiguration. Please see the docs.");
            }
        }
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

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
