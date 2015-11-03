package com.miami.moveforless.managers;


import com.miami.moveforless.App;
import com.miami.moveforless.utils.SimpleDiskCache;

import java.io.IOException;

/**
 * Created by klim on 03.11.15.
 */
public class CacheManager {
    private static CacheManager mInstance;
    private static SimpleDiskCache mDiskLruCache;

    public static CacheManager getInstance() {
        if (mInstance == null) {
            mInstance = new CacheManager();
        }
        return mInstance;
    }

    private CacheManager() {
        try {
            mDiskLruCache = SimpleDiskCache.open(App.getAppContext().getCacheDir(), 1, (long) (20 * Math.pow(2, 20)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void put(long key, String object) {
        try {
            mDiskLruCache.put(String.valueOf(key), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(long key) {
        try {
            SimpleDiskCache.StringEntry entry = mDiskLruCache.getString(String.valueOf(key));
            if (entry == null) return null;
            return entry.getString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
