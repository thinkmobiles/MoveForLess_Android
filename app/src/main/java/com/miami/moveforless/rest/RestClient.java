package com.miami.moveforless.rest;

import com.miami.moveforless.App;
import com.miami.moveforless.R;
import com.miami.moveforless.errors.RouteException;
import com.miami.moveforless.globalconstants.RestConst;
import com.miami.moveforless.managers.CacheManager;
import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.rest.request.LoginRequest;
import com.miami.moveforless.rest.response.LogoutResponse;
import com.miami.moveforless.rest.response.RouteInfo;
import com.miami.moveforless.utils.RouteUtils;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class RestClient {
    private static final String TAG = "RestClient";
    private static final String API_KEY = App.getAppContext().getString(R.string.google_maps_key);

    private IMoverApi mMoverApi;
    private RouteApi mRouteApi;
    private static RestClient mInstance;
    private CacheManager mCacheManager;

    private RestClient() {

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
                .build();

        mRouteApi = googleDirectionRestAdapter.create(RouteApi.class);
        mCacheManager = CacheManager.getInstance();
    }

    public static RestClient getInstance() {
        if (mInstance == null) {
            mInstance = new RestClient();
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

    public Observable<RouteInfo> getRoute(String _startLocation, String _endLocation) {
        final long key = _startLocation.concat(_endLocation).hashCode();
        try {
            RouteInfo cachedResponse = RouteInfo.deserialize(mCacheManager.get(key));
            if (cachedResponse != null)
                return Observable.just(cachedResponse);
        } catch (NullPointerException _e) {

        }
        return getInstance().getRouteApi().getRoute(_startLocation, _endLocation, "imperial", "driving", API_KEY)
                .subscribeOn(Schedulers.io())
                .retry(2)
                .timeout(20, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(routeResponse -> RouteUtils.parseRouteResponse(routeResponse))
                .doOnNext(routeInfo1 -> {
                    if (routeInfo1 != null) mCacheManager.put(key, routeInfo1.serialize());
                })
                .flatMap(routeInfo -> Observable.create(subscriber -> {
                    if (routeInfo == null)
                        subscriber.onError(new RouteException());
                    else {
                        subscriber.onNext(routeInfo);
                    }
                }));

    }

}
