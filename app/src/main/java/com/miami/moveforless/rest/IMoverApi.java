package com.miami.moveforless.rest;

import com.miami.moveforless.rest.request.LoginRequest;
import com.miami.moveforless.rest.response.LoginResponse;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by klim on 20.10.15.
 */
public interface IMoverApi {

    @POST("/user/login")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/user/logout")
    Observable<Boolean> logout(@Query("key") String _key, @Query("token") String _token);
}
