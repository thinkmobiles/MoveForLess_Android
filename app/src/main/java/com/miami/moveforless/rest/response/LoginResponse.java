package com.miami.moveforless.rest.response;

/**
 * Created by klim on 20.10.15.
 */
public class LoginResponse {

    public Integer user_id;
    public String token;
    public Long timeout;

    public LoginResponse() {
    }

    public String getToken() {
        return token;
    }

    public Integer getUserId() {
        return user_id;
    }

}
