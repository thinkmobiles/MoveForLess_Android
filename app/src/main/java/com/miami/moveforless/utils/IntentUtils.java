package com.miami.moveforless.utils;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.miami.moveforless.globalconstants.Const;

/**
 * Created by SetKrul on 12.11.2015.
 */
public class IntentUtils {

    public static Intent getGalleryStartIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryIntent.setType(Const.IMAGE_MIME_TYPE);
        galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        return galleryIntent;
    }

    public static Intent getCameraStartIntent(final Uri _fileUri) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, _fileUri);
        return takePictureIntent;
    }
}
