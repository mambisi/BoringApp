package com.mazeworks.boringapp.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mambisiz on 3/24/17.
 */

public class FirebaseDataReferences {
    private static DatabaseReference userDatabaseReference;

    public static DatabaseReference getUserDatabaseReference() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        return firebaseDatabase.getReference().child("users");
    }
}
