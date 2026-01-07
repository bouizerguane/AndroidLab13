package com.example.lab13.prefs;


import android.content.Context;
import android.content.SharedPreferences;

public final class AppPrefs {
    private static final String PREFS_NAME = "app_prefs";

    public static void save(Context context, String name, String lang, String theme) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString("pref_name", name)
                .putString("pref_lang", lang)
                .putString("pref_theme", theme)
                .apply(); // apply() est asynchrone (recommandé)
    }

    public static String[] load(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return new String[]{
                prefs.getString("pref_name", ""),
                prefs.getString("pref_lang", "fr"),
                prefs.getString("pref_theme", "light")
        };
    }

    public static void clear(Context context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().clear().apply();
    }
}