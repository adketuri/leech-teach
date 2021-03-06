package net.alcuria.review.http.models;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

import androidx.annotation.NonNull;

/**
 * Statistics on a single review.
 *
 * @author Andrew Keturi
 */
public class ReviewStatisticData {

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("subject_id")
    public int subjectId;

    @SerializedName("subject_type")
    public String subjectType;

    @SerializedName("meaning_correct")
    public int meaningCorrect;

    @SerializedName("meaning_incorrect")
    public int meaningIncorrect;

    @SerializedName("meaning_max_streak")
    public int meaningMaxStreak;

    @SerializedName("meaning_current_streak")
    public int meaningCurrentStreak;

    @SerializedName("reading_correct")
    public int readingCorrect;

    @SerializedName("reading_incorrect")
    public int readingIncorrect;

    @SerializedName("reading_max_streak")
    public int readingMaxStreak;

    @SerializedName("reading_current_streak")
    public int readingCurrentStreak;

    @SerializedName("percentage_correct")
    public int percentageCorrect;

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "<ReviewStatisticData subjectId=%d incorrect=%d (RS=%d/%d, MS=%d/%d)>", subjectId, readingIncorrect + meaningIncorrect, readingCurrentStreak, readingMaxStreak, meaningCurrentStreak, meaningMaxStreak);
    }
}
