package com.mazeworks.boringapp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mazeworks.boringapp.R;
import com.mazeworks.boringapp.entities.User;
import com.mazeworks.boringapp.util.FirebaseDataReferences;
import com.mazeworks.boringapp.util.UserPreferences;

import java.util.ArrayList;
import java.util.Collections;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LOGIN_TWITTER";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgressDialog;
    private static final int RC_SIGN_IN = 123;
    private static final boolean DEBUG = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        ///////////////FUll SCREEN///////////////////////////////////////
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /////////////////////////////////////////////////////////////////



        setContentView(R.layout.activity_login);

        createProgressDialog();

        if(DEBUG) {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                FirebaseAuth.getInstance().signInAnonymously();
            }
        }
        else {
            startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(
                                    Collections.singletonList(
                                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build())
                            )
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);
        }




        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    FirebaseDataReferences.getUserDatabaseReference().child(user.getUid()).child("id").setValue(user.getUid());
                    FirebaseDataReferences.getUserDatabaseReference().child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User nUser = dataSnapshot.getValue(User.class);
                            if (nUser == null) {
                                FirebaseDataReferences.getUserDatabaseReference()
                                        .child(user.getUid())
                                        .setValue(new User(user.getUid(), user.getEmail(), user.getDisplayName()))
                                        .addOnSuccessListener(LoginActivity.this, new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mProgressDialog.dismiss();
                                                startActivity(new Intent(LoginActivity.this, ProfileSettingsActivity.class));
                                                finish();
                                            }
                                        });

                            } else {

                                mProgressDialog.dismiss();

                                updateUserSettings(nUser);


                                if (nUser.isActivated()) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else
                                    startActivity(new Intent(LoginActivity.this, ProfileSettingsActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            mProgressDialog.dismiss();
                        }
                    });

                    //.
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(LoginActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(LoginActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };


        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void updateUserSettings(User nUser) {
        UserPreferences preferences = UserPreferences.getInstance();
        preferences.setPhone(nUser.getPhone());
        preferences.setAccountActivated(nUser.isActivated());
        preferences.setProfileUrl(nUser.getProfileUrl());
        preferences.setUsername(nUser.getName());
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Logging you in...");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                showSnackbar("Sign in SUCCESSFUL");
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar("Sign in Canceled");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar("Sign in NO CONNECTION");
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar("Sign in UNKOWN ERROR");
                    return;
                }
            }

            showSnackbar("Sign in UNKOWN RESPONSE");
        }
    }

    void showSnackbar(String s){
        Toast.makeText(this, s , Toast.LENGTH_SHORT).show();
    }
}
