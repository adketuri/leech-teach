package net.alcuria.review.http.models;

import com.google.gson.annotations.SerializedName;

public class Reading {
    @SerializedName("reading")
    public String reading;

    @SerializedName("primary")
    public boolean primary;

    @SerializedName("accepted_answer")
    public boolean acceptedAnswer;
}
