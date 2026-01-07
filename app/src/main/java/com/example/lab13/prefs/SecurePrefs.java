package com.example.lab13.prefs;


import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public final class SecurePrefs {
    private static final String PREFS_NAME = "secure_prefs";

    private static SharedPreferences getSecurePrefs(Context context) throws Exception {
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        return EncryptedSharedPreferences.create(
                context, PREFS_NAME, masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    public static void saveToken(Context context, String token) throws Exception {
        getSecurePrefs(context).edit().putString("api_token", token).apply();
    }

    public static String loadToken(Context context) throws Exception {
        return getSecurePrefs(context).getString("api_token", "");
    }

    public static void clear(Context context) throws Exception {
        getSecurePrefs(context).edit().clear().apply();
    }
}