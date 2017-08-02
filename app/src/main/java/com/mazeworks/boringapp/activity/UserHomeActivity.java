package com.mazeworks.boringapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.mazeworks.boringapp.R;
import com.mazeworks.boringapp.layout.BlankFragment;
import com.mazeworks.boringapp.layout.MessagesContainerFragment;
import com.mazeworks.boringapp.layout.ProfileHeaderFragment;
import com.mazeworks.boringapp.layout.SearchUsersFragment;
import com.yalantis.ucrop.UCrop;

import butterknife.ButterKnife;

public class UserHomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FragmentManager mFragmentManager;
    private int mSelectedItem;
    private static final String SELECTED_ITEM = "arg_selected_item";
    private ProfileHeaderFragment mProfileFragment;
    private BottomNavigationView bottomNav;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            changeFragment(item);
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        bottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFragmentManager = getSupportFragmentManager();
        mProfileFragment = ProfileHeaderFragment.newInstance();
        //navigation.setSelectedItemId(R.id.navigation_messages);
        //ButterKnife.bind(this);

        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = bottomNav.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = bottomNav.getMenu().getItem(0);
        }
        changeFragment(selectedItem);
    }


    private void changeFragment(MenuItem item){
        Fragment frag =  null;
        switch (item.getItemId()) {
            case R.id.navigation_messages:
                frag = MessagesContainerFragment.newInstance();
                break;
            case R.id.navigation_search:
                frag = SearchUsersFragment.newInstance();
                break;
            case R.id.navigation_profile:
                frag = mProfileFragment;
                break;
        }

        mSelectedItem = item.getItemId();
        //bottomNav.setSelectedItemId(mSelectedItem);



        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, frag, frag.getTag());
            ft.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = bottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            changeFragment(homeItem);
        } else {
            super.onBackPressed();
        }
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
