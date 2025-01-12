package com.yuri.clicklar.Helpers;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ActivityHelper {
    public static final List<Activity> activities = new ArrayList<>();

    public static void iniciarActivity(Activity activity){
        Log.d("TROCA DE TELA", "---------------------------------------------------");
        adicionarActivity(activity);
        listarActivities();
        Log.d("TROCA DE TELA", "---------------------------------------------------");
    }

    public static void adicionarActivity(Activity activity){
        if(!activities.contains(activity)){
            activities.add(activity);
        }
    }

    public static void removerActivity(Activity activity){
        activities.remove(activity);
    }

    public static void listarActivities(){
        for(Activity activity : activities){
            Log.d("TAG", "listarActivities: " + activity.getClass().getSimpleName());
        }
    }

    public static void fecharTodasActivities(){
        for(Activity activity : activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
