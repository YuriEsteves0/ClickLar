package com.yuri.clicklar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.R;

public class CadastroActivity extends AppCompatActivity {

    private TextView loginTxt;
    private Button btnPassar;
    private TextInputEditText emailTextInputEditText, senhatextInputEditText, senha2textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityHelper.iniciarActivity(this);

        views();

        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.removerActivity(CadastroActivity.this);
                AndroidHelper.trocarActivity(CadastroActivity.this, LoginActivity.class);
                finish();
            }
        });

        btnPassar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailTextInputEditText.getText().toString().isEmpty()){
                    if(!senhatextInputEditText.getText().toString().isEmpty()){
                        if(!senha2textInputEditText.getText().toString().isEmpty()){
                            if(senhatextInputEditText.getText().toString().equals(senha2textInputEditText.getText().toString())){
                                Intent intent = new Intent(CadastroActivity.this, CadastroSegundaPaginaActivity.class);
                                intent.putExtra("email", emailTextInputEditText.getText().toString());
                                intent.putExtra("senha", senhatextInputEditText.getText().toString());
                                startActivity(intent);
                            }else{
                                senha2textInputEditText.setError("As senhas não são iguais!");
                                senha2textInputEditText.requestFocus();

                                senhatextInputEditText.setError("As senhas não são iguais!");
                                senhatextInputEditText.requestFocus();
                            }
                        }else{
                            senha2textInputEditText.setError("Preencha o campo!");
                            senha2textInputEditText.requestFocus();
                        }
                    }else{
                        senhatextInputEditText.setError("Preencha o campo!");
                        senhatextInputEditText.requestFocus();
                    }
                }else{
                    emailTextInputEditText.setError("Preencha o campo!");
                    emailTextInputEditText.requestFocus();
                }
            }
        });
    }

    public void views(){
        loginTxt = findViewById(R.id.loginTxt);
        btnPassar = findViewById(R.id.btnPassar);
        emailTextInputEditText = findViewById(R.id.emailTextInputEditText);
        senhatextInputEditText = findViewById(R.id.senhatextInputEditText);
        senha2textInputEditText = findViewById(R.id.senha2textInputEditText);
    }
}