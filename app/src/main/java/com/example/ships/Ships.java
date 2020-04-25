package com.example.ships;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ships.classes.UpdateHelper;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

public class Ships extends Application {

    private static final String TAG = Ships.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);

        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // set in-app defaults
        Map<String, Object> remoteConfigDefaults = new HashMap<>();
        remoteConfigDefaults.put(UpdateHelper.KEY_UPDATE_ENABLE, false);
        remoteConfigDefaults.put(UpdateHelper.KEY_UPDATE_VERSION, "1.0.0");
        remoteConfigDefaults.put(UpdateHelper.KEY_UPDATE_URL,
                "https://drive.google.com/open?id=1tu-viWYCQ4fdHARsLJZKgvMMygsCfZDR");

        firebaseRemoteConfig.setDefaultsAsync(remoteConfigDefaults);
        firebaseRemoteConfig.fetch(300) // fetch every minutes
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "remote config is fetched.");
                            firebaseRemoteConfig.activate();
                        }
                    }
                });
    }
}

