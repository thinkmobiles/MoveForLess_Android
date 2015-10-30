package com.miami.moveforless.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.miami.moveforless.R;
import com.miami.moveforless.fragments.JobFragment;

import butterknife.Bind;

/**
 * Created by klim on 20.10.15.
 */
public class MainActivity extends BaseFragmentActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);
        getDelegate().getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (getFragmentById(R.id.contentContainer_AM) == null) {
            switchContent(JobFragment.newInstance());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        getMenuInflater().inflate(R.menu.menu_main, _menu);
        return super.onCreateOptionsMenu(_menu);
    }

    public void switchContent(final Fragment _fragment) {
        clearBackStack();
        replaceFragmentWithBackStack(R.id.contentContainer_AM, _fragment);
    }
}
