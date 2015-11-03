package com.miami.moveforless.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.dialogs.RouteDialog;
import com.miami.moveforless.fragments.ScheduleFragment;
import com.miami.moveforless.location.TrackingService;
import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.rest.response.LogoutResponse;

import butterknife.Bind;
import rx.Subscription;

/**
 * Created by klim on 20.10.15.
 */
public class MainActivity extends BaseFragmentActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Subscription mLogoutSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);
        getDelegate().getSupportActionBar().setDisplayShowTitleEnabled(false);
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
            case R.id.menu_navigation:
                RouteDialog dialog = new RouteDialog();
                dialog.show(getSupportFragmentManager(), "");
//                openGoogleDirection();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openGoogleDirection() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=Seattle, Lakewood");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {

        }
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
            SharedPrefManager.getInstance().storeUserPassword("");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Logout was failed", Toast.LENGTH_SHORT).show();
        }

    }

    private void logoutError(Throwable _throwable) {
        hideLoadingDialog();
        Toast.makeText(MainActivity.this, ErrorParser.parse(_throwable), Toast.LENGTH_SHORT).show();
    }

}
