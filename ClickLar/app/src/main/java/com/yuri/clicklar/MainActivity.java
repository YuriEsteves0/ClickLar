package com.yuri.clicklar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.yuri.clicklar.Adapters.CasaAdapter;
import com.yuri.clicklar.Fragments.AmigosFragment;
import com.yuri.clicklar.Fragments.ChatFragment;
import com.yuri.clicklar.Fragments.FeedFragment;
import com.yuri.clicklar.Fragments.HomeFragment;
import com.yuri.clicklar.Fragments.PerfilFragment;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Model.Casa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private HomeFragment home = new HomeFragment();
    private FeedFragment feed = new FeedFragment();
    private AmigosFragment amigos = new AmigosFragment();
    private ChatFragment chat = new ChatFragment();
    private PerfilFragment perfil = new PerfilFragment();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pegarViews();
        ActivityHelper.iniciarActivity(this);
        configBottomNav();

    }

    public void configBottomNav(){
        carregarFragment(home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Integer id = item.getItemId();

                if (id == R.id.home) {
                    carregarFragment(home);
                    return true;
                }
                if (id == R.id.feed) {
                    carregarFragment(feed);
                    return true;
                }
                if (id == R.id.amigos) {
                    carregarFragment(amigos);
                    return true;
                }
                if (id == R.id.chat) {
                    carregarFragment(chat);
                    return true;
                }
                if (id == R.id.perfil) {
                    carregarFragment(perfil);
                    return true;
                }
                return false;
            }
        });
    }

    public void carregarFragment(Fragment fragment){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.commit();
            }
        }).start();
    }



    public void pegarViews(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }
}
