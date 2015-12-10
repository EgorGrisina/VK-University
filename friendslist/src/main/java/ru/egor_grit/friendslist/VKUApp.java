package ru.egor_grit.friendslist;

import android.app.Application;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class VKUApp extends Application {

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {

        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Log.e(VKUApp.class.getSimpleName(), "VKAccessToken is invalid");
            }
        }
    };

    @Override
    public void onCreate() {

        super.onCreate();
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }
}
