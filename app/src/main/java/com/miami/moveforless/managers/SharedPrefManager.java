package com.miami.moveforless.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.miami.moveforless.App;
import com.miami.moveforless.globalconstants.SharedPrefConst;

/**
 * Created by klim on 21.10.15.
 */
public class SharedPrefManager {

    private static SharedPrefManager instance;
    private SharedPreferences sharedPreferences;

    private SharedPrefManager(Context _context) {
        sharedPreferences = _context.getSharedPreferences(SharedPrefConst.SHARED_PREF_NAME, 0);

    }

    public static SharedPrefManager getInstance() {
        if (instance == null) {
            instance = new SharedPrefManager(App.getAppContext());
        }
        return instance;
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

}
