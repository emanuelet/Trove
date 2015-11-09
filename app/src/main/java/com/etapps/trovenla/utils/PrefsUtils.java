package com.etapps.trovenla.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Ian Ryan on 11/9/2015.
 */
public class PrefsUtils {
    public static void firstStart(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings.edit().putBoolean("firstStart", true).apply();
    }

    public static boolean isFirstStart(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean("firstStart", true);
    }
}
