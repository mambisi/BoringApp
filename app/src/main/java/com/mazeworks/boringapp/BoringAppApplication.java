package com.mazeworks.boringapp;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
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
