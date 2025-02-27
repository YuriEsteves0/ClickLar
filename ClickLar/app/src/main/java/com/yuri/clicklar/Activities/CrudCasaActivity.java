package com.yuri.clicklar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.yuri.clicklar.Fragments.CrudCasaPrimeiroFragment;
import com.yuri.clicklar.Fragments.CrudCasaSegundoFragment;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.R;

public class CrudCasaActivity extends AppCompatActivity {

    private static final String TAG = "CrudCasaActivity";
    private Button btnProximo, btnCancelar;
    private ImageView voltarBTN;
    private FrameLayout FL;
    private CrudCasaPrimeiroFragment casaPrimeiroFragment = new CrudCasaPrimeiroFragment();
    private CrudCasaSegundoFragment casaSegundoFragment = new CrudCasaSegundoFragment();
    private boolean primeiroFragment = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crud_casa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityHelper.iniciarActivity(this);
        pegarViews();

        Log.d(TAG, "FRAGMENT: " + primeiroFragment);
        configFragment(casaPrimeiroFragment);

        configBtn();

    }

    public void configBtn(){
        btnCancelar.setOnClickListener(v -> {
            if(!primeiroFragment){
                //VAI VOLTAR PARA O PRIMEIRO FRAGMENT

                configFragment(casaPrimeiroFragment);
                primeiroFragment = true;
            }else{
                //ESTÁ NO PRIMEIRO FRAGMENT

                ActivityHelper.removerActivity(this);
                finish();
            }
            Log.d(TAG, "PRIMEIRO FRAGMENT:" + primeiroFragment);
        });

        voltarBTN.setOnClickListener(v -> {
            ActivityHelper.removerActivity(this);
            finish();
        });

        btnProximo.setOnClickListener(v -> {
            Fragment fragmentAtual = getSupportFragmentManager().findFragmentById(R.id.FL);

            if (fragmentAtual instanceof CrudCasaPrimeiroFragment) {
                CrudCasaPrimeiroFragment primeiroFragment = (CrudCasaPrimeiroFragment) fragmentAtual;

                Bundle bundle = primeiroFragment.pegarDadosInseridos();

                CrudCasaSegundoFragment segundoFragment = new CrudCasaSegundoFragment();
                segundoFragment.setArguments(bundle);

                configFragment(segundoFragment);
            }

            primeiroFragment = false;
            Log.d(TAG, "PRIMEIRO FRAGMENT:" + primeiroFragment);
        });
    }

    public void configFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.FL, fragment).commit();
    }

    public void pegarViews(){
        btnProximo = findViewById(R.id.btnProximo);
        btnCancelar = findViewById(R.id.btnCancelar);
        FL = findViewById(R.id.FL);
        voltarBTN = findViewById(R.id.voltarBTN);
    }

}