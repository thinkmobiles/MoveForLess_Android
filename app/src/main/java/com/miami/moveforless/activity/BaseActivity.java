package com.miami.moveforless.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by SetKrul on 14.07.2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

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
}
