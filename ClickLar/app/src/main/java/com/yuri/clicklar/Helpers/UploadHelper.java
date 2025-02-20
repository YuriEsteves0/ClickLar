package com.yuri.clicklar.Helpers;

import android.content.Context;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yuri.clicklar.Interfaces.APIService;
import com.yuri.clicklar.Model.UploadResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadHelper {
    private String TAG = "UPLOAD HELPER";
    public void uploadImage(Context context, Uri uriImage, String uidUsu, String folderTrg){
        File file = getFileFromUri(context, uriImage, uidUsu);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.yuriesteves.x-br.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService apiService = retrofit.create(APIService.class);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), uidUsu);
        RequestBody folderTrgReqBody = RequestBody.create(MediaType.parse("text/plain"), folderTrg);

        Call<UploadResponse> call = apiService.uploadImage(body, userId, folderTrgReqBody);

        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                if(response.isSuccessful()){
                    // RESPOSTA DO SERVIDOR
                    UploadResponse uploadResponse = response.body();

                    Log.d(TAG, "SUCESSO AO ENVIAR ARQUIVO: " + uploadResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                    Log.d(TAG, "ERRO AO ENVIAR ARQUIVO: " + t.getMessage());
            }
        });
    }

    public File getFileFromUri(Context context, Uri uriImage, String uidUsu){
        File file = null;

        try{
            // ABRIR LEITURA DE BYTES DA IMAGEM DE ACORDO COM A URI --
            InputStream inputStream = context.getContentResolver().openInputStream(uriImage);

            if(inputStream == null){
                Log.e(TAG, "getFileFromUri: ERRO AO SELECIONAR IMAGEM");
                return null;
            }

            // CRIAÇÃO DO ARQUIVO NO CACHE DO APLICATIVO
            file = new File(context.getCacheDir(), "ProfPic_usuario_" + uidUsu);
            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int bytesLidos;
            while((bytesLidos = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, bytesLidos);
            }

            inputStream.close();
            outputStream.close();

            return file;
        }catch(Exception e){
            Log.e(TAG, "ERRO: " + e.getMessage());
        }
        return file;
    }
}
