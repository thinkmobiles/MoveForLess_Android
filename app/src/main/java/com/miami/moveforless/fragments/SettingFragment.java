package com.miami.moveforless.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.miami.moveforless.R;

import butterknife.Bind;

/**
 * Created by Sergbek on 20.11.2015.
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.edOldPassword_FS)
    EditText edOldPassword;

    @Bind(R.id.edNewPassword_FS)
    EditText edNewPassword;

    @Bind(R.id.edConfirmPassword_FS)
    EditText edConfirmPassword;

    @Bind(R.id.btnChange_FS)
    Button btnChange;

    @Bind(R.id.cbEnableNotification_FS)
    CheckBox cbEnableNotification;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {
        setHasOptionsMenu(true);
        btnChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO: implement change account api
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popBackStack();
    }
}
