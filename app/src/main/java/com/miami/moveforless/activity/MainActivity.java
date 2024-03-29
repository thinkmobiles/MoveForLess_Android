package com.miami.moveforless.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.fragments.BaseFragment;
import com.miami.moveforless.fragments.ScheduleFragment;
import com.miami.moveforless.fragments.SettingsFragment;
import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.rest.response.LogoutResponse;

import butterknife.Bind;
import rx.Subscription;

/**
 * main activity
 * Created by klim on 20.10.15.
 */
public class MainActivity extends BaseFragmentActivity implements FragmentChanger {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private Subscription mLogoutSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(mToolbar);
        getDelegate().getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.logo);

        if (getFragmentById(R.id.contentContainer_AM) == null) {
            switchContent(ScheduleFragment.newInstance(), false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        getMenuInflater().inflate(R.menu.menu_main, _menu);
        return super.onCreateOptionsMenu(_menu);
    }

    public void switchContent(final Fragment _fragment, boolean _AddToBackStack) {
        if (_AddToBackStack)
            replaceFragmentWithBackStack(R.id.contentContainer_AM, _fragment);
        else
            replaceFragmentWithoutBackStack(R.id.contentContainer_AM, _fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_logout:
                showLoadingDialog(getString(R.string.logout_loading));
                logout();
                break;
            case R.id.menu_refresh:
                // TODO: add refresh logic
                break;
            case R.id.menu_settings:
                SettingsFragment settingsFragment = new SettingsFragment();
                replaceFragmentWithBackStack(R.id.contentContainer_AM, settingsFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentContainer_AM);
        if (fragment != null && fragment instanceof BaseFragment && !(fragment instanceof ScheduleFragment)) {
            ((BaseFragment) fragment).onBackPressed();
        } else
            super.onBackPressed();
    }

    private void logout() {
        if (mLogoutSubscription != null) {
            removeSubscription(mLogoutSubscription);
        }
        mLogoutSubscription = RestClient.getInstance().logout()
                .subscribe(this::logoutSuccess, this::logoutError);
        addSubscription(mLogoutSubscription);
    }

    private void logoutSuccess(LogoutResponse _logoutResponse) {
        hideLoadingDialog();
        if (_logoutResponse.getResult()) {
            SharedPrefManager.getInstance().storeToken("");
            SharedPrefManager.getInstance().storeUsername("");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Logout was failed", Toast.LENGTH_SHORT).show();
        }

    }

    private void logoutError(Throwable _throwable) {
        // TODO: maybe add logic if token expired when user use app
        hideLoadingDialog();
        Toast.makeText(MainActivity.this, ErrorParser.parse(_throwable), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void switchFragment(Fragment _fragment, boolean _isAddToBackStack) {
        switchContent(_fragment, _isAddToBackStack);
    }
}
