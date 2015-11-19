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


public class PlayServicesManager implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "PlayServicesManager";

    private AppCompatActivity mActivity;
    private ConnectionResult mResult;
    private boolean isConnected = false;

    protected static final int REQUEST_CODE_RESOLUTION = 1;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIsInResolution;

    public PlayServicesManager(Activity _activity) {
        mActivity = (AppCompatActivity) _activity;
    }

    public void onStart() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    public void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

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

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "GoogleApiClient connected");
        isConnected = true;
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "GoogleApiClient connection suspended");
        retryConnecting();
    }

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
                GooglePlayServicesUtil.getErrorDialog(
                        mResult.getErrorCode(), mActivity, 0, null).show();
                return;
            }
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
                        .subscribe(mActivity::startActivity, throwable -> showGoogleMapsErrorDialog());
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
        dialog.setMessage("Gps is not enabled. Do you want to change settings?");
        dialog.setOnPositiveListener(view -> mActivity.startActivity(IntentManager.getGpsSettingsIntent()));
        dialog.show(mActivity.getSupportFragmentManager(), "");
    }

}
