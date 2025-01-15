package com.yuri.clicklar.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.Model.Favorito;
import com.yuri.clicklar.Model.Usuario;
import com.yuri.clicklar.R;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class CadastroSegundaPaginaActivity extends AppCompatActivity {
    private AutoCompleteTextView ACTV;
    private Button btnVoltar, btnEntrar;
    private String[] opcoes = {"CPF", "CNPJ"};
    private String email, senha;
    private FirebaseAuth auth = FirebaseHelper.getAuth();
    private FirebaseFirestore firestore = FirebaseHelper.getFirestore();
    private TextInputEditText nomeUsuTextInputEditText, numeroIU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_segunda_pagina);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        views();
        pegarDados();

        ActivityHelper.iniciarActivity(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_customizado, opcoes);
        ACTV.setAdapter(adapter);

        ACTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTV.showDropDown();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.removerActivity(CadastroSegundaPaginaActivity.this);
                finish();
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarBD();

            }
        });
    }

    public Usuario criarUsuario(String userId, String nome, String numero) {
        Date dataAtual = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataFormatada = formatador.format(dataAtual);

        Usuario usuario = new Usuario();
        usuario.setIdUsu(userId);
        usuario.setNome(nome);
        usuario.setNPE(numero);
        usuario.setTNPE(ACTV.getText().toString());
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setAtivo(true);
        usuario.setPremium(false);
        usuario.setValido(false);
        usuario.setDataCriacao(dataFormatada);
        usuario.setNivelUsuario("usu");

        return usuario;
    }

    private void salvarUsuarioNoFirestore(Usuario usuario) {
        firestore.collection("Usuarios").document(usuario.getIdUsu())
                .set(usuario)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ActivityHelper.removerActivity(CadastroSegundaPaginaActivity.this);
                            auth.signOut();
                            AndroidHelper.trocarActivity(CadastroSegundaPaginaActivity.this, LoginActivity.class);
                            finish();
                        } else {
                            AndroidHelper.mostrarMensagem(CadastroSegundaPaginaActivity.this, "Erro ao salvar usuário");
                        }
                    }
                });
    }



    public void cadastrarBD() {
        String nome = nomeUsuTextInputEditText.getText().toString();
        String numero = numeroIU.getText().toString();

        if (!nome.isEmpty()) {
            if (!numero.isEmpty()) {
                if (ACTV.getText().toString().equals("CPF") || ACTV.getText().toString().equals("CNPJ")) {
                    auth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String userId = auth.getCurrentUser().getUid();

                                        Usuario usuario = criarUsuario(userId, nome, numero);

                                        salvarUsuarioNoFirestore(usuario);
                                    } else {
                                        // Lidar com erros de criação do usuário
                                        AndroidHelper.mostrarMensagem(CadastroSegundaPaginaActivity.this, "Erro ao criar usuário");
                                    }
                                }
                            });
                } else {
                    ACTV.setError("Preencha o campo");
                    ACTV.requestFocus();
                }
            } else {
                numeroIU.setError("Preencha o campo");
                numeroIU.requestFocus();
            }
        } else {
            nomeUsuTextInputEditText.setError("Preencha o campo");
            nomeUsuTextInputEditText.requestFocus();
        }
    }


    public void pegarDados(){
        email = getIntent().getStringExtra("email");
        senha = getIntent().getStringExtra("senha");
    }

    public void views(){
        ACTV = findViewById(R.id.ACTV);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnEntrar = findViewById(R.id.btnEntrar);
        nomeUsuTextInputEditText = findViewById(R.id.nomeUsuTextInputEditText);
        numeroIU = findViewById(R.id.numeroIU);

    }
}