package com.yuri.clicklar.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.R;

import java.util.ArrayList;

public class DashboardHomeFragment extends Fragment {

    private ImageView perfil;
    private TextView nomeUsu, porcentagem, qntUsuarios;
    private BarChart barChart;
    private String userID = FirebaseHelper.getAuth().getCurrentUser().getUid();
    private FirebaseFirestore userRef = FirebaseHelper.getFirestore();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_home, container, false);

        pegarViews(view);
        configChart();

        return view;
    }

    public void configChart(){
        userRef.collection("Usuarios").document(userID).collection("visHistorico").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    ArrayList<String> labels = new ArrayList<>();
                    ArrayList<Integer> colors = new ArrayList<>();
                    int index = 0;
                    int prevVisualizacoes = -1;

                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        String mesAno = doc.getId(); // Exemplo: "2024-01"
                        int visualizacoes = doc.getLong("visualizacoes").intValue();

                        entries.add(new BarEntry(index, visualizacoes));
                        labels.add(mesAno.substring(5));

                        if (prevVisualizacoes != -1) {
                            if (visualizacoes > prevVisualizacoes) {
                                colors.add(getResources().getColor(R.color.azulPrimario));
                            } else {
                                colors.add(getResources().getColor(R.color.vermelhoPrimario));
                            }
                        } else {
                            colors.add(getResources().getColor(R.color.laranjaPrimario));
                        }

                        prevVisualizacoes = visualizacoes;
                        index++;
                    }

                    BarDataSet barDataSet = new BarDataSet(entries, "Visualizações dos seus imóveis");
                    barDataSet.setValueTextSize(14f);
                    barDataSet.setColors(colors); // Aplica as cores personalizadas

                    BarData barData = new BarData(barDataSet);
                    barChart.setData(barData);

                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);

                    String[] meses = new String[] {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(meses));

                    YAxis yAxisLeft = barChart.getAxisLeft();
                    yAxisLeft.setAxisMinimum(0f);

                    YAxis yAxisRight = barChart.getAxisRight();
                    yAxisRight.setEnabled(false);

                    Description description = new Description();
                    description.setText("Visualizações do Imóvel");
                    barChart.setDescription(description);

                    barChart.getDescription().setEnabled(false);
                    barChart.animateY(1000);
                });
    }

    public void pegarViews(View view){
        perfil = view.findViewById(R.id.perfil);
        nomeUsu = view.findViewById(R.id.nomeUsu);
        porcentagem = view.findViewById(R.id.porcentagem);
        qntUsuarios = view.findViewById(R.id.qntUsuarios);
        barChart = view.findViewById(R.id.barChart);
    }
}