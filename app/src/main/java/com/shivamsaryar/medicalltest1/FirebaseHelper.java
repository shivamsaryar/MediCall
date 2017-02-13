package com.shivamsaryar.medicalltest1;

import com.firebase.client.Firebase;

/**
 * Created by Mahe on 4/26/2016.
 */
public class FirebaseHelper extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
