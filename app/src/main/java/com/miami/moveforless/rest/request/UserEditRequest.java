package com.miami.moveforless.rest.request;

/**
 * Created by klim on 24.11.15.
 */
public class UserEditRequest {

    public String key;
    public String token;
    public String password;
    public String password_new;
    public String password_repeat;

    public UserEditRequest(String _key, String _token, String _password, String _newPassword, String _repeatPassword) {
        key = _key;
        token = _token;
        password = _password;
        password_new = _newPassword;
        password_repeat = _repeatPassword;
    }

}
