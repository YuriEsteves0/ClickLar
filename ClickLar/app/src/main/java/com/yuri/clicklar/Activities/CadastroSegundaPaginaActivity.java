package com.yuri.clicklar.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.R;

public class CadastroSegundaPaginaActivity extends AppCompatActivity {

    private AutoCompleteTextView ACTV;
    private Button btnVoltar;
    private String[] opcoes = {"CPF", "CNPJ"};

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
    }

    public void views(){
        ACTV = findViewById(R.id.ACTV);
        btnVoltar = findViewById(R.id.btnVoltar);
    }
}