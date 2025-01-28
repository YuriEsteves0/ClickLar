package com.yuri.clicklar.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.Model.Seguidores;
import com.yuri.clicklar.Model.Usuario;
import com.yuri.clicklar.R;

public class PerfilFragment extends Fragment {

    private FirebaseAuth auth = FirebaseHelper.getAuth();
    private FirebaseUser user = auth.getCurrentUser();
    private FirebaseFirestore firestore = FirebaseHelper.getFirestore();
    private ImageView colecao1, colecao2, colecao3, config, perfilFoto;
    private TextView nomeUsuario, tipoUsuario, descricaoPerfil, dataCriacao, qntSeguidores, qntSeguindo;
    private Button seguirBtn, mensagemBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        pegarViews(view);
        pegarDadosUsu();
        configFotoColecao();

        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                ActivityHelper.fecharTodasActivities();
            }
        });

        return view;
    }


    public void setarDadosUsu(Usuario usuario){
        nomeUsuario.setText(usuario.getNome());
        tipoUsuario.setText(usuario.getNivelUsuario());
        descricaoPerfil.setText(usuario.getDescricaoPerfil());
        dataCriacao.setText(usuario.getDataCriacao());
        qntSeguidores.setText("0");
        qntSeguindo.setText("0");
        Glide.with(getContext()).load(R.drawable.perfil).transform(new CenterCrop()).into(perfilFoto);

    }
    public void pegarDadosUsu() {
        Log.d("TAG", "pegarDadosUsu: " + user.getUid());
        firestore.collection("Usuarios").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    if (documentSnapshot.exists()) {
                        Usuario usuario = documentSnapshot.toObject(Usuario.class);

                        setarDadosUsu(usuario); // Atualiza a interface com os dados do usuário
                        botoes(usuario); // Agora que o 'usuario' está inicializado, podemos chamar botoes()
                    } else {
                        Log.e("TAG", "Documento não encontrado para o UID: " + user.getUid());
                    }
                } else {
                    Log.e("TAG", "Erro ao acessar Firestore: ", task.getException());
                }
            }
        });
    }

    public void botoes(Usuario usuario) {
        if(usuario != null){
            if(usuario.getIdUsu() != null){
                if (usuario.getIdUsu().equals(user.getUid())) {
                    seguirBtn.setBackgroundColor(Color.parseColor("#D9D9D9"));
                    seguirBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                } else {
                    seguirBtn.setBackgroundColor(Color.parseColor("#E2A025"));
                    seguirBtn.setTextColor(Color.parseColor("#FFFFFFFF"));
                }

                seguirBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!usuario.getIdUsu().equals(user.getUid())) {
                            String idSeguidores = usuario.getIdUsu() + "_" + user.getUid();
                            Seguidores seguidores = new Seguidores(idSeguidores, usuario.getIdUsu(), user.getUid());
                            firestore.collection("Usuarios").document(usuario.getIdUsu()).collection("Seguidores").document(usuario.getIdUsu()).set(seguidores).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("SEGUIDORES", "onComplete: ----------------------------------");
                                        Log.d("SEGUIDORES", "onComplete: SEGUIDORES CRIADO COM SUCESSO!");
                                        Log.d("SEGUIDORES", "onComplete: SEGUIDOR " + seguidores.getUidUsuarioSeguidor() + " ADICIONADO COM SUCESSO!");
                                        Log.d("SEGUIDORES", "onComplete: ----------------------------------");
                                    }
                                }
                            });
                        }
                    }

                });
            }else{
                Log.e("BOTÕES", "ID do usuario está nulo.");
            }
        } else {
            Log.e("BOTÕES", "Usuário está nulo.");
        }
    }


    public void configFotoColecao(){
        Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.houseplaceholder);
        Bitmap blur = AndroidHelper.aplicarBlurImagem(getContext(), original, 25);
        colecao1.setImageBitmap(blur);
        colecao2.setImageBitmap(blur);
        colecao3.setImageBitmap(blur);
    }

    public void pegarViews(View view){
        colecao1 = view.findViewById(R.id.colecao1);
        colecao2 = view.findViewById(R.id.colecao2);
        colecao3 = view.findViewById(R.id.colecao3);
        config = view.findViewById(R.id.config);
        nomeUsuario = view.findViewById(R.id.nomeUsuario);
        tipoUsuario = view.findViewById(R.id.tipoUsuario);
        descricaoPerfil = view.findViewById(R.id.descricaoPerfil);
        dataCriacao = view.findViewById(R.id.dataCriacao);
        qntSeguidores = view.findViewById(R.id.qntSeguidores);
        qntSeguindo = view.findViewById(R.id.qntSeguindo);
        seguirBtn = view.findViewById(R.id.seguirBtn);
        mensagemBtn = view.findViewById(R.id.mensagemBtn);
        perfilFoto = view.findViewById(R.id.perfilFoto);
    }
}