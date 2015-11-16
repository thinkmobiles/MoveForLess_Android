package com.miami.moveforless.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.HelveticaEditText;
import com.miami.moveforless.customviews.PhotoPlaceholder;
import com.miami.moveforless.dialogs.PhotoDialog;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.utils.BitmapUtils;
import com.miami.moveforless.utils.Camera;
import com.miami.moveforless.utils.Gallery;
import com.miami.moveforless.utils.RxUtils;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.BindString;
import rx.Subscription;

/**
 * Created by klim on 12.11.15.
 */
public class FeedbackFragment extends BaseJobDetailFragment {
    @BindString(R.string.send_successfull)      String strSendSuccessfull;
    @Bind(R.id.btnSend_FF)                                  Button btnSend;
    @Bind(R.id.btnAdd_FF)                                   Button btnAdd;
    @Bind(R.id.photos_container_FF)                         GridLayout mPhotosContainer;
    @Bind(R.id.etFeedBack_FF)                               HelveticaEditText etFeedback;

    private Subscription sendSubscription;
    private String mPhotoFilePath;
    private Camera mCamera;
    private Gallery mGallery;


    public static FeedbackFragment newInstance() {

        Bundle args = new Bundle();

        FeedbackFragment fragment = new FeedbackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {
        etFeedback.setUnderlineColor(android.R.color.transparent);
        mCamera = new Camera(getActivity());
        mGallery = new Gallery(getActivity());

        RxUtils.click(btnSend, o -> btnSendClicked());
        RxUtils.click(btnAdd, o -> btnAddClicked());
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

    private void btnSendClicked() {

        showLoadingDialog();

        if (sendSubscription != null) removeSubscription(sendSubscription);
        sendSubscription = RestClient.getInstance().sendFeedback().subscribe(aBoolean -> onSendSuccess(), this::onSendError);
        addSubscription(sendSubscription);
    }

    private void btnAddClicked() {
        PhotoDialog dialog = new PhotoDialog();
        dialog.setOnCameraListener(view -> mCamera.openCamera(this));
        dialog.setOnGalleryListener(view -> mGallery.openGallery(this));
        dialog.show(getActivity().getSupportFragmentManager(), "");
    }

    private void onSendSuccess() {
        hideLoadingDialog();
        showInfoDialog(strSendSuccessfull, view -> {
            getParentFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
            getActivity().onBackPressed();
        });
    }

    private void onSendError(Throwable _throwable) {
        hideLoadingDialog();
        showErrorDialog(ErrorParser.parse(_throwable));
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
                            try {
                                PhotoPlaceholder placeholder = new PhotoPlaceholder(getContext());
                                placeholder.setPhoto(BitmapUtils.handleSamplingAndRotationBitmap(getActivity(), Uri
                                        .fromFile(imageFile), 400, 400));
                                mPhotosContainer.addView(placeholder);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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
                            PhotoPlaceholder placeholder = new PhotoPlaceholder(getContext());
                            placeholder.setPhoto(BitmapUtils.compressImage(imageFile1, 300));
                            mPhotosContainer.addView(placeholder);
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
