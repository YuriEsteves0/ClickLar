package com.yuri.clicklar.Interfaces;

import com.yuri.clicklar.Helpers.RetroFitClient;
import com.yuri.clicklar.Model.UploadResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService {
    @Multipart
    @POST("upload.php")
    Call<UploadResponse> uploadImage(
            @Part MultipartBody.Part image,
            @Part("user_id")RequestBody userId,
            @Part("folder_trg") RequestBody folter_trg
    );
}
