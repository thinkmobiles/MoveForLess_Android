package com.miami.moveforless.rest;

import com.miami.moveforless.globalconstants.RestConst;
import com.miami.moveforless.rest.request.LoginRequest;
import com.miami.moveforless.rest.response.LoginResponse;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by SetKrul on 28.07.2015.
 */
public class RestClientApi {

    private IMoverApi api;
    private static RestClientApi mInstance;

    private RestClientApi() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(new OkHttpClient()))
                .setEndpoint(RestConst.END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        api = restAdapter.create(IMoverApi.class);
    }

    public static RestClientApi getInstance() {
        if (mInstance == null) {
            mInstance = new RestClientApi();
        }
        return mInstance;
    }

    public static IMoverApi getApi() {
        return getInstance().api;
    }

    public static rx.Observable<String> login(String _username, String _password) {
        final LoginRequest loginRequest = new LoginRequest(_username, _password);
        return RestClientApi.getApi().login(loginRequest.username, loginRequest.password)
                .subscribeOn(Schedulers.io())
                .retry(2)
                .timeout(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(loginResponse -> loginResponse.getToken());
    }

}
