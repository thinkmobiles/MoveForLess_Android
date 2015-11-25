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

    private void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void saveInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private String retrieveString(String _s) {
        return sharedPreferences.getString(_s, "");
    }

    private boolean retrieveBoolean(String _s) {
        return sharedPreferences.getBoolean(_s, false);
    }

    public void storeUsername(String _username) {
        saveString(SharedPrefConst.SHARED_PREF_USER_NAME, _username);
    }

    public String retrieveUsername() {
        return retrieveString(SharedPrefConst.SHARED_PREF_USER_NAME);
    }

    public void storeToken(String _token) {
        saveString(SharedPrefConst.SHARED_PREF_USER_TOKEN, _token);
    }

    public String retrieveToken() {
        return retrieveString(SharedPrefConst.SHARED_PREF_USER_TOKEN);
    }

    public void storeNotificationState(boolean _value) {
        saveBoolean(SharedPrefConst.SHARED_PREF_NOTIFICATION_STATE, _value);
    }

    public boolean retrieveNotificationState() {
        return retrieveBoolean(SharedPrefConst.SHARED_PREF_NOTIFICATION_STATE);
    }
}
