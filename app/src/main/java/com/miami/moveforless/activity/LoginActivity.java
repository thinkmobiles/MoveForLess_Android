package com.miami.moveforless.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.BindColor;
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
    @Bind(R.id.root_container_AL)
    View mRootView;
    @BindColor(R.color.yellow) int clrYellow;
    @BindColor(R.color.cyan_dark) int clrCyan;

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
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void login() {
        showLoadingDialog(getString(R.string.login));
        if (loginSubscription != null) removeSubscription(loginSubscription);
        loginSubscription = RestClient.getInstance().login(etEmail.getText().toString(), etPassword.getText().toString())
                        .subscribe(this::onSuccess, this::onError);
        addSubscription(loginSubscription);
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

        Snackbar snackbar = Snackbar.
                make(mRootView, ErrorParser.parse(_throwable), Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(clrYellow);
        snackbar.show();
    }
}
