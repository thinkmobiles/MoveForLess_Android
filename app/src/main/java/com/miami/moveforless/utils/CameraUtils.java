package com.miami.moveforless.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.globalconstants.Const;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SetKrul on 12.11.2015.
 */
public class CameraUtils {

    Activity mActivity;
    private static final String PHOTO_FILE_EXTENSION = ".jpg";

    public CameraUtils(Activity _activity){
        this.mActivity = _activity;
    }

    public boolean isCanGetCameraPicture() {
        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager pm = mActivity.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) && takePictureIntent.resolveActivity(pm) != null;
    }

    public String openCamera(Fragment _fragment) {
        String mPhotoFilePath = null;
        if (isCanGetCameraPicture()) {
            final File imageFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            boolean isFolderExist = imageFolder.exists() || imageFolder.mkdir();
            if (isFolderExist) {
                @SuppressLint("SimpleDateFormat") String imageFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                File imageFile;
                try {
                    imageFile = File.createTempFile(imageFileName, PHOTO_FILE_EXTENSION, imageFolder);
                } catch (IOException e) {
                    imageFile = null;
                }
                if (imageFile != null) {
                    mPhotoFilePath = imageFile.getPath();
                    Intent takePictureIntent = IntentUtils.getCameraStartIntent(Uri.fromFile(imageFile));
                    _fragment.getParentFragment().startActivityForResult(takePictureIntent, Const.REQUEST_PHOTO);
                }

            } else {
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.cant_create_photo), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mActivity, mActivity.getResources().getString(R.string.not_found_camera), Toast.LENGTH_SHORT).show();
        }
        return mPhotoFilePath;
    }

    public void deleteImage(Activity _activity, File _file) {
        try {

            _activity.getContentResolver() .delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    MediaStore.Images.Media.DATA
                            + "='"
                            + _file.getPath()
                            + "'", null);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
