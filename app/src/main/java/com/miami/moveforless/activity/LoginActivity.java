package com.miami.moveforless.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.miami.moveforless.App;
import com.miami.moveforless.Exceptions.ConnectionException;
import com.miami.moveforless.R;
import com.miami.moveforless.customviews.CustomProgressBar;
import com.miami.moveforless.database.DatabaseController;
import com.miami.moveforless.dialogs.ConfirmDialog;
import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.utils.RxUtils;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindString;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by klim on 20.10.15.
 */
public class LoginActivity extends BaseActivity {
    @BindString(R.string.login_loading)         String strLogin;
    @BindString(R.string.connection_error)      String strConnectionError;
    @Bind(R.id.etEmail_AL)                      EditText etEmail;
    @Bind(R.id.etPassword_AL)                   EditText etPassword;
    @Bind(R.id.btnLogin_AL)                     Button btnLogin;
    @Bind(R.id.non_animation_logo_AL)           ViewGroup mNonAnimLogoContainer;
    @Bind(R.id.animation_logo_AL)               ViewGroup mAnimLogoContainer;
    @Bind(R.id.login_container_AL)              ViewGroup mLoginContainer;
    @Bind(R.id.root_container_AL)               ViewGroup mRootContainer;
    @Bind(R.id.progress_AL)                     CustomProgressBar mProgressBar;

    private Subscription loginSubscription;
    private Subscription mJobDataSubscription;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_login);

        RxUtils.click(btnLogin, o -> login());

        if (SharedPrefManager.getInstance().retrieveToken().isEmpty()) {
            mNonAnimLogoContainer.setVisibility(View.VISIBLE);
            mLoginContainer.setVisibility(View.VISIBLE);
            mAnimLogoContainer.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            mAnimLogoContainer.setVisibility(View.VISIBLE);
            mNonAnimLogoContainer.setVisibility(View.INVISIBLE);
            mLoginContainer.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            getJobsData();
        }

    }

    private void login() {
        showLoadingDialog(strLogin);
        if (loginSubscription != null) removeSubscription(loginSubscription);
        loginSubscription = RestClient.getInstance().login(etEmail.getText().toString(), etPassword.getText().toString())
                .subscribe(this::onLoginSuccess, this::onLoginError);
        addSubscription(loginSubscription);
    }

    private void onLoginSuccess(String _token) {
        SharedPrefManager.getInstance().storeToken(_token);
        SharedPrefManager.getInstance().storeUsername(etEmail.getText().toString());
        getJobsData();
    }

    private void onLoginError(Throwable _throwable) {
        hideLoadingDialog();
        showErrorDialog(ErrorParser.parse(_throwable));
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void onJobDataError(Throwable _throwable) {
        if (ErrorParser.checkConnectionError(_throwable) instanceof ConnectionException) {
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setMessage(strConnectionError);
            dialog.setOnPositiveListener(view -> startMainActivity(0));
            dialog.setOnNegativeListener(view -> finish());
            dialog.show(getSupportFragmentManager(), "");
        } else {
            String error = ErrorParser.parse(_throwable);
            if (error.equals("Invalid token")) {
                startTransitionAnimation();
            } else {
                showErrorDialog(error, view -> finish());
            }
        }
    }


    private void getJobsData() {
        if (mJobDataSubscription != null) removeSubscription(mJobDataSubscription);
        mJobDataSubscription = Observable.combineLatest(
                RestClient.getInstance().jobList(),
                RestClient.getInstance().getListNumberMen(),
                RestClient.getInstance().getListMoveSize(),
                (jobResponses1, listNumberMenResponse, listMoveSizeResponse) -> {
                    DatabaseController.getInstance().dropDataBase(App.getAppContext());
                    saveInDatabase(jobResponses1);
                    saveInDatabase(listNumberMenResponse.number_men);
                    saveInDatabase(listMoveSizeResponse.move_sizes);

                    return jobResponses1 != null && listNumberMenResponse != null && listMoveSizeResponse != null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onJobDataSuccess, this::onJobDataError);
        addSubscription(mJobDataSubscription);
    }

    private void saveInDatabase(List<? extends BaseModel> _list) {
        for (BaseModel item : _list) {
            item.save();
        }
    }

    private void onJobDataSuccess(boolean _isloaded) {
        hideLoadingDialog();
        startMainActivity(2);
    }

    public void startTransitionAnimation() {
        mProgressBar.setVisibility(View.INVISIBLE);
        int startY = mAnimLogoContainer.getTop();
        float targetY = getResources().getDimension(R.dimen.logo_margin_top);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mAnimLogoContainer, "y", startY, targetY);
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mLoginContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }


    private void startMainActivity(int _delay) {
        addSubscription(Observable.just(null)
                .delay(_delay, TimeUnit.SECONDS)
                .subscribe(o -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }));
    }
}
