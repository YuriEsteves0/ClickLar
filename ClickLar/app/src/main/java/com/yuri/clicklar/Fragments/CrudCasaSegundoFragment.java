package com.yuri.clicklar.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.yuri.clicklar.R;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

public class CrudCasaSegundoFragment extends Fragment {

    private TextInputLayout precoEncl, condominioEncl;
    private TextInputEditText preco, condominio, area, quarto, garagem, banheiro;
    private LinearLayout checkboxEncl;
    private CheckBox checkBox;
    private TextView erroFotos;
    private ImageView fotoPrincipal, fotoSec1, fotoSec2, fotoSec3;
    private String titulo, logradouro, bairro, selecione1;
    private final int IMAGE_PICK_REQUEST = 1;
    private String tipoFoto;
    private Map<String, Uri> fotosDados = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crud_casa_segundo, container, false);

        pegarViews(view);
        configPreco();
        pegarDadosAnteriores();
        configDadosInseridos();
        configImagens();
        return view;
    }

    private void configImagens() {
        fotoPrincipal.setOnClickListener(v ->{
            abrirGaleria();
            tipoFoto = "FP";
        });
        fotoSec1.setOnClickListener(v ->{
            abrirGaleria();
            tipoFoto = "FS1";
        });
        fotoSec2.setOnClickListener(v ->{
            abrirGaleria();
            tipoFoto = "FS2";
        });
        fotoSec3.setOnClickListener(v ->{
            abrirGaleria();
            tipoFoto = "FS3";
        });
    }

    public void abrirGaleria(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_PICK_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();

            if(tipoFoto.equals("FP")){
                fotoPrincipal.setImageURI(uri);
            }else if(tipoFoto.equals("FS1")) {
                fotoSec1.setImageURI(uri);
            }else if(tipoFoto.equals("FS2")) {
                fotoSec2.setImageURI(uri);
            }else if(tipoFoto.equals("FS3")) {
                fotoSec3.setImageURI(uri);
            }

            fotosDados.put(tipoFoto, uri);
        }
    }

    public void pegarDadosAnteriores(){
        titulo = getArguments().getString("titulo");
        logradouro = getArguments().getString("logradouro");
        bairro = getArguments().getString("bairro");
        selecione1 = getArguments().getString("selecione1");
    }

    private boolean usuarioSelecionouFoto() {
        return fotosDados.containsKey("FP") && fotosDados.containsKey("FS1") &&
                fotosDados.containsKey("FS2") && fotosDados.containsKey("FS3");
    }


    public Bundle configDadosInseridos(){
        String precoTxt = preco.getText().toString();
        String condominioTxt = condominio.getText().toString();
        String areaTxt = area.getText().toString();
        String quartoTxt = quarto.getText().toString();
        String garagemTxt = garagem.getText().toString();
        String banheiroTxt = banheiro.getText().toString();

        if(precoTxt.isEmpty()) {
            preco.setError("Campo obrigatório");
            return null;
        }
        if(condominioTxt.isEmpty()) {
            condominio.setError("Campo obrigatório");
            return null;
        }
        if(areaTxt.isEmpty()) {
            area.setError("Campo obrigatório");
            return null;
        }
        if(quartoTxt.isEmpty()) {
            quarto.setError("Campo obrigatório");
            return null;
        }
        if(garagemTxt.isEmpty()) {
            garagem.setError("Campo obrigatório");
            return null;
        }
        if(banheiroTxt.isEmpty()) {
            banheiro.setError("Campo obrigatório");
            return null;
        }
        if(!usuarioSelecionouFoto()) {
            erroFotos.setVisibility(View.VISIBLE);
            return null;
        } else {
            erroFotos.setVisibility(View.GONE);
        }

        Bundle bundle = new Bundle();
        bundle.putString("titulo", titulo);
        bundle.putString("logradouro", logradouro);
        bundle.putString("bairro", bairro);
        bundle.putString("selecione1", selecione1);
        bundle.putString("preco", precoTxt);
        bundle.putString("condominio", condominioTxt);
        bundle.putString("area", areaTxt);
        bundle.putString("quarto", quartoTxt);
        bundle.putString("garagem", garagemTxt);
        bundle.putString("banheiro", banheiroTxt);
        bundle.putParcelable("fotoPrincipal", fotosDados.get("FP"));
        bundle.putParcelable("fotoSec1", fotosDados.get("FS1"));
        bundle.putParcelable("fotoSec2", fotosDados.get("FS2"));
        bundle.putParcelable("fotoSec3", fotosDados.get("FS3"));

        Log.d("CrudCasaSegundoFragment", "Bundle gerado: " + bundle);
        
        return bundle;
    }

    public void configPreco() {
        preco.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                Log.d("TAG", "TEM FOCO");
                checkboxEncl.setVisibility(View.VISIBLE);
            } else {
                if(!checkBox.isChecked()){
                    Log.d("TAG", "SEM FOCO");
                    checkboxEncl.setVisibility(View.GONE);
                    condominioEncl.setVisibility(View.GONE);
                    checkBox.setChecked(false);
                }
            }
        });

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Log.d("TAG", "TA CHECKADO");
                condominioEncl.setVisibility(View.VISIBLE);
            } else {
                Log.d("TAG", "DESCHECKOU");
                condominioEncl.setVisibility(View.GONE);
            }
        });
    }

    public void pegarViews(View view) {
        precoEncl = view.findViewById(R.id.precoEncl);
        preco = view.findViewById(R.id.preco);
        condominioEncl = view.findViewById(R.id.condominioEncl);
        checkboxEncl = view.findViewById(R.id.checkboxEncl);
        condominio = view.findViewById(R.id.condominio);
        area = view.findViewById(R.id.area);
        quarto = view.findViewById(R.id.quarto);
        garagem = view.findViewById(R.id.garagem);
        banheiro = view.findViewById(R.id.banheiro);
        fotoPrincipal = view.findViewById(R.id.fotoPrincipal);
        fotoSec1 = view.findViewById(R.id.fotoSec1);
        fotoSec2 = view.findViewById(R.id.fotoSec2);
        fotoSec3 = view.findViewById(R.id.fotoSec3);
        checkBox = view.findViewById(R.id.checkbox);
        erroFotos = view.findViewById(R.id.erroFotos);
    }
}
