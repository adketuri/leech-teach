package net.alcuria.review.http.models;

import com.google.gson.annotations.SerializedName;

/**
 * Data for a specific character meaning.
 *
 * @author Andrew Keturi
 */
public class Meaning {

    @SerializedName("meaning")
    public String meaning;

    @SerializedName("primary")
    public boolean primary;

    @SerializedName("accepted_answer")
    public boolean acceptedAnswer;

}
