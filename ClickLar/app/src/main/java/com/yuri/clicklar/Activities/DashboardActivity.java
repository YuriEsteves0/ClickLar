package com.yuri.clicklar.Activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.R;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseHelper.getAuth();
    private ImageView perfil, voltarBTN;
    private TextView nomeUsu, porcentagem, qntUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityHelper.iniciarActivity(this);
        pegarViews();

        voltarBTN.setOnClickListener(v -> {
            ActivityHelper.removerActivity(this);
            finish();
        });
    }

    public void pegarViews(){
        perfil = findViewById(R.id.perfil);
        nomeUsu = findViewById(R.id.nomeUsu);
        porcentagem = findViewById(R.id.porcentagem);
        qntUsuarios = findViewById(R.id.qntUsuarios);
        voltarBTN = findViewById(R.id.voltarBTN);

    }
}