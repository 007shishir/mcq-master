package com.mcqmaster.saif_win10.mcqmaster;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MCQMaster extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
