package com.yuri.clicklar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.MainActivity;
import com.yuri.clicklar.R;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//         Obtenha a instância de FirebaseAuth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d("TAG", "onCreate: passou aqui " + user);
        ActivityHelper.iniciarActivity(this);

        if (user == null) {
            AndroidHelper.trocarActivity(SplashActivity.this, LoginActivity.class);
        } else {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }, 200);
        }
        ActivityHelper.fecharTodasActivities();
        finish();
    }
}
