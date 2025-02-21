package com.yuri.clicklar.Activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.R;

public class ConfiguracoesActivity extends AppCompatActivity {

    private TextView logout, configPerfil, faleConosco, dashboard;
    private FirebaseAuth auth = FirebaseHelper.getAuth();
    private ImageView voltarBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuracoes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityHelper.iniciarActivity(this);

        pegarViews();

        botoes();

    }

    public void botoes(){
        logout.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ConfiguracoesActivity.this)
                    .setIcon(R.drawable.logout)
                    .setTitle("Você realmente deseja sair?")
                    .setMessage("Ao sair, você terá que fazer login novamente para acessar o aplicativo.")
                    .setPositiveButton("Sair", (dialog, which) -> {
                        auth.signOut();
                        ActivityHelper.fecharTodasActivities();
                        finish();
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

            androidx.appcompat.app.AlertDialog dialog = builder.show();

            // Define a cor de fundo do diálogo
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

            // Personalizar cor do texto da mensagem
            TextView messageText = dialog.findViewById(android.R.id.message);
            if (messageText != null) {
                messageText.setTextColor(Color.BLACK);
            }

            // Alterando as cores dos botões
            dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK); // Cancelar (Preto)
            dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFA500")); // Sair (Laranja)
        });

        configPerfil.setOnClickListener(v -> {

        });

        voltarBTN.setOnClickListener(v -> {
            ActivityHelper.removerActivity(this);
            finish();
        });

        faleConosco.setOnClickListener(v -> {

        });

        dashboard.setOnClickListener(v -> {
            AndroidHelper.trocarActivity(this, DashboardActivity.class);
        });

    }

    public void pegarViews(){
        logout = findViewById(R.id.logout);
        configPerfil = findViewById(R.id.configPerfil);
        faleConosco = findViewById(R.id.faleConosco);
        voltarBTN = findViewById(R.id.voltarBTN);
        dashboard = findViewById(R.id.dashboard);

    }
}