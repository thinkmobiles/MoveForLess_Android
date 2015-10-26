package com.miami.moveforless.activity;

import android.os.Bundle;
import android.view.View;
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
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by klim on 20.10.15.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.editext1)
    EditText etEmail;
    @Bind(R.id.editext2)
    EditText etPassword;


    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private Subscription loginSubscription;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        _subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(_subscriptions);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        RxUtils.unsubscribeIfNotNull(_subscriptions);
        loginSubscription.unsubscribe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @OnClick(R.id.button1)
    public void loginClick(View view) {
        login();
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
        SharedPrefManager.getInstance().saveString(SharedPrefConst.SHARED_PREF_USER_TOKEN, _token);
    }

    private void onError(Throwable _throwable) {
        hideLoadingDialog();
        Toast.makeText(LoginActivity.this, ErrorParser.parse(_throwable), Toast.LENGTH_SHORT).show();

    }
}
