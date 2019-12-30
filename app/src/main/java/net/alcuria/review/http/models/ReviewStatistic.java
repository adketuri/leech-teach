package net.alcuria.review.http.models;

import com.google.gson.annotations.SerializedName;

/**
 * Wrapper for a single review statistic.
 *
 * @author Andrew Keturi
 */
public class ReviewStatistic {

    @SerializedName("id")
    public int id;

    @SerializedName("object")
    public String object;

    @SerializedName("data")
    public ReviewStatisticData data;

    public ReviewStatistic() {
    }

    public ReviewStatistic(int id, ReviewStatisticData data) {
        this.id = id;
        this.data = data;
    }
}
