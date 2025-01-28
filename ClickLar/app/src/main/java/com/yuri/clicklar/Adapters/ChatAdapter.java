package com.yuri.clicklar.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.Model.Mensagens;
import com.yuri.clicklar.R;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>{

    private Context context;
    private List<Mensagens> mensagensList = new ArrayList<>();

    public ChatAdapter(Context context, List<Mensagens> mensagensList) {
        this.context = context;
        this.mensagensList = mensagensList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mensagens_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Mensagens mensagens = mensagensList.get(position);

        String uidUsu = mensagens.getUidUsu();
        String currentUid = FirebaseHelper.getCurrentUser().getUid();

        if (uidUsu != null && uidUsu.equals(currentUid)) {
            holder.mensagemUsuLaranja.setText(mensagens.getMensagem());
            holder.mensagemUsuLaranja.setVisibility(View.VISIBLE);
            holder.mensagemUsuVerde.setVisibility(View.GONE);
        } else {
            holder.mensagemUsuVerde.setText(mensagens.getMensagem());
            holder.mensagemUsuVerde.setVisibility(View.VISIBLE);
            holder.mensagemUsuLaranja.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return mensagensList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mensagemUsuVerde, mensagemUsuLaranja;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mensagemUsuVerde = itemView.findViewById(R.id.mensagemUsuVerde);
            mensagemUsuLaranja = itemView.findViewById(R.id.mensagemUsuLaranja);
        }

    }

}
