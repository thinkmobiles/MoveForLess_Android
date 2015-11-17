package com.miami.moveforless.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.PhotoLayout.PhotoLayout;
import com.miami.moveforless.dialogs.PhotoDialog;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.utils.BitmapUtils;
import com.miami.moveforless.utils.Camera;
import com.miami.moveforless.utils.Gallery;
import com.miami.moveforless.utils.RxUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import rx.Subscription;

/**
 * Created by klim on 16.11.15.
 */
public class ClaimFragment extends BaseJobDetailFragment {
    @BindString(R.string.send_claim_successfull)
    String strSendSuccessfull;
    @Bind(R.id.photos_container_FF)
    PhotoLayout mPhotosContainer;
    @Bind(R.id.btnAdd_FC)
    Button btnAdd;
    @Bind(R.id.btnSend_FC)
    Button btnSend;
    @Bind(R.id.etClaim_FC)
    EditText etClaim;

    private Camera mCamera;
    private Gallery mGallery;
    private String mPhotoFilePath;
    private Subscription sendSubscription;
    private List<File> mPhotos;

    public static ClaimFragment newInstance() {
        Bundle args = new Bundle();
        ClaimFragment fragment = new ClaimFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean isAllowGoHome() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_claim;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {
        mCamera = new Camera(getActivity());
        mGallery = new Gallery(getActivity());
        mPhotos = new ArrayList<>();

        RxUtils.click(btnAdd, o -> btnAddPhoto());
        RxUtils.click(btnSend, o -> btnSendClicked());
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
                            mPhotosContainer.addImage(imageFile);
                            mPhotos.add(imageFile);
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
                            mPhotosContainer.addImage(imageFile1);
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


    private void btnAddPhoto() {
        PhotoDialog dialog = new PhotoDialog();
        dialog.setOnCameraListener(view -> mPhotoFilePath = mCamera.openCamera(this));
        dialog.setOnGalleryListener(view -> mGallery.openGallery(this));
        dialog.show(getActivity().getSupportFragmentManager(), "");
    }

    private void btnSendClicked() {
        showLoadingDialog();

        if (sendSubscription != null) removeSubscription(sendSubscription);
        sendSubscription = RestClient.getInstance().sendFeedback().subscribe(aBoolean -> onSendSuccess(), this::onSendError);
        addSubscription(sendSubscription);
    }

    private void onSendSuccess() {
        hideLoadingDialog();

        showInfoDialog(strSendSuccessfull, view -> {
            clearAll();
            getParentFragment().onActivityResult(Const.CLAIM_REQUEST_ID, Activity.RESULT_OK, null);
            getActivity().onBackPressed();
        });
    }

    private void onSendError(Throwable _throwable) {
        hideLoadingDialog();
        showErrorDialog(ErrorParser.parse(_throwable));
    }

    private void clearAll() {
        mPhotosContainer.removeAllViews();
        etClaim.setText("");
        for (File file : mPhotos) {

            if (file.exists()) {
                file.delete();
                mCamera.deleteImage(getActivity(), file);
            }
        }
        mPhotos.clear();
    }

}
