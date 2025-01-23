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
import android.widget.Toast;

import com.yuri.clicklar.Adapters.CasaAdapter;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Model.Casa;
import com.yuri.clicklar.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private SearchView searchView;
    private EditText searchEditText;
    private AutoCompleteTextView selecione1, selecione2;
    private ImageView searchIcon;
    private String[] opcoesCasas = {"Casa", "Apartamento", "Terreno", "Sobrado"};
    private ImageView closeIcon;
    private String[] opcoesDinheiro = {"100k - 200k", "200k - 300k", "300k - 400k", "400k - 500k"};
    private List<Casa> casaList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        pegarViews(view);

        configSearchView();
        dropdownConfig(selecione1, opcoesCasas);
        dropdownConfig(selecione2, opcoesDinheiro);


        ConfigCasa();


        selecione1.setOnClickListener(v -> selecione1.showDropDown());

        return view;
    }


    public void ConfigCasa() {
        new Thread(new Runnable() {
            @Override
            public void run() {
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

                casaList.clear();
                casaList.add(casa);
                casaList.add(casa2);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String imagemBase64 = AndroidHelper.encodeImageToBase64(requireContext(), R.drawable.casafoda);
                        casa.setImagemBase64(Collections.singletonList(imagemBase64));

                        String imagemBase642 = AndroidHelper.encodeImageToBase64(requireContext(), R.drawable.casafoda2);
                        casa2.setImagemBase64(Collections.singletonList(imagemBase642));
                    }
                });

                CasaAdapter adapter = new CasaAdapter(casaList, requireContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }


    public void dropdownConfig(AutoCompleteTextView autoCompleteTextView, String[] opcoes) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_customizado, opcoes);
        autoCompleteTextView.setAdapter(adapter);
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
                Toast.makeText(requireContext(), "Buscando por: " + query + selecione1.getText().toString() + selecione2.getText().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void pegarViews(View view) {
        searchView = view.findViewById(R.id.search_view);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        selecione1 = view.findViewById(R.id.selecione1);
        closeIcon = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        selecione2 = view.findViewById(R.id.selecione2);
        recyclerView = view.findViewById(R.id.recyclerView);
    }
}
