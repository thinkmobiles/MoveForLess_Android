package com.miami.moveforless.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.managers.IntentManager;

import java.io.File;

/**
 * Created by SetKrul on 12.11.2015.
 */
public class GalleryUtils {

    Activity mActivity;

    public GalleryUtils(Activity _activity){
        this.mActivity = _activity;
    }

    public ComponentName isCanGetGalleryPicture() {
        Intent galleryIntent = IntentManager.getGalleryStartIntent();
        return galleryIntent.resolveActivity(mActivity.getPackageManager());
    }

    public void openGallery(Fragment _fragment) {
        if (isCanGetGalleryPicture() != null) {
            Intent galleryIntent = IntentManager.getGalleryStartIntent();
            if (galleryIntent.resolveActivity(mActivity.getPackageManager()) != null) {
                _fragment.getParentFragment().startActivityForResult(galleryIntent, Const.REQUEST_GALLERY_IMAGE);
            }
        }
    }

    public void openGallery(Activity _activity) {
        if (isCanGetGalleryPicture() != null) {
            Intent galleryIntent = IntentManager.getGalleryStartIntent();
            if (galleryIntent.resolveActivity(mActivity.getPackageManager()) != null) {
                _activity.startActivityForResult(galleryIntent, Const.REQUEST_GALLERY_IMAGE);
            }
        }
    }


    public void addPhotoToGallery(String _mPhotoFilePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(_mPhotoFilePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        mActivity.sendBroadcast(mediaScanIntent);
    }
}
