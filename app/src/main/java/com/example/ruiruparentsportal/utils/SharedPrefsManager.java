package com.example.ruiruparentsportal.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManager {
    private static final String SHARED_PREFS_NAME = "parent_portal";
    private static SharedPrefsManager mInstance;
    private SharedPreferences sharedPreferences;
    //private Context mCtx;

    private SharedPrefsManager(Context context) {

        /*try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    SHARED_PREFS_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }*/
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        //mCtx = context;
    }

    public static synchronized SharedPrefsManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefsManager(context);
        }
        return mInstance;
    }

    public void userLogin(String name, String email, String phone, Boolean isLoggedIn) {
        sharedPreferences.edit()
                .putString("name", name)
                .putString("email", email)
                .putString("phone", phone)
                .putBoolean("isLoggedIn", isLoggedIn)
                .apply();
    }

    public String[] getUserInformation() {
        return new String[]{
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("phone", null)
        };
    }


    public Boolean loginCheck() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    public void logoutUser() {
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply();
    }

    public void saveUserData(String serializedData) {
        sharedPreferences.edit().putString("user_data", serializedData).apply();
    }

    public void resetSharefPrefs() {
        try {
            sharedPreferences.getAll().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
