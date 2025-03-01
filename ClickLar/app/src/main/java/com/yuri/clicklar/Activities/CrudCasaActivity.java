package com.yuri.clicklar.Activities;

import android.content.Intent;
import android.net.Uri;
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

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yuri.clicklar.Fragments.CrudCasaPrimeiroFragment;
import com.yuri.clicklar.Fragments.CrudCasaSegundoFragment;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.Helpers.UploadHelper;
import com.yuri.clicklar.Interfaces.APIService;
import com.yuri.clicklar.Model.Casa;
import com.yuri.clicklar.R;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrudCasaActivity extends AppCompatActivity {

    private static final String TAG = "CrudCasaActivity";
    private Button btnProximo, btnCancelar;
    private ImageView voltarBTN;
    private String uidUsu = FirebaseHelper.getAuth().getUid();
    private FrameLayout FL;
    private FirebaseFirestore firestore = FirebaseHelper.getFirestore();
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

                Log.d(TAG, "BUNDLE: " + bundle);
                CrudCasaSegundoFragment segundoFragment = new CrudCasaSegundoFragment();
                segundoFragment.setArguments(bundle);

                configFragment(segundoFragment);
            }

            if(fragmentAtual instanceof CrudCasaSegundoFragment){
                CrudCasaSegundoFragment segundoFragment = (CrudCasaSegundoFragment) fragmentAtual;
                Bundle bundle = segundoFragment.configDadosInseridos();

                if (bundle == null) {
                    Log.e(TAG, "Erro: Bundle está null, verifique se todos os campos obrigatórios foram preenchidos.");
                    return;
                }

                salvarBD(bundle);

                Log.d(TAG, "configBtn: SEGUNDO FRAGMENT " + bundle);
            }

            primeiroFragment = false;
            Log.d(TAG, "PRIMEIRO FRAGMENT:" + primeiroFragment);
        });
    }

    public void salvarBD(Bundle bundle){
        String titulo = bundle.getString("titulo");
        String logradouro = bundle.getString("logradouro");
        String bairro = bundle.getString("bairro");
        String selecione1 = bundle.getString("selecione1");
        String preco = bundle.getString("preco");
        String condominio = bundle.getString("condominio");
        String area = bundle.getString("area");
        String quarto = bundle.getString("quarto");
        String garagem = bundle.getString("garagem");
        String banheiro = bundle.getString("banheiro");

        //SALVAR NO SERVIDOR
        Uri fotoPrincipal = bundle.getParcelable("fotoPrincipal");
        Uri fotoSec1 = bundle.getParcelable("fotoSec1");
        Uri fotoSec2 = bundle.getParcelable("fotoSec2");
        Uri fotoSec3 = bundle.getParcelable("fotoSec3");

        Log.d(TAG, "PRINCIPAL: " + fotoPrincipal + " SEC: " + fotoSec1 + " SEC2: " + fotoSec2 + " SEC3: " + fotoSec3);

        Casa casa = new Casa();
        casa.setNomeCasa(titulo);
        casa.setLocalizacao(logradouro);
        casa.setBairro(bairro);
        casa.setStatusCasa(selecione1);
        casa.setPromocao("0");
        casa.setPrecoCasa(preco);
        casa.setQntArea(area);
        casa.setQntQuarto(quarto);
        casa.setQntGaragem(garagem);
        casa.setQntBanheiro(banheiro);
        casa.setValorCondominio(condominio);
        casa.setUidDono(uidUsu);

        String uidCasa = firestore.collection("Imóveis").document().getId();

//        String uidCasaReformado
        for (int i = 0; i < 4; i++) {
            firestore.collection("Imoveis").document(uidCasa).set(casa).addOnCompleteListener(v -> {
                if(v.isSuccessful()){
                    UploadHelper uploadHelper = new UploadHelper();
                    uploadHelper.uploadImage(getApplicationContext(), fotoPrincipal, uidCasa, "imgprod", "fotoImovel_Principal");
                    uploadHelper.uploadImage(getApplicationContext(), fotoSec1, uidCasa, "imgprod", "fotoImovel_Sec1");
                    uploadHelper.uploadImage(getApplicationContext(), fotoSec2, uidCasa, "imgprod", "fotoImovel_Sec2");
                    uploadHelper.uploadImage(getApplicationContext(), fotoSec3, uidCasa, "imgprod", "fotoImovel_Sec3");ActivityHelper.removerActivity(this);
                    finish();
                }else{
                    AndroidHelper.mostrarMensagem(this, "Erro ao salvar casa");
                    ActivityHelper.removerActivity(this);
                    finish();
                }
            });
        }
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