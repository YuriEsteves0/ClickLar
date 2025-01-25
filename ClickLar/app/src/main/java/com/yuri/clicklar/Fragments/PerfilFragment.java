package com.yuri.clicklar.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.R;

public class PerfilFragment extends Fragment {

    private FirebaseAuth auth = FirebaseHelper.getAuth();
    private FirebaseUser user = auth.getCurrentUser();
    private FirebaseFirestore firestore = FirebaseHelper.getFirestore();
    private ImageView colecao1, colecao2, colecao3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        pegarViews(view);
        pegarDadosUsu();
        configFotoColecao();

        return view;
    }

    public void pegarDadosUsu(){
        firestore.collection("usuarios").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

            }
        });
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
    }
}