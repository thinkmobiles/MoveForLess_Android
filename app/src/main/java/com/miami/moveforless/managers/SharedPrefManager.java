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

    public String retrieveString(String _s) {
        return sharedPreferences.getString(_s, "");
    }

    public String retrieveToken() {
        return retrieveString(SharedPrefConst.SHARED_PREF_USER_TOKEN);
    }

    public void storeToken(String _token) {
        saveString(SharedPrefConst.SHARED_PREF_USER_TOKEN, _token);
    }

    public void storeUsername(String _username) {
        saveString(SharedPrefConst.SHARED_PREF_USER_NAME, _username);
    }

    public String retriveUsername() {
        return retrieveString(SharedPrefConst.SHARED_PREF_USER_NAME);
    }

    public void storeUserPassword(String _username) {
        saveString(SharedPrefConst.SHARED_PREF_USER_PASSWORD, _username);
    }

    public String retriveUserPassword() {
        return retrieveString(SharedPrefConst.SHARED_PREF_USER_PASSWORD);
    }

}
