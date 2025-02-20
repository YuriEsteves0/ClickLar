package com.yuri.clicklar.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetImageResponse {
    @SerializedName("success")
    private String success;

    @SerializedName("message")
    private String message;

    @SerializedName("images")
    private List<ImageList> images;

    public String getSuccess() {
        return success;
    }

    public void setSucess(String sucess) {
        this.success = sucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ImageList> getImages() {
        return images;
    }

    public void setImages(List<ImageList> images) {
        this.images = images;
    }

    public class ImageList{
        @SerializedName("image_url")
        private String image_url;

        public ImageList() {
        }

        public ImageList(String image_url) {
            this.image_url = image_url;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
