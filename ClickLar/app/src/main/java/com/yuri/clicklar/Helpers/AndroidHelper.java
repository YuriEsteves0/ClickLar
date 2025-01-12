package com.yuri.clicklar.Helpers;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class AndroidHelper {
    public static void trocarActivity(Context context, Class <?> intentFinal){
        Intent intent = new Intent(context, intentFinal);
        context.startActivity(intent);
    }
}
