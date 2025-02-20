package com.yuri.clicklar.Interfaces;

import com.yuri.clicklar.Helpers.RetroFitClient;
import com.yuri.clicklar.Model.GetImageResponse;
import com.yuri.clicklar.Model.UploadResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {
    @Multipart
    @POST("upload.php")
    Call<UploadResponse> uploadImage(
            @Part MultipartBody.Part image,
            @Part("user_id")RequestBody userId,
            @Part("folder_trg") RequestBody folter_trg
    );

    @GET("get_images.php")
    Call<GetImageResponse> getImage(
            @Query("user_id") String userId
    );
}
