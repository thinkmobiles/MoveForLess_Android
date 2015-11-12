package com.miami.moveforless.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.dialogs.PhotoDialog;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.utils.BitmapUtils;
import com.miami.moveforless.utils.Camera;
import com.miami.moveforless.utils.Gallery;

import java.io.File;

/**
 * Created by SetKrul on 12.11.2015.
 */
public class CameraWorks extends BaseJobDetailFragment {

    private String mPhotoFilePath;
    private Camera mCamera;
    private Gallery mGallery;


    public static CameraWorks newInstance() {
        return new CameraWorks();
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected void setupViews() {

    }

    @Override
    public void onViewCreated(final View _view, final Bundle _savedInstanceState) {
        super.onViewCreated(_view, _savedInstanceState);
        mCamera = new Camera(getActivity());
        mGallery = new Gallery(getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mPhotoFilePath", mPhotoFilePath);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mPhotoFilePath = savedInstanceState.getString("mPhotoFilePath");
        }
    }

    public void Click(){
        PhotoDialog dialog = new PhotoDialog();
        dialog.setOnCameraListener(view -> mPhotoFilePath = mCamera.openCamera(this));
        dialog.setOnGalleryListener(view -> mGallery.openGallery(this));
        dialog.show(getActivity().getSupportFragmentManager(), "");
    }


    @Override
    public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);
        switch (_requestCode) {
            case Const.REQUEST_PHOTO:
                if (!TextUtils.isEmpty(mPhotoFilePath)) {
                    File imageFile = new File(mPhotoFilePath);
                    if (_resultCode == Activity.RESULT_OK) {
                        if (imageFile.exists()) {
                            mGallery.addPhotoToGallery(mPhotoFilePath);
//                            try {
//                                ivPhoto.setImageBitmap(BitmapUtils.handleSamplingAndRotationBitmap(getActivity(), Uri
//                                        .fromFile(imageFile), 400, 400));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                        } else {
                            Toast.makeText(getActivity(), this.getResources().getString(R.string.file_is_not_found),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (_resultCode == Activity.RESULT_CANCELED) {
                        //noinspection ResultOfMethodCallIgnored
                        imageFile.delete();
                    }
                }
                break;

            case Const.REQUEST_GALLERY_IMAGE:
                if (_resultCode == Activity.RESULT_OK) {
                    mPhotoFilePath = BitmapUtils.getPath(getActivity(), _data.getData());
                    if (!TextUtils.isEmpty(mPhotoFilePath)) {
                        File imageFile1 = new File(mPhotoFilePath);
                        if (imageFile1.exists()) {
//                            ivPhoto.setImageBitmap(BitmapUtils.compressImage(imageFile1, 300));
                        } else {
                            Toast.makeText(getActivity(), this.getResources().getString(R.string.file_is_not_found),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), this.getResources().getString(R.string.uncompilable_type),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    @Override
    protected boolean isAllowGoHome() {
        return false;
    }
}
