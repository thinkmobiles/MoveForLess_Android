package com.miami.moveforless.rest;

import com.miami.moveforless.rest.response.RouteInfo;
import com.miami.moveforless.rest.response.RouteResponse;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by klim on 17.09.15.
 */
public interface RouteApi {

    @GET("/maps/api/directions/json")
    Observable<RouteResponse> getRoute(
            @Query("origin") String position,
            @Query("destination") String destination,
            @Query("units") String units,
            @Query("mode") String mode,
            @Query("key") String key
    );
}
