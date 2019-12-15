package net.alcuria.review.http.models;

import com.google.gson.annotations.SerializedName;

public class CharacterImage {

    @SerializedName("url")
    public String url;

    @SerializedName("content_type")
    public String contentType;
}
