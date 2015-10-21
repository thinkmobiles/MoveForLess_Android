package com.miami.moveforless.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.globalconstants.SharedPrefConst;
import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.rest.RestClientApi;
import com.miami.moveforless.rest.request.LoginRequest;
import com.miami.moveforless.rest.response.LoginResponse;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by klim on 20.10.15.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.editext1)
    EditText etEmail;
    @Bind(R.id.editext2)
    EditText etPassword;
    @BindString(R.string.login) String loadingText;

    private CompositeSubscription _subscriptions = new CompositeSubscription();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onResume() {
        super.onResume();
        _subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(_subscriptions);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(_subscriptions);
    }

    @OnClick(R.id.button1)
    public void loginClick(View view) {
        login();
    }

    private Observable<LoginResponse> createLoginRequest() {
        final LoginRequest loginRequest = new LoginRequest(etEmail.getText().toString(), etPassword.getText().toString());
        return RestClientApi.getApi().login(loginRequest.username, loginRequest.password);
    }

    private void login() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(loadingText);
        progressDialog.show();

        _subscriptions.add(
                createLoginRequest()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<LoginResponse>() {
                            @Override
                            public void onCompleted() {
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Error!!!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(LoginResponse loginResponse) {
                                Toast.makeText(LoginActivity.this, "token = " + loginResponse.getToken(), Toast.LENGTH_SHORT).show();
                                SharedPrefManager.getInstance().saveString(SharedPrefConst.SHARED_PREF_USER_TOKEN, loginResponse.getToken());
                            }
                        }));

    }
}
