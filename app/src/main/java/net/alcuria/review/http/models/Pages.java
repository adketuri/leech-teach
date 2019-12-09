package net.alcuria.review.http.models;

import com.google.gson.annotations.SerializedName;

public class Pages {

    @SerializedName("per_page")
    public int perPage;

    @SerializedName("next_url")
    public String nextUrl;

    @SerializedName("previous_url")
    public String previousUrl;
}
