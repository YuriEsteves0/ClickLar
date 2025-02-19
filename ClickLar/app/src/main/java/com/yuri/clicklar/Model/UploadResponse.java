package com.yuri.clicklar.Model;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {
    @SerializedName("success")
    private boolean sucess;

    @SerializedName("message")
    private String message;

    @SerializedName("image_url")
    private String imageUrl;

    public UploadResponse() {
    }

    public UploadResponse(boolean sucess, String message, String imageUrl) {
        this.sucess = sucess;
        this.message = message;
        this.imageUrl = imageUrl;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
