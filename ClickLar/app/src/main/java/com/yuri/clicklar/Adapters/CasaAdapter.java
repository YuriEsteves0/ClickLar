package com.yuri.clicklar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuri.clicklar.Fragments.PerfilOutroUsuarioFragment;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Model.Casa;
import com.yuri.clicklar.R;

import java.util.List;

public class CasaAdapter extends RecyclerView.Adapter<CasaAdapter.MyViewHolder>{

    private List<Casa> casaList;
    private Context context;
    private FragmentManager fragmentManager;

    public CasaAdapter(List<Casa> casaList, Context context, FragmentManager fragmentManager) {
        this.casaList = casaList;
        this.context = context;
        this.fragmentManager = fragmentManager;
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

        if (casa.getPromocao().equals("0")) { //NÃO TEM PROMOÇÃO
            holder.promocao.setVisibility(View.GONE);
            holder.precoCasaAntigo.setVisibility(View.GONE);
            holder.precoCasa.setVisibility(View.VISIBLE);
            holder.precoCasa.setText(casa.getPrecoCasa());
        } else {
            holder.promocao.setVisibility(View.VISIBLE);
            holder.precoCasaAntigo.setVisibility(View.VISIBLE);
            holder.precoCasa.setVisibility(View.VISIBLE);
            holder.precoCasaAntigo.setText(casa.getPrecoCasaAntigo());
            holder.precoCasa.setText(casa.getPrecoCasa());
            holder.promocao.setText(casa.getPromocao());
        }

        holder.qntQuarto.setText(casa.getQntQuarto());
        holder.qntArea.setText(casa.getQntArea());
        holder.qntGaragem.setText(casa.getQntGaragem());
        holder.qntBanheiro.setText(casa.getQntBanheiro());
        holder.localizacao.setText(casa.getLocalizacao());
        holder.bairro.setText(casa.getBairro());

        //GARAGEM
        Glide.with(context).load(R.drawable.garagemicone).placeholder(R.drawable.houseplaceholder).into(holder.garagemIcone);
        //TAMANHO CASA
        Glide.with(context).load(R.drawable.tamanhocasaicone).placeholder(R.drawable.houseplaceholder).into(holder.tamanhoCasaIcone);
        //CAMA
        Glide.with(context).load(R.drawable.camaicone).placeholder(R.drawable.houseplaceholder).into(holder.camaIcone);
        //BANHEIRO
        Glide.with(context).load(R.drawable.banheiroicone).placeholder(R.drawable.houseplaceholder).into(holder.banheiroIcone);
        //CORACAP
        Glide.with(context).load(R.drawable.coracao).placeholder(R.drawable.houseplaceholder).into(holder.coracao);

        if (casa.getImagemBase64() != null && !casa.getImagemBase64().isEmpty()) {
            String base64Image = casa.getImagemBase64().get(0);
            Bitmap bitmap = AndroidHelper.decodeBase64ToBitmap(base64Image);
            Glide.with(context)
                    .asBitmap()
                    .load(bitmap)
                    .placeholder(R.drawable.houseplaceholder)
                    .into(holder.fotocasa);
        } else {
            Glide.with(context)
                    .load(R.drawable.houseplaceholder)
                    .into(holder.fotocasa);
        }


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uidDono", casa.getUidDono());
                Log.d("TAG", "DONO CASA: " + casa.getUidDono());
                Fragment perfilUsu = new PerfilOutroUsuarioFragment();
                perfilUsu.setArguments(bundle);

                fragmentManager.beginTransaction().replace(R.id.frameLayout, perfilUsu).addToBackStack(null).commit();

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
        private ImageView coracao, garagemIcone, tamanhoCasaIcone, camaIcone, banheiroIcone;
        private TextView precoCasaAntigo;
        private TextView precoCasa;
        private TextView promocao;
        private TextView qntQuarto;
        private TextView qntArea;
        private TextView qntGaragem;
        private TextView qntBanheiro;
        private TextView localizacao;
        private TextView bairro;
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
            bairro = itemView.findViewById(R.id.bairro);
            garagemIcone = itemView.findViewById(R.id.garagemIcone);
            tamanhoCasaIcone = itemView.findViewById(R.id.tamanhoCasaIcone);
            camaIcone = itemView.findViewById(R.id.camaIcone);
            banheiroIcone = itemView.findViewById(R.id.banheiroIcone);
        }
    }
}
