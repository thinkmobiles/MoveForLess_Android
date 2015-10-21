package com.miami.moveforless.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.miami.moveforless.R;
import com.miami.moveforless.TestLoader;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by klim on 20.10.15.
 */
public class LoginActivity extends BaseActivity implements LoaderManager.LoaderCallbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @OnClick (R.id.btnLogin_AL)
    public void LoginClick(View view) {
        getLoaderManager().initLoader(1, null ,this).forceLoad();
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return new TestLoader(this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
