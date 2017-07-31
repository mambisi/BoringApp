package com.mazeworks.boringapp.util;

import android.content.SharedPreferences;

/**
 * Created by mambisiz on 4/10/17.
 */

interface IUserSettings {

    String getProfileUrl();

    void setProfileUrl(String profileUrl);

    String getUsername();

    void setUsername(String username);

    String getPhone();

    void setPhone(String phone);

    boolean isAccountActivated();

    void setAccountActivated(boolean activated);

    SharedPreferences getPreferences();
}
