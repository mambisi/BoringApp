package com.mazeworks.boringapp.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by mambisiz on 8/1/17.
 */

public class MessageInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String idToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        FirebaseDatabase.getInstance().getReference().child("notifications").child(user.getUid()).setValue(idToken);
    }
}
