package com.miami.moveforless.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.PhotoLayout.CheckLayout;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.utils.BitmapUtils;
import com.miami.moveforless.utils.CameraUtils;
import com.miami.moveforless.utils.GalleryUtils;
import com.miami.moveforless.utils.RxUtils;

import java.io.File;
import java.util.Arrays;

import butterknife.Bind;

/**
 * Created by klim on 20.11.15.
 */
public class CheckDialog extends BaseDialog {


    @Bind(R.id.photos_container_DCL)
    CheckLayout mPhotoContainer;
    @Bind(R.id.btnAdd_DCL)
    Button btnAdd;
    @Bind(R.id.btnPay_DCL)
    Button btnPay;

    private CameraUtils mCamera;
    private GalleryUtils mGallery;
    private String mPhotoFilePath;


    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_check_layout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) {
        View view = super.onCreateView(_inflater, _container, _savedInstanceState);
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.TOP|Gravity.LEFT);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);

        return view;
    }

    @Override
    protected void setupViews() {
        setCancelable(true);

        mCamera = new CameraUtils(getActivity());
        mGallery = new GalleryUtils(getActivity());

        RxUtils.click(btnAdd, o -> addPhoto());
        RxUtils.click(btnPay, o -> pay());
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
                            mPhotoContainer.addImage(imageFile);
                            mPhotoFilePath = "";
                        } else {
                            Toast.makeText(getActivity(), this.getResources().getString(R.string.file_is_not_found),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (_resultCode == Activity.RESULT_CANCELED) {
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
                            mPhotoContainer.addImage(imageFile1);
                            mPhotoFilePath = "";
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

    private void addPhoto() {
        PhotoDialog dialog = new PhotoDialog();
        dialog.setOnCameraListener(view -> mPhotoFilePath = mCamera.openCamera(this));
        dialog.setOnGalleryListener(view -> mGallery.openGallery(this));
        dialog.show(getActivity().getSupportFragmentManager(), "");
    }

    private void pay() {
        dismiss();
    }
}
