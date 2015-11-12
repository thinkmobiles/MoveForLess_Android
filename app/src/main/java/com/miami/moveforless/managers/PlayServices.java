package com.miami.moveforless.managers;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.miami.moveforless.dialogs.ConfirmDialog;
import com.miami.moveforless.dialogs.ErrorDialog;


public class PlayServices implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "GooglePlayServicesActiv";

    private static final String KEY_IN_RESOLUTION = "is_in_resolution";
    private AppCompatActivity mActivity;
    private ConnectionResult mResult;
    private boolean isConnected = false;

    /**
     * Request code for auto Google Play Services error resolution.
     */
    protected static final int REQUEST_CODE_RESOLUTION = 1;

    /**
     * Google API client.
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * Determines if the client is in a resolution state, and
     * waiting for resolution intent to return.
     */
    private boolean mIsInResolution;

    /**
     * Called when the Activity is made visible.
     * A connection to Play Services need to be initiated as
     * soon as the activity is visible. Registers {@code ConnectionCallbacks}
     * and {@code OnConnectionFailedListener} on the
     * activities itself.
     */

    public PlayServices(Activity _activity) {
        mActivity = (AppCompatActivity) _activity;
    }

    public void onStart() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                    // Optionally, add additional APIs and scopes if required.
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    /**
     * Called when activity gets invisible. Connection to Play Services needs to
     * be disconnected as soon as an activity is invisible.
     */
    public void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Handles Google Play Services resolution callbacks.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_RESOLUTION:
                retryConnecting();
                break;
        }
    }

    private void retryConnecting() {
        mIsInResolution = false;
        if (!mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }
    }

    /**
     * Called when {@code mGoogleApiClient} is connected.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "GoogleApiClient connected");
        isConnected = true;
    }

    /**
     * Called when {@code mGoogleApiClient} connection is suspended.
     */
    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "GoogleApiClient connection suspended");
        retryConnecting();
    }

    /**
     * Called when {@code mGoogleApiClient} is trying to connect but failed.
     * Handle {@code result.getResolution()} if there is a resolution
     * available.
     */
    @Override
    public void onConnectionFailed(ConnectionResult _result) {
        mResult = _result;
    }

    private Location getCurrentLocation() {
        return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    private boolean isAvailable() {
        return isConnected;
    }


    private void onFailedConnection() {
        if (mResult != null) {
            Log.i(TAG, "GoogleApiClient connection failed: " + mResult.toString());
            if (!mResult.hasResolution()) {
                // Show a localized error dialog.
                GooglePlayServicesUtil.getErrorDialog(
                        mResult.getErrorCode(), mActivity, 0, null).show();
                return;
            }
            // If there is an existing resolution error being displayed or a resolution
            // activity has started before, do nothing and wait for resolution
            // progress to be completed.
            if (mIsInResolution) {
                return;
            }
            mIsInResolution = true;
            try {
                mResult.startResolutionForResult(mActivity, REQUEST_CODE_RESOLUTION);
            } catch (SendIntentException e) {
                Log.e(TAG, "Exception while starting resolution activity", e);
                retryConnecting();
            }
        }
    }

    public void startNavigation() {
        if (isAvailable()) {
            Location currentLocation = getCurrentLocation();
            if (currentLocation != null) {
                IntentManager.getGoogleMapsIntent(mActivity)
                        .subscribe(intent -> mActivity.startActivity(intent), throwable -> showGoogleMapsErrorDialog());
            } else showGpsOffDialog();
        } else onFailedConnection();
    }

    private void showGoogleMapsErrorDialog() {
        ErrorDialog dialog = new ErrorDialog();
        dialog.setMessage("Google maps is not installed, please install it");
        dialog.show(mActivity.getSupportFragmentManager(), "");
    }


    private void showGpsOffDialog() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setMesssage("Gps is not enabled. Do you want to change settings?");
        dialog.setOnPositiveListener(view -> mActivity.startActivity(IntentManager.getGpsSettingsIntent()));
        dialog.show(mActivity.getSupportFragmentManager(), "");
    }

}
