package com.mazeworks.boringapp;

import android.app.Application;

import com.mazeworks.boringapp.util.UserPreferences;

/**
 * Created by mambisiz on 7/31/17.
 */

public class BoringAppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UserPreferences.init(this);
    }
}
