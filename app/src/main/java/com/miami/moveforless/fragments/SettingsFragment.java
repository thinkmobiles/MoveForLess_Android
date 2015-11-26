package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.miami.moveforless.R;
import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.rest.ErrorParser;
import com.miami.moveforless.rest.RestClient;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.BindString;
import rx.Subscription;

/**
 * settings screen
 * Created by Sergbek on 20.11.2015.
 */
public class SettingsFragment extends BaseFragment {
    @BindString(R.string.edit_user_error)           String strEditError;
    @BindString(R.string.edit_user_success)         String strEditSuccess;
    @Bind(R.id.etOldPassword_FS)                    EditText etOldPassword;
    @Bind(R.id.etNewPassword_FS)                    EditText etNewPassword;
    @Bind(R.id.etConfirmPassword_FS)                EditText etConfirmPassword;
    @Bind(R.id.btnChange_FS)                        Button btnChange;
    @Bind(R.id.cbEnableNotification_FS)             CheckBox cbEnableNotification;

    private Subscription loginEditSubscription;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {
        setHasOptionsMenu(true);

        RxUtils.click(btnChange, o -> btnEditClicked());

        cbEnableNotification.setChecked(SharedPrefManager.getInstance().retrieveNotificationState());
        cbEnableNotification.setOnCheckedChangeListener((compoundButton, b) -> onNotificationChangeClicked(b));
    }

    public void btnEditClicked() {
        showLoadingDialog();

        if (loginEditSubscription != null) removeSubscription(loginEditSubscription);
        final String password = etOldPassword.getText().toString();
        final String newPassword = etNewPassword.getText().toString();
        final String repeatPassword = etConfirmPassword.getText().toString();

        loginEditSubscription = RestClient.getInstance().UserEdit(password, newPassword, repeatPassword)
                .subscribe(this::onEditSuccess, this::onEditError);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popBackStack();
    }

    private void onEditSuccess(boolean _isSuccess) {
        hideLoadingDialog();
        if (_isSuccess)
            showInfoDialog(strEditSuccess);
        else
            showErrorDialog(strEditError);
    }

    private void onEditError(Throwable _throwable) {
        hideLoadingDialog();
        showErrorDialog(ErrorParser.parse(_throwable));
    }

    private void onNotificationChangeClicked(boolean _isChecked) {
        SharedPrefManager.getInstance().storeNotificationState(_isChecked);
        if (_isChecked) {
            // TODO: add starting notification service
        }
    }
}
