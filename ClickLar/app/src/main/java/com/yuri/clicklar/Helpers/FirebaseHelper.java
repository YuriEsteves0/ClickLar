package com.yuri.clicklar.Helpers;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yuri.clicklar.Activities.LoginActivity;

public class FirebaseHelper {
    public static FirebaseAuth auth;
    public static FirebaseFirestore firestore;
    private static boolean loginVerificado = false;

    public static FirebaseAuth getAuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static FirebaseUser getCurrentUser() {
        return getAuth().getCurrentUser();
    }

    public static void verificarLogin(Context context) {
        if (!loginVerificado) {
            FirebaseAuth auth1 = getAuth();
            if (auth.getCurrentUser() == null) {
                AndroidHelper.trocarActivity(context, LoginActivity.class);
                ActivityHelper.fecharTodasActivities();
                Log.e("ERRO", "verificarLogin: USUARIO NÃO VERIFICADO");
            } else {
                Log.d("ERRO", "verificarLogin: USUARIO VERIFICADO");
            }
            loginVerificado = true;
        }
    }

    public static FirebaseFirestore getFirestore(){
        if(firestore == null){
            firestore = FirebaseFirestore.getInstance();
            return firestore;
        }
        return firestore;
    }

}
