package com.example.ships.classes;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UpdateHelper {

    private static final String TAG = UpdateHelper.class.getSimpleName();

    public static String KEY_UPDATE_ENABLE = "is_update";
    public static String KEY_UPDATE_VERSION = "version";
    public static String KEY_UPDATE_URL = "update_url";

    private OnUpdateNeededListener onUpdateNeededListener;
    private Context context;

    // done
    public interface OnUpdateNeededListener {
        void onUpdateNeeded(String updateUrl);
    }

    //done
    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    //done
    public UpdateHelper(@NonNull Context context,
                              OnUpdateNeededListener onUpdateNeededListener) {
        this.context = context;
        this.onUpdateNeededListener = onUpdateNeededListener;
    }

    //done
    public void check(){
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

        if (remoteConfig.getBoolean(KEY_UPDATE_ENABLE)) {
            String currentVersion = remoteConfig.getString(KEY_UPDATE_VERSION);
            String appVersion = getAppVersion(context);
            String updateUrl = remoteConfig.getString(KEY_UPDATE_URL);

            if (!TextUtils.equals(currentVersion, appVersion)
                    && onUpdateNeededListener != null) {
                onUpdateNeededListener.onUpdateNeeded(updateUrl);
            }
        }

    }


    //done
    private String getAppVersion(Context context) {
        String result = "";

        try {
            result = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }






    public static class Builder {

        private Context context;
        private OnUpdateNeededListener onUpdateNeededListener;

        //done
        public Builder(Context context) {
            this.context = context;
        }

        //done
        public Builder onUpdateNeeded(OnUpdateNeededListener onUpdateNeededListener) {
            this.onUpdateNeededListener = onUpdateNeededListener;
            return this;
        }


        // done
        public UpdateHelper build() {
            return new UpdateHelper(context, onUpdateNeededListener);
        }

        // done
        public UpdateHelper check() {
            UpdateHelper updateHelper = build();
            updateHelper.check();

            return updateHelper;
        }
    }



}

