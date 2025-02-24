package com.yuri.clicklar.Helpers;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yuri.clicklar.Activities.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FirebaseHelper {
    public static FirebaseAuth auth;
    public static DatabaseReference reference;
    public static FirebaseFirestore firestore;
    private static boolean loginVerificado = false;

    public static FirebaseAuth getAuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static void aumentarVisM(String idUsu){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Usuarios").document(idUsu);

        // Obtém a data atual no formato YYYY-MM
        String mesAtual = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Calendar.getInstance().getTime());

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                int visualizacoesMes = documentSnapshot.getLong("visM") != null ? documentSnapshot.getLong("visM").intValue() : 0;
                int visualizacoesTotais = documentSnapshot.getLong("visT") != null ? documentSnapshot.getLong("visT").intValue() : 0;

                visualizacoesMes++;
                visualizacoesTotais++;

                Map<String, Object> updateData = new HashMap<>();
                updateData.put("visM", visualizacoesMes);
                updateData.put("visT", visualizacoesTotais);

                userRef.update(updateData);

                DocumentReference mesRef = userRef.collection("visHistorico").document(mesAtual);
                mesRef.get().addOnSuccessListener(mesSnapshot -> {
                    int visMes = mesSnapshot.exists() && mesSnapshot.getLong("visualizacoes") != null
                            ? mesSnapshot.getLong("visualizacoes").intValue()
                            : 0;

                    Map<String, Object> mesData = new HashMap<>();
                    mesData.put("visualizacoes", visMes + 1);

                    mesRef.set(mesData);
                });
            }
        });
    }

    public static DatabaseReference getReference(){
        if(reference == null){
            reference = FirebaseDatabase.getInstance().getReference();
            return reference;
        }
        return reference;
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
