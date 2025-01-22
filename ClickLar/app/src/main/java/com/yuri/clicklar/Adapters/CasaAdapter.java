package com.yuri.clicklar.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Model.Casa;
import com.yuri.clicklar.R;

import java.util.List;

public class CasaAdapter extends RecyclerView.Adapter<CasaAdapter.MyViewHolder>{

    private List<Casa> casaList;
    private Context context;

    public CasaAdapter(List<Casa> casaList, Context context) {
        this.casaList = casaList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_model_casa, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Casa casa = casaList.get(position);

        holder.nomeCasa.setText(casa.getNomeCasa());
        holder.statusCasa.setText(casa.getStatusCasa());
        if(casa.getPromocao().equals("0")){
            holder.promocao.setVisibility(View.GONE);
            holder.precoCasaAntigo.setVisibility(View.GONE);
            holder.precoCasa.setText(casa.getPrecoCasa());
        }else{
            holder.precoCasa.setText(casa.getPrecoCasa());
        }

        holder.qntQuarto.setText(casa.getQntQuarto());
        holder.qntArea.setText(casa.getQntArea());
        holder.qntGaragem.setText(casa.getQntGaragem());
        holder.qntBanheiro.setText(casa.getQntBanheiro());
        holder.localizacao.setText(casa.getLocalizacao());

        if (casa.getImagemBase64() != null && !casa.getImagemBase64().isEmpty()) {
            String base64Image = casa.getImagemBase64().get(0);
            Bitmap bitmap = AndroidHelper.decodeBase64ToBitmap(base64Image);
            holder.fotocasa.setImageBitmap(bitmap);
        }else{
            holder.fotocasa.setImageResource(R.drawable.houseplaceholder);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidHelper.mostrarMensagem(context, "Clicou no Card");
            }
        });

        holder.coracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidHelper.mostrarMensagem(context, "Clicou no coracao");
            }
        });
    }

    @Override
    public int getItemCount() {
        return casaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView fotocasa;
        private TextView nomeCasa;
        private TextView statusCasa;
        private ImageView coracao;
        private TextView precoCasaAntigo;
        private TextView precoCasa;
        private TextView promocao;
        private TextView qntQuarto;
        private TextView qntArea;
        private TextView qntGaragem;
        private TextView qntBanheiro;
        private TextView localizacao;
        private CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            fotocasa = itemView.findViewById(R.id.fotocasa);
            nomeCasa = itemView.findViewById(R.id.nomeCasa);
            statusCasa = itemView.findViewById(R.id.statusCasa);
            coracao = itemView.findViewById(R.id.coracao);
            precoCasaAntigo = itemView.findViewById(R.id.precoCasaAntigo);
            precoCasa = itemView.findViewById(R.id.precoCasa);
            promocao = itemView.findViewById(R.id.promocao);
            qntQuarto = itemView.findViewById(R.id.qntQuarto);
            qntArea = itemView.findViewById(R.id.qntArea);
            qntGaragem = itemView.findViewById(R.id.qntGaragem);
            qntBanheiro = itemView.findViewById(R.id.qntBanheiro);
            localizacao = itemView.findViewById(R.id.localizacao);
        }
    }
}
