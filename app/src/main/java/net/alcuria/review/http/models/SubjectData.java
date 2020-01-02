package net.alcuria.review.http.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;

public class SubjectData {

    @SerializedName("level")
    public int level;

    @SerializedName("slug")
    public String slug;

    @SerializedName("document_url")
    public String documentUrl;

    @SerializedName("characters")
    public String characters;

    @SerializedName("meanings")
    public List<Meaning> meanings;

    @SerializedName("readings")
    public List<Reading> readings;

    @SerializedName("character_images")
    public List<CharacterImage> characterImages;

    @SerializedName("component_subject_ids")
    public List<Integer> componentSubjectIds;

    @SerializedName("amalgamation_subject_ids")
    public List<Integer> amalgamationSubjectIds;

    @SerializedName("meaning_mnemonic")
    public String meaningMnemonic;

    @SerializedName("reading_mnemonic")
    public String readingMnemonic;

    @SerializedName("meaning_hint")
    public String meaningHint;

    @SerializedName("reading_hint")
    public String readingHint;

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "<SubjectData slug=%s>", slug);
    }

}
