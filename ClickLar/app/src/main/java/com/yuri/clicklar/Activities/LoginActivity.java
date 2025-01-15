package com.yuri.clicklar.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.MainActivity;
import com.yuri.clicklar.R;

public class LoginActivity extends AppCompatActivity {

    private TextView cadastroTxt;
    private Button btnEntrar;
    private FirebaseAuth auth = FirebaseHelper.getAuth();
    private TextInputEditText emailTextInputEditText, senhatextInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        views();
        ActivityHelper.iniciarActivity(this);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailTextInputEditText.getText().toString().isEmpty()){
                    if(!senhatextInputEditText.getText().toString().isEmpty()){
                        String email = emailTextInputEditText.getText().toString();
                        String senha = senhatextInputEditText.getText().toString();

                        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    ActivityHelper.removerActivity(LoginActivity.this);
                                    AndroidHelper.trocarActivity(LoginActivity.this, MainActivity.class);
                                    finish();
                                }else{
                                    AndroidHelper.mostrarMensagem(LoginActivity.this, "Erro ao fazer login");
                                }
                            }
                        });

                    }else{
                        senhatextInputEditText.setError("Campo obrigatório");
                        senhatextInputEditText.requestFocus();
                    }
                }else{
                    emailTextInputEditText.requestFocus();
                    emailTextInputEditText.setError("Campo obrigatório");
                }
            }
        });

        cadastroTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.removerActivity(LoginActivity.this);
                AndroidHelper.trocarActivity(LoginActivity.this, CadastroActivity.class);
                finish();
            }
        });
    }

    public void views(){
        cadastroTxt = findViewById(R.id.cadastroTxt);
        btnEntrar = findViewById(R.id.btnEntrar);
        emailTextInputEditText = findViewById(R.id.emailTextInputEditText);
        senhatextInputEditText = findViewById(R.id.SenhatextInputEditText);
    }
}