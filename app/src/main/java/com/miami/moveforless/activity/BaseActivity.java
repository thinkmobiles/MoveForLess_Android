package com.miami.moveforless.activity;

import android.app.ProgressDialog;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.miami.moveforless.R;
import com.miami.moveforless.dialogs.ErrorDialog;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.utils.RxUtils;

import butterknife.BindString;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by SetKrul on 14.07.2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @BindString(R.string.loading) String loadingText;

    private ProgressDialog progressDialog;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions);
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(mSubscriptions);
        BusProvider.getInstance().unregister(this);
    }

    protected void addSubscription(Subscription _subscription) {
        mSubscriptions.add(_subscription);
    }

    protected void removeSubscription(Subscription _subscription) {
        mSubscriptions.remove(_subscription);
    }

    protected void showLoadingDialog(String _message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(_message);
        progressDialog.show();
    }

    protected void hideLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected void showErrorDialog(String _message) {
        ErrorDialog dialog = new ErrorDialog();
        dialog.setMessage(_message);
        dialog.show(getSupportFragmentManager(), "");
    }
}
