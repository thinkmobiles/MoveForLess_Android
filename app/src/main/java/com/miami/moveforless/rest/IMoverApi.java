package com.miami.moveforless.rest;

import com.miami.moveforless.rest.request.JobRequest;
import com.miami.moveforless.rest.request.LoginRequest;
import com.miami.moveforless.rest.response.JobResponse;
import com.miami.moveforless.rest.response.ListMoveSizeResponse;
import com.miami.moveforless.rest.response.ListNumberMenResponse;
import com.miami.moveforless.rest.response.LoginResponse;
import com.miami.moveforless.rest.response.LogoutResponse;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by klim on 20.10.15.
 */
public interface IMoverApi {

    @POST("/user/login")
    Observable<LoginResponse> login(@Body LoginRequest _loginRequest);

    @GET("/user/logout")
    Observable<LogoutResponse> logout(@Query("key") String _key, @Query("token") String _token);

    @POST("/job/list")
    Observable<List<JobResponse>> jobList(@Body JobRequest _jobRequest);

    @GET("/job/move_size")
    Observable<ListMoveSizeResponse> moveSizeList(@Query("company_id") String _companyId,
                                                  @Query("resource_id") String _resourceId,
                                                  @Query("key") String _key,
                                                  @Query("token") String _token);

    @GET("/job/number_men")
    Observable<ListNumberMenResponse> numberMenList(@Query("rank_id") String rankId,
                                                    @Query("key") String key,
                                                    @Query("token") String token);
}
