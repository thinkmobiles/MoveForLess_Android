package com.miami.moveforless.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClientApi;
import com.miami.moveforless.utils.RxUtils;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Subscription;

/**
 * Created by klim on 20.10.15.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.etEmail_AL)
    EditText etEmail;
    @Bind(R.id.etPassword_AL)
    EditText etPassword;
    @Bind(R.id.btnLogin_AL)
    Button btnLogin;

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
        showLoadingDialog(getString(R.string.login));
        loginSubscription =
                RestClientApi.login(etEmail.getText().toString(), etPassword.getText().toString())
                        .subscribe(this::onSuccess, this::onError);
    }

    private void onSuccess(String _token) {
        hideLoadingDialog();
        SharedPrefManager.getInstance().storeToken(_token);
        SharedPrefManager.getInstance().storeUsername(etEmail.getText().toString());
        SharedPrefManager.getInstance().storeUserPassword(etPassword.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    private void onError(Throwable _throwable) {
        hideLoadingDialog();
        Toast.makeText(LoginActivity.this, ErrorParser.parse(_throwable), Toast.LENGTH_SHORT).show();

    }
}
