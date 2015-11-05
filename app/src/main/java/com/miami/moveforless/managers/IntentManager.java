package com.miami.moveforless.managers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.miami.moveforless.Exceptions.GoogleMapsException;

import rx.Observable;

/**
 * Created by klim on 05.11.15.
 */
public class IntentManager {

    public static Observable<Intent> getGoogleMapsIntent(Activity _activity) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=Seattle, Lakewood");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        return Observable.create(subscriber -> {
            if (mapIntent.resolveActivity(_activity.getPackageManager()) != null) {
                subscriber.onNext(mapIntent);
            } else {
                subscriber.onError(new GoogleMapsException());
            }
        });
    }

    public static Intent getGoogleMapsOnPlayStore() {
        Intent intent  = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.maps"));
        return intent;
    }

    public static Intent getGpsSettingsIntent() {
        return new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    }
}
