package com.yuri.clicklar.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuri.clicklar.Adapters.CasaDashboardAdapter;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Model.Casa;
import com.yuri.clicklar.R;

import java.util.ArrayList;
import java.util.List;


public class DashboardImoveisFragment extends Fragment {

    private TextView filtroTxt, textApoio;
    private ImageView iconeBtn, voltarBTN;
    private EditText searchEditText;
    private ImageView searchIcon, closeIcon;
    private SearchView searchView;
    private AutoCompleteTextView selecione1, selecione2, selecione3;
    private String[] opcoesCasas = {"Casa", "Apartamento", "Terreno", "Sobrado"};
    private String[] opcoesDinheiro = {"100k - 200k", "200k - 300k", "300k - 400k", "400k - 500k"};
    private String[] statusImovel = {"Venda", "Locação", "Temporada"};
    private List<Casa> casaList = new ArrayList<>();
    private RecyclerView RV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_imoveis, container, false);

        pegarViews(view);
        configToolbar();
        dropdownConfig(selecione1, opcoesDinheiro);
        dropdownConfig(selecione2, statusImovel);
        dropdownConfig(selecione3, opcoesCasas);

        configSearchView();

        textApoio.setVisibility(View.GONE);

        configRv();
        configDados();

        voltarBTN.setOnClickListener(v -> {
            getActivity().finish();
        });

        return view;
    }

    public void configRv(){
        CasaDashboardAdapter adapter = new CasaDashboardAdapter(casaList, getContext());
        RV.setLayoutManager(new LinearLayoutManager(getContext()));
        RV.setAdapter(adapter);
    }

    public void configDados(){
        casaList.clear();
        Casa casa = new Casa();
        casa.setNomeCasa("Casa T3... - ");
        casa.setStatusCasa("Venda");
        casa.setPrecoCasaAntigo("R$ 699.000");
        casa.setPrecoCasa("R$ 499.000");
        casa.setPromocao("5% OFF");
        casa.setQntQuarto("3");
        casa.setQntArea("100m²");
        casa.setQntGaragem("2");
        casa.setQntBanheiro("2");
        casa.setLocalizacao("Rua Cor... - ");
        casa.setBairro("Nilopolis");
        casa.setUidDono("DipmgfeikQbhch6nXGfxxcU0UNq2");
        casa.setValorCondominio("1300");

        Casa casa2 = new Casa();
        casa2.setNomeCasa("Casa T1... - ");
        casa2.setStatusCasa("Aluguel");
        casa2.setPrecoCasaAntigo("0");
        casa2.setPrecoCasa("R$ 250.000");
        casa2.setPromocao("0");
        casa2.setQntQuarto("1");
        casa2.setQntArea("80m²");
        casa2.setQntGaragem("0");
        casa2.setLocalizacao("Barro V... - ");
        casa2.setQntBanheiro("1");
        casa2.setBairro("Belford Roxo");
        casa2.setUidDono("DipmgfeikQbhch6nXGfxxcU0UNq2");
        casa2.setValorCondominio("1900");

        casaList.add(casa);
        casaList.add(casa2);

        RV.getAdapter().notifyDataSetChanged();
    }

    public void dropdownConfig(AutoCompleteTextView selecione, String[] opcoes){
        selecione.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.dropdown_customizado, opcoes));
    }

    public void configToolbar(){
        iconeBtn.setImageResource(R.drawable.add);
        iconeBtn.setVisibility(View.VISIBLE);

        iconeBtn.setOnClickListener(v-> {
            AndroidHelper.mostrarMensagem(getContext(), "Funcionalidade em desenvolvimento!");
        });
    }

    public void configSearchView() {
        searchView.setIconifiedByDefault(false);

        searchEditText.setTextSize(16);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            searchEditText.setAutofillHints(View.AUTOFILL_HINT_POSTAL_ADDRESS);
        }
        searchEditText.setHint("Digite o nome da rua, bairro, estado");
        searchEditText.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));

        searchIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black));
        closeIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String texto = "Filtrar por: ";

                if(!selecione1.getText().toString().isEmpty()){
                    texto += "R$ " + selecione1.getText().toString() + ", ";
                }else{
                    texto += "R$ 100k - 500k";
                }
                if(!selecione2.getText().toString().isEmpty()){
                    texto += selecione2.getText().toString() + ", ";
                }else{
                    texto += "Venda";
                }
                if(!selecione3.getText().toString().isEmpty()){
                    texto += selecione3.getText().toString();
                }else{
                    texto += "Casa";
                }

                filtroTxt.setText(texto);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    public void pegarViews(View view){
        iconeBtn = view.findViewById(R.id.iconeBtn);
        voltarBTN = view.findViewById(R.id.voltarBTN);
        selecione1 = view.findViewById(R.id.selecione1);
        selecione2 = view.findViewById(R.id.selecione2);
        selecione3 = view.findViewById(R.id.selecione3);
        searchView = view.findViewById(R.id.search_view);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        closeIcon = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        filtroTxt = view.findViewById(R.id.filtroTxt);
        textApoio = view.findViewById(R.id.textApoio);
        RV = view.findViewById(R.id.RV);
    }
}