package com.miami.moveforless;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.miami.moveforless.rest.IMoverApi;
import com.miami.moveforless.rest.RestClientApi;
import com.miami.moveforless.rest.request.LoginRequest;
import com.miami.moveforless.rest.response.LoginResponse;

/**
 * Created by klim on 20.10.15.
 */
public class TestLoader extends AsyncTaskLoader{
    IMoverApi restApi;

    public TestLoader(Context context) {
        super(context);

    }

    @Override
    public Object loadInBackground() {
//        restApi = RestClientApi.getApi();
//        LoginResponse resp = null;
//        LoginRequest request  = new LoginRequest();
//        try {
//            resp = restApi.login(request.username, request.password);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return resp;
        return null;
    }
}
