package com.yuri.clicklar.Helpers;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AndroidHelper {
    public static void trocarActivity(Context context, Class <?> intentFinal){
        Intent intent = new Intent(context, intentFinal);
        context.startActivity(intent);
    }

    public static void mostrarMensagem(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
