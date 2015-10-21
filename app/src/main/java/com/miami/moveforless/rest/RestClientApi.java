package com.miami.moveforless.rest;

import com.miami.moveforless.globalconstants.IMoverApi;
import com.miami.moveforless.globalconstants.RestConst;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

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
}
