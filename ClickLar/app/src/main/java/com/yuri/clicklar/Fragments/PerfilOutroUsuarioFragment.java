package com.yuri.clicklar.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yuri.clicklar.Activities.ChatActivity;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.Model.Seguidores;
import com.yuri.clicklar.Model.Usuario;
import com.yuri.clicklar.R;

public class PerfilOutroUsuarioFragment extends Fragment {

    private TextView nomeUsuario, tipoUsuario, descricaoPerfil, dataCriacao, qntSeguidores, qntSeguindo;
    private Button seguirBtn, mensagemBtn;
    private FirebaseUser user = FirebaseHelper.getAuth().getCurrentUser();
    private ImageView perfilFoto, voltarBtn, report;
    private FirebaseFirestore firestore = FirebaseHelper.getFirestore();
    private boolean seguindo = false;
    private String uidUsu;
    private HomeFragment home = new HomeFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_outro_usuario, container, false);

        pegarViews(view);
        pegarDadosUsu();

        mensagemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("uidUsuRecebedor", uidUsu);
                getContext().startActivity(intent);
            }
        });

        voltarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayout, home);
                transaction.commit();
            }
        });

        return view;
    }

    public void pegarDadosUsu() {
        uidUsu = getArguments().getString("uidDono");
        Log.d("TAG", "pegarDadosUsu: " + uidUsu);

        firestore.collection("Usuarios").document(uidUsu).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Usuario usuario = documentSnapshot.toObject(Usuario.class);
                        setarDadosUsuario(usuario);
                        botoes(usuario);
                    }
                }
            }
        });
    }

    public void verifSeguindo() {
        String idSeguidores = uidUsu + "_" + user.getUid();

        firestore.collection("Usuarios").document(uidUsu)
                .collection("Seguidores").document(idSeguidores).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            seguindo = document.exists();
                            atualizarBotaoSeguir();
                        } else {
                            Log.e("TAG", "Erro ao verificar seguidores", task.getException());
                        }
                    }
                });
    }

    public void atualizarBotaoSeguir() {
        if (seguindo) {
            seguirBtn.setBackgroundColor(Color.parseColor("#D9D9D9"));
            seguirBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
            seguirBtn.setText("Seguindo");
        } else {
            seguirBtn.setBackgroundColor(Color.parseColor("#E2A025"));
            seguirBtn.setTextColor(Color.parseColor("#FFFFFF"));
            seguirBtn.setText("Seguir");
        }

        seguirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seguindo) {
                    removerSeguidor();
                } else {
                    adicionarSeguidor();
                }
            }
        });
    }

    public void adicionarSeguidor() {
        String idSeguidores = uidUsu + "_" + user.getUid();
        Seguidores seguidores = new Seguidores(idSeguidores, uidUsu, user.getUid());

        firestore.collection("Usuarios").document(uidUsu)
                .collection("Seguidores").document(idSeguidores)
                .set(seguidores)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Seguidor adicionado com sucesso!");
                            seguindo = true;
                            atualizarBotaoSeguir();
                        } else {
                            Log.e("TAG", "Erro ao adicionar seguidor", task.getException());
                        }
                    }
                });
    }

    public void removerSeguidor() {
        String idSeguidores = uidUsu + "_" + user.getUid();

        firestore.collection("Usuarios").document(uidUsu)
                .collection("Seguidores").document(idSeguidores)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Seguidor removido com sucesso!");
                            seguindo = false;
                            atualizarBotaoSeguir();
                        } else {
                            Log.e("TAG", "Erro ao remover seguidor", task.getException());
                        }
                    }
                });
    }

    public void setarDadosUsuario(Usuario usuario) {
        nomeUsuario.setText(usuario.getNome());
        tipoUsuario.setText(usuario.getNivelUsuario());
        descricaoPerfil.setText(usuario.getDescricaoPerfil());
        dataCriacao.setText(usuario.getDataCriacao());
        qntSeguidores.setText("0");
        qntSeguindo.setText("0");
        Glide.with(getContext()).load(R.drawable.perfil).transform(new CenterCrop()).into(perfilFoto);
        verifSeguindo();
    }

    public void botoes(Usuario usuario) {
        if (usuario != null && !usuario.getIdUsu().equals(user.getUid())) {
            seguirBtn.setBackgroundColor(Color.parseColor("#E2A025"));
            seguirBtn.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            seguirBtn.setVisibility(View.GONE); // Oculta o botão para o próprio perfil
        }
    }

    public void pegarViews(View view) {
        nomeUsuario = view.findViewById(R.id.nomeUsuario);
        tipoUsuario = view.findViewById(R.id.tipoUsuario);
        descricaoPerfil = view.findViewById(R.id.descricaoPerfil);
        dataCriacao = view.findViewById(R.id.dataCriacao);
        qntSeguidores = view.findViewById(R.id.qntSeguidores);
        qntSeguindo = view.findViewById(R.id.qntSeguindo);
        seguirBtn = view.findViewById(R.id.seguirBtn);
        mensagemBtn = view.findViewById(R.id.mensagemBtn);
        voltarBtn = view.findViewById(R.id.voltarBtn);
        perfilFoto = view.findViewById(R.id.perfilFoto);
        report = view.findViewById(R.id.report);
    }
}
