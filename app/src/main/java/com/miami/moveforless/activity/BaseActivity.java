package com.miami.moveforless.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.miami.moveforless.R;

import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by SetKrul on 14.07.2015.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @BindString(R.string.login) String loadingText;

    private ProgressDialog progressDialog;

    @SuppressWarnings("unchecked")
    public final <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

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

    protected void showLoadingDialog() {
        showLoadingDialog(loadingText);
    }

    protected void showLoadingDialog(String _message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(_message);
        progressDialog.show();
    }

    protected void hideLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
