package com.miami.moveforless.rest.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by klim on 20.10.15.
 */
public class LoginResponse {
    @SerializedName("user_id")
    public Integer user_id;

    @SerializedName("token")
    public String token;

    @SerializedName("timeout")
    public Long timeout;

    public LoginResponse() {
    }
}
