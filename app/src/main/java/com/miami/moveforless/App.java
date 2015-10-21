package com.miami.moveforless;

import android.app.Application;
import android.content.Context;

import com.miami.moveforless.managers.SharedPrefManager;

/**
 * Created by klim on 21.10.15.
 */
public class App extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        SharedPrefManager.getInstance();
    }

    public static Context getAppContext() {
        return mContext;
    }
}
