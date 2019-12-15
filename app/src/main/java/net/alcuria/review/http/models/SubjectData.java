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

    @SerializedName("character_images")
    public List<CharacterImage> characterImages;

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "<SubjectData slug=%s>", slug);
    }

}
