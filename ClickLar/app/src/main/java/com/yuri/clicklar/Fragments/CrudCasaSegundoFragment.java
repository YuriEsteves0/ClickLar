package com.yuri.clicklar.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.yuri.clicklar.R;
public class CrudCasaSegundoFragment extends Fragment {

    private TextInputLayout precoEncl, condominioEncl;
    private TextInputEditText preco, condominio, area, quarto, garagem, banheiro;
    private LinearLayout checkboxEncl;
    private CheckBox checkBox;
    private ImageView fotoPrincipal, fotoSec1, fotoSec2, fotoSec3;
    private String titulo, logradouro, bairro, selecione1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crud_casa_segundo, container, false);

        pegarViews(view);
        configPreco();
        pegarDadosAnteriores();
        configDadosInseridos();
        return view;
    }

    public void pegarDadosAnteriores(){
        titulo = getArguments().getString("titulo");
        logradouro = getArguments().getString("logradouro");
        bairro = getArguments().getString("bairro");
        selecione1 = getArguments().getString("selecione1");
    }

    public void configDadosInseridos(){
        String precoTxt = preco.getText().toString();
        String condominioTxt = condominio.getText().toString();
        String areaTxt = area.getText().toString();
        String quartoTxt = quarto.getText().toString();
        String garagemTxt = garagem.getText().toString();
        String banheiroTxt = banheiro.getText().toString();

        if(!precoTxt.isEmpty()){
            if(!condominioTxt.isEmpty()){
                if(!areaTxt.isEmpty()){
                    if(!quartoTxt.isEmpty()) {
                        if(!garagemTxt.isEmpty()) {
                            if(!banheiroTxt.isEmpty()){

                            }else{
                                banheiro.setError("Campo obrigatório");
                            }
                        }else{
                            garagem.setError("Campo obrigatório");
                        }
                    }else{
                        quarto.setError("Campo obrigatório");
                    }
                }else{
                    area.setError("Campo obrigatório");
                }
            }else{
                condominio.setError("Campo obrigatório");
            }
        }else{
            preco.setError("Campo obrigatório");
        }
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
    }
}
