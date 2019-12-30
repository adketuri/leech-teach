package net.alcuria.review.http.models;

import com.google.gson.annotations.SerializedName;

/**
 * Wrapper for a single subject
 */
public class Subject {

    @SerializedName("id")
    public int id;

    @SerializedName("object")
    public String object;

    @SerializedName("data")
    public SubjectData data;

    public Subject() {
    }

    public Subject(int id, SubjectData data) {
        this.id = id;
        this.data = data;
    }
}
