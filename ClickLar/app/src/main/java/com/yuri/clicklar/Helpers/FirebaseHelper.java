package com.yuri.clicklar.Helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseHelper {
    public static FirebaseAuth auth;
    public static FirebaseFirestore firestore;

    public static FirebaseAuth getAuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
            return auth;
        }
        return auth;
    }

    public static FirebaseUser getUser(){
        auth = getAuth();
        return auth.getCurrentUser();
    }

    public static FirebaseFirestore getFirestore(){
        if(firestore == null){
            firestore = FirebaseFirestore.getInstance();
            return firestore;
        }
        return firestore;
    }
}
