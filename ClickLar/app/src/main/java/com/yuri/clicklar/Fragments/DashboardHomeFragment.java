package com.yuri.clicklar.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.Interfaces.APIService;
import com.yuri.clicklar.Model.GetImageResponse;
import com.yuri.clicklar.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardHomeFragment extends Fragment {

    private ImageView perfil, seta, voltarBTN;
    private RelativeLayout cardSeguidores;
    private static final String TAG = "DashboardHomeFragment";
    private TextView msgErroChart, nomeUsu, porcentagem, qntUsuarios, valorEstatisticaSeguidores, valorEstatisticaSeguidoresVariada, valorEstatisticaVendas, valorEstatisticaVendasVariada, valorEstatisticaConversa, valorEstatisticaConversaVariada, valorEstatisticaFavoritados, valorEstatisticaFavoritadosVariada;
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
        configDadosUsu();
        configCards();

        voltarBTN.setOnClickListener(v -> {
            getActivity().finish();
        });

        return view;
    }

    public void configCards(){
        userRef.collection("Usuarios").document(userID).collection("Seguidores").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int qntRetorno = queryDocumentSnapshots.size();
                valorEstatisticaSeguidores.setText(String.valueOf(qntRetorno));
            }
        });
    }

    public void configDadosUsu() {
        userRef.collection("Usuarios").document(userID).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String nome = documentSnapshot.getString("nome");
                nomeUsu.setText(nome);

                String dataAtualFormatada, dataAnteriorFormatada;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate dataAtual = LocalDate.now();
                    dataAtualFormatada = String.format("%04d-%02d", dataAtual.getYear(), dataAtual.getMonthValue());
                    LocalDate dataAnterior = dataAtual.minusMonths(1);
                    dataAnteriorFormatada = String.format("%04d-%02d", dataAnterior.getYear(), dataAnterior.getMonthValue());
                } else {
                    Calendar calendar = Calendar.getInstance();
                    int anoAtual = calendar.get(Calendar.YEAR);
                    int mesAtual = calendar.get(Calendar.MONTH) + 1; // Janeiro é 0, soma 1
                    int anoAnterior = mesAtual == 1 ? anoAtual - 1 : anoAtual;
                    int mesAnterior = mesAtual == 1 ? 12 : mesAtual - 1;

                    dataAtualFormatada = String.format("%04d-%02d", anoAtual, mesAtual);
                    dataAnteriorFormatada = String.format("%04d-%02d", anoAnterior, mesAnterior);
                }

                userRef.collection("Usuarios").document(userID).collection("visHistorico").document(dataAtualFormatada)
                        .get().addOnSuccessListener(documentSnapshotAtual -> {
                            final int[] visualizacoesAtuais = {0};
                            if (documentSnapshotAtual.exists()) {
                                visualizacoesAtuais[0] = documentSnapshotAtual.getLong("visualizacoes").intValue();
                                qntUsuarios.setText(String.valueOf(visualizacoesAtuais[0]));
                            }

                            userRef.collection("Usuarios").document(userID).collection("visHistorico").document(dataAnteriorFormatada)
                                    .get().addOnSuccessListener(documentSnapshotAnterior -> {
                                        int visualizacoesAnteriores = 0;
                                        if (documentSnapshotAnterior.exists()) {
                                            visualizacoesAnteriores = documentSnapshotAnterior.getLong("visualizacoes").intValue();
                                        }

                                        // Cálculo da porcentagem de crescimento
                                        if (visualizacoesAnteriores > 0) {
                                            int variacao = ((visualizacoesAtuais[0] - visualizacoesAnteriores) * 100) / visualizacoesAnteriores;
                                            porcentagem.setText(variacao + "%");

                                            // Definir cor baseada no crescimento ou queda
                                            if (variacao >= 0) {
                                                porcentagem.setTextColor(getResources().getColor(R.color.verdePrimario));
                                            } else {
                                                porcentagem.setTextColor(getResources().getColor(R.color.vermelhoPrimario));
                                                seta.setRotation(180);
                                            }
                                        } else {
                                            porcentagem.setVisibility(View.GONE);
                                            seta.setVisibility(View.GONE);
                                        }
                                    });
                        });
            }
        });

        pegarFotoUsu();
    }

    public void pegarFotoUsu(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.yuriesteves.x-br.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<GetImageResponse> call = apiService.getImage(userID);

        call.enqueue(new Callback<GetImageResponse>() {
            @Override
            public void onResponse(Call<GetImageResponse> call, Response<GetImageResponse> response) {
                if(response.isSuccessful()){
                    GetImageResponse imageResponse = response.body();

                    if(imageResponse != null && "true".equals(imageResponse.getSuccess()) && imageResponse.getImages() != null && !imageResponse.getImages().isEmpty()){
                        String urlImagem = imageResponse.getImages().get(0).getImage_url();

                        String urlImagemComTimestamp = urlImagem + "?t=" + System.currentTimeMillis();

                        Glide.with(getContext())
                                .load(urlImagemComTimestamp)
                                .transform(new CenterCrop())
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(perfil);
                    }else{
                        Log.d(TAG, "Usuario sem foto de perfil! " + (imageResponse != null ? imageResponse.getMessage() : "Resposta nula"));
                        Glide.with(getContext()).load(R.drawable.perfil).transform(new CenterCrop()).into(perfil);

                    }
                }
            }

            @Override
            public void onFailure(Call<GetImageResponse> call, Throwable t) {
                Log.d(TAG, "Resposta da API não foi bem-sucedida.");
            }
        });
    }

    public void configChart() {
        userRef.collection("Usuarios").document(userID).collection("visHistorico").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.isEmpty()){
                        msgErroChart.setVisibility(View.VISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                    }else{

                        ArrayList<BarEntry> entries = new ArrayList<>();
                        ArrayList<String> labels = new ArrayList<>();
                        ArrayList<Integer> colors = new ArrayList<>();
                        int index = 0;
                        int prevVisualizacoes = -1;
                        int primeiroMes = 12, primeiroAno = 3000;

                        for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                            String mesAno = doc.getId(); // Exemplo: "2024-02"
                            int visualizacoes = doc.getLong("visualizacoes").intValue();

                            // Descobrir o primeiro mês e ano
                            int ano = Integer.parseInt(mesAno.substring(0, 4));
                            int mes = Integer.parseInt(mesAno.substring(5));

                            if (ano < primeiroAno || (ano == primeiroAno && mes < primeiroMes)) {
                                primeiroAno = ano;
                                primeiroMes = mes;
                            }

                            entries.add(new BarEntry(index, visualizacoes));
                            labels.add(mesAno.substring(5));

                            // Definir cores com base no crescimento
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

                        // Formatar os rótulos do eixo X começando pelo primeiro mês encontrado
                        String[] meses = new String[]{"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
                        ArrayList<String> mesesFormatados = new ArrayList<>();

                        int anoAtual = primeiroAno;
                        int mesAtual = primeiroMes;
                        for (int i = 0; i < labels.size(); i++) {
                            mesesFormatados.add(meses[mesAtual - 1] + "/" + (anoAtual % 100));
                            mesAtual++;
                            if (mesAtual > 12) {
                                mesAtual = 1;
                                anoAtual++;
                            }
                        }

                        xAxis.setValueFormatter(new IndexAxisValueFormatter(mesesFormatados));

                        YAxis yAxisLeft = barChart.getAxisLeft();
                        yAxisLeft.setAxisMinimum(0f);

                        YAxis yAxisRight = barChart.getAxisRight();
                        yAxisRight.setEnabled(false);

                        Description description = new Description();
                        description.setText("Visualizações do Imóvel");
                        barChart.setDescription(description);

                        barChart.getDescription().setEnabled(false);
                        barChart.animateY(1000);
                    }
                });

    }


    public void pegarViews(View view){
        perfil = view.findViewById(R.id.perfil);
        nomeUsu = view.findViewById(R.id.nomeUsu);
        porcentagem = view.findViewById(R.id.porcentagem);
        qntUsuarios = view.findViewById(R.id.qntUsuarios);
        valorEstatisticaSeguidores = view.findViewById(R.id.valorEstatisticaSeguidores);
        valorEstatisticaVendas = view.findViewById(R.id.valorEstatisticaVendas);
        valorEstatisticaVendasVariada = view.findViewById(R.id.valorEstatisticaVendasVariada);
        valorEstatisticaConversa = view.findViewById(R.id.valorEstatisticaConversa);
        valorEstatisticaConversaVariada = view.findViewById(R.id.valorEstatisticaConversaVariada);
        valorEstatisticaFavoritados = view.findViewById(R.id.valorEstatisticaFavoritados);
        valorEstatisticaFavoritadosVariada = view.findViewById(R.id.valorEstatisticaFavoritadosVariada);
        seta = view.findViewById(R.id.seta);
        cardSeguidores = view.findViewById(R.id.cardSeguidores);
        barChart = view.findViewById(R.id.barChart);
        msgErroChart = view.findViewById(R.id.msgErroChart);
        voltarBTN = view.findViewById(R.id.voltarBTN);
    }
}