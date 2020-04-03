package com.example.ships;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String SHARED_PREFERENCES_NAME = "fcmsharedpref";
    public static final String KEY_ACCESS_TOKEN = "token";

    private static Context mCtx;
    private static SharedPreferencesManager mInstance;

    private SharedPreferencesManager(Context context){
        mCtx=context;
    }

    public static synchronized SharedPreferencesManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPreferencesManager(context);
        }
        return mInstance;
    }

    public boolean storeToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.apply();
        return true;
    }

    public String getToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN,null);
    }

}
