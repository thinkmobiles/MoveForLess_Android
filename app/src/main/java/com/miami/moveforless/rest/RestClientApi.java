package com.miami.moveforless.rest;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.miami.moveforless.errors.RouteException;
import com.miami.moveforless.globalconstants.RestConst;
import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.rest.request.LoginRequest;
import com.miami.moveforless.rest.response.LogoutResponse;
import com.miami.moveforless.rest.response.RouteInfo;
import com.miami.moveforless.rest.response.RouteResponse;
import com.miami.moveforless.utils.RouteUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Route;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class RestClientApi {

    private IMoverApi mMoverApi;
    private RouteApi mRouteApi;
    private static RestClientApi mInstance;

    private RestClientApi() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(new OkHttpClient()))
                .setEndpoint(RestConst.IMOVER_END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(request -> request.addHeader("Content-type", "application/json; charset=UTF-8"))
                .build();

        mMoverApi = restAdapter.create(IMoverApi.class);

        RestAdapter googleDirectionRestAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(new OkHttpClient()))
                .setEndpoint(RestConst.GOOGLE_DIRECTION_END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(request -> request.addHeader("Content-type", "application/json; charset=UTF-8"))
                .build();

        mRouteApi = googleDirectionRestAdapter.create(RouteApi.class);
    }

    public static RestClientApi getInstance() {
        if (mInstance == null) {
            mInstance = new RestClientApi();
        }
        return mInstance;
    }

    private IMoverApi getIMoverApi() {
        return getInstance().mMoverApi;
    }

    private RouteApi getRouteApi() {
        return getInstance().mRouteApi;
    }


    public Observable<String> login(String _username, String _password) {
        final LoginRequest loginRequest = new LoginRequest(_username, _password);
        return getInstance().getIMoverApi().login(loginRequest)
                .subscribeOn(Schedulers.io())
                .retry(2)
                .timeout(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(loginResponse -> loginResponse.getToken());
    }

    public Observable<LogoutResponse> logout() {
        final String username = SharedPrefManager.getInstance().retriveUsername();
        final String token = SharedPrefManager.getInstance().retrieveToken();
        return getInstance().getIMoverApi().logout(username, token)
                .subscribeOn(Schedulers.io())
                .retry(2)
                .timeout(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<RouteInfo> getRoute(Location _startLocation, String _endLocation) {
        String str_origin = _startLocation.getLatitude() + "," + _startLocation.getLongitude();

        return getInstance().getRouteApi().getRoute(str_origin, _endLocation, "imperial", "driving")
                .subscribeOn(Schedulers.io())
                .retry(2)
                .timeout(30, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(routeResponse -> RouteUtils.parseRouteResponse(routeResponse))
                .flatMap(routeInfo -> Observable.create(subscriber -> {
                    if (routeInfo == null) subscriber.onError(new RouteException());
                    else subscriber.onNext(routeInfo);
                }));

    }


}
