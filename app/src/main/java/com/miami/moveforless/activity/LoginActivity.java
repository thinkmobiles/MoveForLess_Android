package com.miami.moveforless.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.globalconstants.SharedPrefConst;
import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClientApi;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by klim on 20.10.15.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.etEmail_AL)      EditText etEmail;
    @Bind(R.id.etPassword_AL)   EditText etPassword;
    @Bind(R.id.btnLogin_AL)     Button btnLogin;

    private Subscription loginSubscription;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        if (!SharedPrefManager.getInstance().retrieveToken().isEmpty()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_login);
        RxUtils.click(btnLogin, o -> login());
    }

    @Override
    protected void onPause() {
        super.onPause();
        loginSubscription.unsubscribe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void login() {
        showLoadingDialog();
        loginSubscription =
                RestClientApi.login(etEmail.getText().toString(), etPassword.getText().toString())
                        .subscribe(token -> onSuccess(token), e -> onError(e));

    }

    private void onSuccess(String _token) {
        hideLoadingDialog();
        Toast.makeText(LoginActivity.this, "token = " + _token, Toast.LENGTH_SHORT).show();
        SharedPrefManager.getInstance().storeToken(_token);
    }

    private void onError(Throwable _throwable) {
        hideLoadingDialog();
        Toast.makeText(LoginActivity.this, ErrorParser.parse(_throwable), Toast.LENGTH_SHORT).show();

    }
}
