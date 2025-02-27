package com.yuri.clicklar.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputEditText;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.R;

public class CrudCasaPrimeiroFragment extends Fragment {

    private static final String TAG = "CrudCasaPrimeiroFragment";
    private String[] statusCasa = {"Venda", "Locação", "Temporada"};
    private AutoCompleteTextView selecione1;
    private boolean add;
    private TextInputEditText titulo, logradouro, bairro, CEP;
    private boolean edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crud_casa_primeiro, container, false);


        pegarViews(view);
        pegarDados();
        configDropDown();

        pegarDadosInseridos();
        return view;
    }

    public Bundle pegarDadosInseridos() {
        String tituloTxt = titulo.getText().toString();
        String logradouroTxt = logradouro.getText().toString();
        String bairroTxt = bairro.getText().toString();
        String selecione1Txt = selecione1.getText().toString();
        String CEPTxt = CEP.getText().toString();

        if(!tituloTxt.isEmpty()){
            if(!CEPTxt.isEmpty()){
                if(!logradouroTxt.isEmpty()){
                    if(!bairroTxt.isEmpty()){
                        if(!selecione1Txt.isEmpty()){
                            Bundle bundle = new Bundle();
                            bundle.putString("titulo", tituloTxt);
                            bundle.putString("logradouro", logradouroTxt);
                            bundle.putString("bairro", bairroTxt);
                            bundle.putString("selecione1", selecione1Txt);
                            return bundle;
                        }else{
                            selecione1.setError("Campo obrigatório");
                            return null;
                        }
                    }else{
                        bairro.setError("Campo obrigatório");
                        return null;
                    }
                }else{
                    logradouro.setError("Campo obrigatório");
                    return null;
                }
            }else{
                CEP.setError("Campo obrigatório");
                return null;
            }
        }else{
            titulo.setError("Campo obrigatório");
            return null;
        }
    }


    public void pegarDados(){
        Intent intent = getActivity().getIntent();
        String action = intent.getStringExtra("action");
        Log.d(TAG, "ACTION SELECTED: " + action + " Intent Atual: " + intent.toString());
        switch (action){
            case "add":
                add = true;
                edit = false;
                break;
            case "edit":
                edit = true;
                add = false;
                break;
            default:
                ActivityHelper.removerActivity(getActivity());
                AndroidHelper.mostrarMensagem(getContext(), "ERRO");
                getActivity().finish();
                break;
        }
    }

    public void configDropDown(){
        selecione1.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.dropdown_customizado, statusCasa));
    }

    public void pegarViews(View view){
        selecione1 = view.findViewById(R.id.selecione1);
        titulo = view.findViewById(R.id.titulo);
        logradouro = view.findViewById(R.id.logradouro);
        bairro = view.findViewById(R.id.bairro);
        CEP = view.findViewById(R.id.CEP);
    }
}