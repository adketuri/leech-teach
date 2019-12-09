package net.alcuria.review.http.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData<T> {

    @SerializedName("pages")
    public Pages pages;

    @SerializedName("total_count")
    public int totalCount;

    @SerializedName("data")
    public List<T> data;

}
