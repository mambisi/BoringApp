package com.mazeworks.boringapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by mambisiz on 1/17/17.
 */

public class UserPreferences implements IUserSettings{
    private SharedPreferences preferences;
    private static UserPreferences userPreferences;
    private static final String USER_PROFILE_KEY = "user.profile";
    private static final String USER_NAME_KEY = "user.name";
    private static final String USER_PHONE_KEY = "user.phone";
    private static final String USER_ACTIVATED_KEY = "user.activated";
    public static void init(Context context) {
        userPreferences = new UserPreferences(context);
    }


    public static UserPreferences getInstance() {
        return userPreferences;
    }

    private UserPreferences(Context context) {
        preferences = context.getSharedPreferences("KASABI_APP", Context.MODE_PRIVATE);
    }

    @Override
    public String getProfileUrl() {
        String p = preferences.getString(USER_PROFILE_KEY, null);
        return p;
    }

    @Override
    public void setProfileUrl(String profileUrl) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_PROFILE_KEY, profileUrl);
        editor.commit();
        EventBus.getDefault().post(new MessageEvent(Events.PROFILE_URL_UPDATE));
    }

    @Override
    public String getUsername() {
        String p = preferences.getString(USER_NAME_KEY, null);
        return p;
    }
    @Override
    public void setUsername(String username) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_NAME_KEY, username);

        FirebaseDataReferences.getUserDatabaseReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("name").setValue(username);

        editor.commit();
        if (!isAccountActivated())
            setAccountActivated(true);
        EventBus.getDefault().post(new MessageEvent(Events.DISPLAY_NAME_UPDATE));
    }

    @Override
    public String getPhone() {
        String p = preferences.getString(USER_PHONE_KEY, null);
        return p;
    }
    @Override
    public void setPhone(String phone) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_PHONE_KEY, phone);

        FirebaseDataReferences.getUserDatabaseReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("phone").setValue(phone);

        editor.commit();
        EventBus.getDefault().post(new MessageEvent(Events.PHONE_CHANGED));
    }
    @Override
    public boolean isAccountActivated() {
        boolean p = preferences.getBoolean(USER_ACTIVATED_KEY, false);
        return p;
    }
    @Override
    public void setAccountActivated(boolean activated) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(USER_ACTIVATED_KEY, activated);

        FirebaseDataReferences.getUserDatabaseReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("activated").setValue(activated);

        editor.commit();
        EventBus.getDefault().post(new MessageEvent(Events.ACCOUNT_ACTIVATED));
    }
    @Override
    public SharedPreferences getPreferences() {
        return preferences;
    }
}