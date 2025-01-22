package com.yuri.clicklar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuri.clicklar.Adapters.CasaAdapter;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Model.Casa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pegarViews();

        configSearchView();
        dropdownConfig(selecione1, opcoesCasas);
        dropdownConfig(selecione2, opcoesDinheiro);

        ConfigCasa();

        selecione1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecione1.showDropDown();
            }
        });
    }

    public void ConfigCasa(){
        Casa casa = new Casa();
        casa.setNomeCasa("Casa T3 em Niló... - ");
        casa.setStatusCasa("Venda");
        casa.setPrecoCasaAntigo("R$ 699.000");
        casa.setPrecoCasa("R$ 499.000");
        casa.setPromocao("5% OFF");
        casa.setQntQuarto("3");
        casa.setQntArea("100m²");
        casa.setQntGaragem("2");
        casa.setQntBanheiro("2");

        casaList.add(casa);
        String imagemBase64 = AndroidHelper.encodeImageToBase64(this, R.drawable.casafoda);
        casa.setImagemBase64(Collections.singletonList(imagemBase64));

        CasaAdapter adapter = new CasaAdapter(casaList, MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.notifyDataSetChanged();
    }

    public void dropdownConfig(AutoCompleteTextView autoCompleteTextView, String[] opcoes){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_customizado, opcoes);
        autoCompleteTextView.setAdapter(adapter);
    }

    public void configSearchView(){
        searchView.setIconifiedByDefault(false);

        searchEditText.setTextSize(16);
        searchEditText.setHint("Digite o nome da rua, bairro, estado");
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.black));

        searchIcon.setColorFilter(ContextCompat.getColor(this, R.color.black));
        closeIcon.setColorFilter(ContextCompat.getColor(this, R.color.black));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Buscando por: " + query + selecione1.getText().toString() + selecione2.getText().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void pegarViews(){
        searchView = findViewById(R.id.search_view);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        selecione1 = findViewById(R.id.selecione1);
        closeIcon = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        selecione2 = findViewById(R.id.selecione2);
        recyclerView = findViewById(R.id.recyclerView);
    }
}
