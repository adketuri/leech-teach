package net.alcuria.review.http.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectData {

    @SerializedName("level")
    public int level;

    @SerializedName("slug")
    public String slug;

    @SerializedName("document_url")
    public String documentUrl;

    @SerializedName("characters")
    public String characters;

    @SerializedName("character_images")
    public List<CharacterImage> characterImages;

}
