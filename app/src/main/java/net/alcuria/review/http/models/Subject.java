package net.alcuria.review.http.models;

import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("id")
    public int id;

    @SerializedName("object")
    public String object;

    @SerializedName("data")
    public SubjectData data;

}
