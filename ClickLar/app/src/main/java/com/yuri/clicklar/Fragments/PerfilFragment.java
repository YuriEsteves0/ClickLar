package com.yuri.clicklar.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.Helpers.UploadHelper;
import com.yuri.clicklar.Interfaces.APIService;
import com.yuri.clicklar.Model.GetImageResponse;
import com.yuri.clicklar.Model.Usuario;
import com.yuri.clicklar.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilFragment extends Fragment {

    private FirebaseAuth auth = FirebaseHelper.getAuth();
    private String TAG = "PERFIL FRAGMENT";
    private FirebaseUser user = auth.getCurrentUser();
    private FirebaseFirestore firestore = FirebaseHelper.getFirestore();
    private ImageView perfilFoto;
    private TextView nomeUsuario, tipoUsuario, descricaoPerfil, dataCriacao;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        pegarViews(view);
        pegarDadosUsu();

        perfilFoto.setOnClickListener(v -> abrirGaleria());

        return view;
    }

    public void abrirGaleria(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: CAIU AQUI" + "REQ CODE: " + requestCode + " RESULT CODE: " + resultCode + " DATA: " + data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri imagemSelecionada = data.getData();
            Log.d(TAG, "URI DA IMAGEM: " + imagemSelecionada);
            Glide.with(getContext()).load(imagemSelecionada).transform(new CenterCrop()).into(perfilFoto);

            UploadHelper uploadHelper = new UploadHelper();
            uploadHelper.uploadImage(getContext(), imagemSelecionada, user.getUid(), "imgperfil");
        }
    }

    public void setarDadosUsu(Usuario usuario) {
        nomeUsuario.setText(usuario.getNome());
        tipoUsuario.setText(usuario.getNivelUsuario());
        descricaoPerfil.setText(usuario.getDescricaoPerfil());
        dataCriacao.setText(usuario.getDataCriacao());
    }

    public void pegarDadosUsu() {
        firestore.collection("Usuarios").document(user.getUid()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Usuario usuario = documentSnapshot.toObject(Usuario.class);
                        setarDadosUsu(usuario);
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Erro ao acessar Firestore", e));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.yuriesteves.x-br.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<GetImageResponse> call = apiService.getImage(user.getUid());

        call.enqueue(new Callback<GetImageResponse>() {
            @Override
            public void onResponse(Call<GetImageResponse> call, Response<GetImageResponse> response) {
                if (response.isSuccessful()) {
                    GetImageResponse imageResponse = response.body();
                    Log.d(TAG, "RESPONSE " + response.toString());

                    Log.d(TAG,  "Usuario: " + user.getUid() + "Status: " + imageResponse.getSuccess() + " Mensagem: " + imageResponse.getMessage() + " Imagens: " + imageResponse.getImages());

                    if (imageResponse != null && "true".equals(imageResponse.getSuccess()) && imageResponse.getImages() != null && !imageResponse.getImages().isEmpty()) {
                        String imagemUrl = imageResponse.getImages().get(0).getImage_url();

                        Log.d(TAG, "Usuario com foto de perfil! " + imageResponse.getMessage() + " IMAGENS: " + imageResponse.getImages() + " URL: " + imagemUrl);

                        Glide.with(getContext()).load(imagemUrl).transform(new CenterCrop()).into(perfilFoto);
                    } else {
                        Log.d(TAG, "Usuario sem foto de perfil! " + (imageResponse != null ? imageResponse.getMessage() : "Resposta nula"));

                        Glide.with(getContext()).load(R.drawable.perfil).transform(new CenterCrop()).into(perfilFoto);
                    }
                } else {
                    Log.d(TAG, "Resposta da API não foi bem-sucedida.");
                }
            }


            @Override
            public void onFailure(Call<GetImageResponse> call, Throwable t) {
                Log.d(TAG, "NÃO FOI POSSÍVEL RETORNAR A IMAGEM " + t.getMessage());
            }
        });

    }

    public void pegarViews(View view) {
        perfilFoto = view.findViewById(R.id.perfilFoto);
        nomeUsuario = view.findViewById(R.id.nomeUsuario);
        tipoUsuario = view.findViewById(R.id.tipoUsuario);
        descricaoPerfil = view.findViewById(R.id.descricaoPerfil);
        dataCriacao = view.findViewById(R.id.dataCriacao);
    }
}
