package com.example.ships;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("NEW_TOKEN",token);

        storeToken(token);
    }

    private void storeToken(String token) {
        SharedPreferencesManager.getInstance(getApplicationContext()).storeToken(token);
    }


}
