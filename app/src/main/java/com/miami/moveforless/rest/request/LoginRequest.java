package com.miami.moveforless.rest.request;

/**
 * Created by klim on 20.10.15.
 */
public class LoginRequest {
    public String username;
    public String password;

    public LoginRequest(String _userName, String _password) {
        username = _userName;
        password = _password;
    }
}
