package com.miami.moveforless.globalconstants;

import com.miami.moveforless.rest.request.LoginRequest;
import com.miami.moveforless.rest.response.LoginResponse;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by klim on 20.10.15.
 */
public interface IMoverApi {

    @POST("/user/login")
    LoginResponse login(@Body LoginRequest _loginRequest);
}
