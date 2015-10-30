package com.miami.moveforless;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.utils.TypefaceManager;

/**
 * Created by klim on 21.10.15.
 */
public class App extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        mContext = getApplicationContext();
        TypefaceManager.init(this);
        SharedPrefManager.getInstance();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return mContext;
    }
}
