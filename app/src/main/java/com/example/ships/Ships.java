package com.example.ships;

import android.app.Application;

import com.firebase.client.Firebase;

public class Ships extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
