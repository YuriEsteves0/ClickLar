package com.yuri.clicklar.Activities;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yuri.clicklar.Adapters.ChatAdapter;
import com.yuri.clicklar.Helpers.ActivityHelper;
import com.yuri.clicklar.Helpers.AndroidHelper;
import com.yuri.clicklar.Helpers.FirebaseHelper;
import com.yuri.clicklar.Helpers.SoftInputAssist;
import com.yuri.clicklar.Model.Mensagens;
import com.yuri.clicklar.Model.Usuario;
import com.yuri.clicklar.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView RV;
    private FirebaseFirestore firestore = FirebaseHelper.getFirestore();
    private FirebaseUser user = FirebaseHelper.getCurrentUser();
    private String uidUsuRecebedor;
    private TextView textInput, nomeUsu;
    private ImageView enviar, voltarBTN, iconeBtn;
    private List<Mensagens> mensagensList = new ArrayList<>();
    private DatabaseReference reference = FirebaseHelper.getReference();

    // Instância de SoftInputAssist
    private SoftInputAssist softInputAssist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        // Configurar SoftInputAssist
        softInputAssist = new SoftInputAssist(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityHelper.iniciarActivity(this);
        pegarViews();
        receberDados();


        configAdapter();
        configTopBar();
        botoes();
        ajustarRV();
        pegarMensagensAnteriores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Retomar SoftInputAssist
        if (softInputAssist != null) softInputAssist.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pausar SoftInputAssist
        if (softInputAssist != null) softInputAssist.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Destruir SoftInputAssist
        if (softInputAssist != null) softInputAssist.onDestroy();
    }

    public void ajustarRV() {
        RV.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                RV.getWindowVisibleDisplayFrame(r);

                int screenHeight = RV.getRootView().getHeight();
                int alturaTeclado = screenHeight - r.bottom;

                if (alturaTeclado > screenHeight * 0.15 && mensagensList.size() > 0) {
                    RV.smoothScrollToPosition(mensagensList.size() - 1);
                    RV.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    public void configTopBar() {
        firestore.collection("Usuarios").document(uidUsuRecebedor).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        Usuario usuario = documentSnapshot.toObject(Usuario.class);

                        nomeUsu.setText(usuario.getNome());
                        voltarBTN.setOnClickListener(v -> {
                            ActivityHelper.removerActivity(ChatActivity.this);
                            finish();
                        });

                        iconeBtn.setOnClickListener(v ->
                                AndroidHelper.mostrarMensagem(getApplicationContext(), "Funcionalidade em desenvolvimento!")
                        );
                    }
                }
            }
        });
    }

    public void botoes() {
        enviar.setOnClickListener(v -> {
            Log.d("TAG", "botoes: to dentro do botao");
            if (!textInput.getText().toString().isEmpty()) {
                Log.d("TAG", "botoes: to dentro do if");

                String idChat = user.getUid() + "_" + uidUsuRecebedor;
                String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                String horaAtual = new SimpleDateFormat("HH:mm").format(new Date());

                Mensagens mensagens = new Mensagens(textInput.getText().toString(), FirebaseHelper.getCurrentUser().getUid(), dataAtual, horaAtual);

                reference.child("Chats").child(idChat).push().setValue(mensagens).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mensagensList.add(mensagens);
                        RV.getAdapter().notifyDataSetChanged();
                        RV.smoothScrollToPosition(mensagensList.size() - 1);
                        textInput.setText("");
                    }
                });
            }
        });
    }

    public void configAdapter() {
        ChatAdapter adapter = new ChatAdapter(getApplicationContext(), mensagensList);
        RV.setAdapter(adapter);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));
        RV.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void receberDados() {
        Intent intent = getIntent();
        uidUsuRecebedor = intent.getStringExtra("uidUsuRecebedor");
    }

    public void pegarMensagensAnteriores() {
        String idChat = user.getUid() + "_" + uidUsuRecebedor;
        reference.child("Chats").child(idChat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mensagensList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String mensagem = dataSnapshot.child("mensagem").getValue(String.class);
                    String uidUsu = dataSnapshot.child("uidUsu").getValue(String.class);
                    String data = dataSnapshot.child("data").getValue(String.class);
                    String hora = dataSnapshot.child("hora").getValue(String.class);

                    Mensagens mensagens = new Mensagens(mensagem, uidUsu, data, hora);

                    mensagensList.add(mensagens);
                }
                RV.getAdapter().notifyDataSetChanged();

                if (mensagensList.size() > 0) {
                    RV.scrollToPosition(mensagensList.size() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }

    public void pegarViews() {
        textInput = findViewById(R.id.textInput);
        enviar = findViewById(R.id.enviar);
        RV = findViewById(R.id.RV);
        voltarBTN = findViewById(R.id.voltarBTN);
        iconeBtn = findViewById(R.id.iconeBtn);
        nomeUsu = findViewById(R.id.nomeUsu);
    }
}
