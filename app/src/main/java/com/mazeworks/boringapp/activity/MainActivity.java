package com.mazeworks.boringapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.mazeworks.boringapp.R;
import com.mazeworks.boringapp.util.UserPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            UserPreferences u = UserPreferences.getInstance();

            if(u.isAccountActivated() && u.getUsername() != null)
                startActivity(new Intent(this , UserHomeActivity.class));
            else
                startActivity(new Intent(this, ProfileSettingsActivity.class));
            finish();
        }

    }


}
