package com.example.myapplication.db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase{
    public void writeNewUser(String userId, String name) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).child("name").setValue(name);
    }
    public void changeUserName(String userId, String name) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).child("name").setValue(name);
    }


}
