package com.miami.moveforless.location;

import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.miami.moveforless.errors.LocationException;
import com.miami.moveforless.globalconstants.RouteConst;

import rx.Observable;
import rx.exceptions.OnErrorFailedException;

/**
 * Created by klim on 02.09.15.
 */
public final class TrackingService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Activity mActivity;

    private boolean mIsInResolution;
    private Location mLastLocation;
    private OnConnectedListener mListener;


    public TrackingService(Activity _activity) {
        mActivity = _activity;

        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public final void connect() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();

    }

    public final void disconnect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public void setOnConnectedListener(OnConnectedListener _listener) {
        mListener = _listener;
    }

    public final void retryConnecting() {
        mIsInResolution = false;
        if (!mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }
    }

    public Observable<Location> getLocationObservable() {
        return Observable.create(subscriber -> {
            if (mLastLocation == null) {subscriber.onError(new LocationException());
            } else subscriber.onNext(mLastLocation);
        });
    }

    @Override
    public final void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mListener != null) mListener.onConnected();
    }

    @Override
    public final void onConnectionSuspended(int i) {
        retryConnecting();
    }

    @Override
    public final void onConnectionFailed(ConnectionResult _result) {
        if (!_result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(
                    _result.getErrorCode(), mActivity, 0, dialog -> retryConnecting()).show();
            return;
        }
        if (mIsInResolution) {
            return;
        }
        mIsInResolution = true;
        try {
            _result.startResolutionForResult(mActivity, RouteConst.REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e) {
            retryConnecting();
        }

    }

    public interface OnConnectedListener{
        void onConnected();
    }

}
