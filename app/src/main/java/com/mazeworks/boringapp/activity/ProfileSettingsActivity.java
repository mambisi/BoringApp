package com.mazeworks.boringapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.FrameLayout;

import com.mazeworks.boringapp.R;
import com.mazeworks.boringapp.layout.ProfileHeaderFragment;
import com.yalantis.ucrop.UCrop;

public class ProfileSettingsActivity extends AppCompatActivity {
    private ProfileHeaderFragment mProfileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        mProfileFragment = ProfileHeaderFragment.newInstance();

        AppCompatButton doneBtn = (AppCompatButton) findViewById(R.id.done_btn);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.container);


        addFragmentToLayout(frameLayout,mProfileFragment);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileSettingsActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void addFragmentToLayout(View view, Fragment fragment) {
        FragmentManager fragmentManager =  getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .replace(view.getId() , fragment, "Profile_Fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commitNow();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            mProfileFragment.setCroppedImageUri(resultUri);
        }
        else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            cropError.printStackTrace();
        }
    }
}
