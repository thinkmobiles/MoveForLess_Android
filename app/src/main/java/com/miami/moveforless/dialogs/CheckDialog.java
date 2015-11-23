package com.miami.moveforless.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.miami.moveforless.R;
import com.miami.moveforless.activity.BaseActivity;
import com.miami.moveforless.customviews.PhotoLayout.CheckLayout;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.utils.BitmapUtils;
import com.miami.moveforless.utils.CameraUtils;
import com.miami.moveforless.utils.GalleryUtils;
import com.miami.moveforless.utils.RxUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by klim on 20.11.15.
 */
public class CheckDialog extends BaseActivity {
    @BindString(R.string.check_error) String strCheckError;

    @Bind(R.id.photos_container_DCL)
    CheckLayout mPhotoContainer;
    @Bind(R.id.btnAdd_DCL)
    Button btnAdd;
    @Bind(R.id.btnPay_DCL)
    Button btnPay;
    @Bind(R.id.content_container_DCL)
    LinearLayout mContentContainer;

    private CameraUtils mCamera;
    private GalleryUtils mGallery;
    private String mPhotoFilePath = "";
    private float mAmount;
    private int mPaymentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_layout);

        mAmount = getIntent().getFloatExtra(Const.AMOUNT_KEY, -1);
        mPaymentId = getIntent().getIntExtra(Const.PAYMENT_ID_KEY, -1);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.height = (int) (size.y * 0.75);
        params.width = (int) (size.x * 0.75);

        this.getWindow().setAttributes(params);
        setFinishOnTouchOutside(true);

        mCamera = new CameraUtils(this);
        mGallery = new GalleryUtils(this);

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
                        } else {
                            Toast.makeText(this, this.getResources().getString(R.string.file_is_not_found),
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
                    mPhotoFilePath = BitmapUtils.getPath(this, _data.getData());
                    if (!TextUtils.isEmpty(mPhotoFilePath)) {
                        File imageFile1 = new File(mPhotoFilePath);
                        if (imageFile1.exists()) {
                            mPhotoContainer.addImage(imageFile1);
                        } else {
                            Toast.makeText(this, this.getResources().getString(R.string.file_is_not_found),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, this.getResources().getString(R.string.uncompilable_type),
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
        dialog.show(this.getSupportFragmentManager(), "");
    }

    private void pay() {
        if (!mPhotoFilePath.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra(Const.PAYMENT_ID_KEY, mPaymentId);
            intent.putExtra(Const.AMOUNT_KEY, mAmount);
            intent.putExtra(Const.CHECK_PHOTO_KEY, mPhotoFilePath);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            showErrorDialog(strCheckError);
        }
    }
}
