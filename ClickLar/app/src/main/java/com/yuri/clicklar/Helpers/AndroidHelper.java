package com.yuri.clicklar.Helpers;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.telecom.Call;
import android.util.Base64;
import android.util.Log;
import android.view.PixelCopy;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.request.Request;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AndroidHelper {
    private static final String BASE_URL = "https://www.yuriesteves.x-br.com/";
    private static Retrofit retrofit;

    public static void trocarActivity(Context context, Class <?> intentFinal){
        Intent intent = new Intent(context, intentFinal);
        context.startActivity(intent);
    }

    public static Bitmap aplicarBlurImagem(Context context, Bitmap image, float blurRadius){
        RenderScript rs = RenderScript.create(context);

        // Criar a entrada e saída do bitmap
        Bitmap outputBitmap = image.copy(image.getConfig(), true);
        Allocation input = Allocation.createFromBitmap(rs, image);
        Allocation output = Allocation.createFromBitmap(rs, outputBitmap);

        // Aplicar o efeito de blur
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, input.getElement());
        blur.setRadius(blurRadius); // Define o raio do blur (1 a 25)
        blur.setInput(input);
        blur.forEach(output);

        // Copiar o resultado para o bitmap de saída
        output.copyTo(outputBitmap);

        // Liberar o RenderScript
        rs.destroy();

        return outputBitmap;
    }

    public static void mostrarMensagem(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String encodeImageToBase64(Context context, int drawableRes) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableRes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);

    }

    public static Bitmap decodeBase64ToBitmap(String base64Image) {
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
