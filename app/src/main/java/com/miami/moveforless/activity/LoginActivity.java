package com.miami.moveforless.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.miami.moveforless.R;

import butterknife.Bind;

/**
 * Created by klim on 20.10.15.
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.et_email_AL)         EditText etEmail;

    @Bind(R.id.et_password_AL)      EditText etPassword;

    @Bind(R.id.btn_login_AL)        Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

}
