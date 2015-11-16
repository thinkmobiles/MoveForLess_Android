package com.miami.moveforless;

import android.app.Application;
import android.content.Context;

import com.miami.moveforless.managers.SharedPrefManager;
import com.miami.moveforless.utils.TypefaceManager;
import com.raizlabs.android.dbflow.config.FlowManager;

//import android.support.multidex.MultiDex;

/**
 * Created by klim on 21.10.15.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
//        MultiDex.install(this);
        super.onCreate();
        mContext = getApplicationContext();
        TypefaceManager.init(this);
        SharedPrefManager.getInstance();
//        FlowManager.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
//        FlowManager.destroy();
    }

    public static Context getAppContext() {
        return mContext;
    }

}
