package com.miami.moveforless.rest;

import com.miami.moveforless.rest.response.LoginResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by klim on 20.10.15.
 */
public interface IMoverApi {

    @POST("/user/login")
    @FormUrlEncoded
    Observable<LoginResponse> login(@Field("username") String user, @Field(("password")) String password);
}
