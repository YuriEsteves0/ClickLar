package com.yuri.clicklar.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yuri.clicklar.Fragments.DashboardHomeFragment;
import com.yuri.clicklar.Fragments.DashboardImoveisFragment;
import com.yuri.clicklar.Fragments.DashboardNotificacoesFragment;
import com.yuri.clicklar.Fragments.DashboardPerfilFragment;
import com.yuri.clicklar.Fragments.DashboardVendasFragment;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseHelper.getAuth();
    private DashboardHomeFragment home = new DashboardHomeFragment();
    private DashboardPerfilFragment perfil = new DashboardPerfilFragment();
    private DashboardNotificacoesFragment notificacoes = new DashboardNotificacoesFragment();
    private DashboardImoveisFragment imoveis = new DashboardImoveisFragment();
    private DashboardVendasFragment vendas = new DashboardVendasFragment();
    private BottomNavigationView BNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityHelper.iniciarActivity(this);
        pegarViews();

        configBNV();

    }

    public void carregarFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    public void configBNV(){
        carregarFragment(home);

        BNV.setOnItemSelectedListener(v -> {
            if(v.getItemId() == R.id.home){
                carregarFragment(home);
                return true;
            }else if(v.getItemId() == R.id.perfil){
                carregarFragment(perfil);
                return true;
            }else if(v.getItemId() == R.id.notificacoes){
                carregarFragment(notificacoes);
                return true;
            }else if(v.getItemId() == R.id.imoveis){
                carregarFragment(imoveis);
                return true;
            }else if(v.getItemId() == R.id.vendas){
                carregarFragment(vendas);
                return true;
            }else{
                return false;
            }
        });
    }

    public void pegarViews(){
        BNV = findViewById(R.id.BNV);
    }
}