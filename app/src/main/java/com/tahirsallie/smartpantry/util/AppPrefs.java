package com.tahirsallie.smartpantry.util;

import android.content.Context;
import android.content.SharedPreferences;

public final class AppPrefs {

    private static final String FILE = "smart_pantry_prefs";
    private static final String KEY_HIGHLIGHT_EXPIRY = "highlight_expiry";
    private static final String KEY_DEFAULT_UNIT = "default_unit";

    private AppPrefs() {}

    private static SharedPreferences prefs(Context c) {
        return c.getApplicationContext().getSharedPreferences(FILE, Context.MODE_PRIVATE);
    }

    public static boolean isExpiryHighlightEnabled(Context c) {
        return prefs(c).getBoolean(KEY_HIGHLIGHT_EXPIRY, true);   // on by default
    }

    public static void setExpiryHighlightEnabled(Context c, boolean enabled) {
        prefs(c).edit().putBoolean(KEY_HIGHLIGHT_EXPIRY, enabled).apply();
    }

    public static String getDefaultUnit(Context c) {
        return prefs(c).getString(KEY_DEFAULT_UNIT, "pcs");
    }

    public static void setDefaultUnit(Context c, String unit) {
        prefs(c).edit().putString(KEY_DEFAULT_UNIT, unit).apply();
    }
}
