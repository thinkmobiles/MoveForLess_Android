package com.miami.moveforless.managers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;

import com.miami.moveforless.Exceptions.GoogleMapsException;
import com.miami.moveforless.globalconstants.Const;

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

    public static Intent getGalleryStartIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryIntent.setType(Const.IMAGE_MIME_TYPE);
        galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        return galleryIntent;
    }

    public static Intent getCameraStartIntent(Uri _fileUri) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, _fileUri);
        return takePictureIntent;
    }
}
