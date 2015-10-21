package com.miami.moveforless;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.v4.content.Loader;

import com.miami.moveforless.globalconstants.IMoverApi;
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
        restApi = RestClientApi.getApi();
    }

    @Override
    public Object loadInBackground() {

        LoginResponse resp;
        try {
            resp = restApi.login(new LoginRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
