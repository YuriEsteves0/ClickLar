package com.yuri.clicklar.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuri.clicklar.Model.Casa;
import com.yuri.clicklar.R;

import java.util.ArrayList;
import java.util.List;

public class CasaDashboardAdapter extends RecyclerView.Adapter<CasaDashboardAdapter.MyViewHolder> {

    private List<Casa> casaList = new ArrayList<>();
    private Context context;

    public CasaDashboardAdapter(List<Casa> casaList, Context context) {
        this.casaList = casaList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_model_dashboard, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Casa casa = casaList.get(position);

        holder.nomeCasa.setText(casa.getNomeCasa());
        holder.statusCasa.setText(casa.getStatusCasa());
        holder.precoCasa.setText(casa.getPrecoCasa());
        holder.qntQrts.setText(casa.getQntQuarto());
        holder.qntM2.setText(casa.getQntArea());
        holder.qntGaragem.setText(casa.getQntGaragem());
        holder.qntBanheiro.setText(casa.getQntBanheiro());

        if(holder.valorCondominio.equals("0")){
            holder.valorCondominio.setVisibility(View.GONE);
            holder.iconeCondominio.setVisibility(View.GONE);
        }else{
            holder.valorCondominio.setText("R$ " + casa.getValorCondominio());
        }

        holder.excluir.setOnClickListener(v -> excluir());
        holder.editar.setOnClickListener(v -> editar());

        holder.fotoCasa.setImageResource(R.drawable.casafoda);
    }

    @Override
    public int getItemCount() {
        return casaList.size();
    }

    public void editar(){

    }

    public void excluir(){

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeCasa;
        public TextView statusCasa;
        public TextView precoCasa;
        public TextView qntQrts;
        public TextView qntM2;
        public TextView qntGaragem;
        public TextView qntBanheiro;
        public TextView valorCondominio;
        public Button excluir;
        public Button editar;
        public Button visualizar;
        private ImageView fotoCasa, iconeCondominio;

        public MyViewHolder(View itemView) {
            super(itemView);
            nomeCasa = itemView.findViewById(R.id.nomeCasa);
            statusCasa = itemView.findViewById(R.id.statusCasa);
            precoCasa = itemView.findViewById(R.id.precoCasa);
            qntQrts = itemView.findViewById(R.id.qntQrts);
            qntM2 = itemView.findViewById(R.id.qntM2);
            qntGaragem = itemView.findViewById(R.id.qntGaragem);
            qntBanheiro = itemView.findViewById(R.id.qntBanheiro);
            valorCondominio = itemView.findViewById(R.id.valorCondominio);
            iconeCondominio= itemView.findViewById(R.id.iconeCondominio);
            excluir = itemView.findViewById(R.id.excluir);
            editar = itemView.findViewById(R.id.editar);
            fotoCasa = itemView.findViewById(R.id.fotoCasa);
        }
    }
}
