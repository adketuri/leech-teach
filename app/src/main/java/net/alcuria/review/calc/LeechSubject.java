package net.alcuria.review.calc;

import net.alcuria.review.calc.converter.ReviewStatisticTypeConverter;
import net.alcuria.review.http.models.ReviewStatisticData;
import net.alcuria.review.http.models.SubjectData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/**
 * Subject data with associated review statistics.
 */
@Entity
public class LeechSubject {

    @PrimaryKey
    public int id;
    @TypeConverters(ReviewStatisticTypeConverter.class)
    public ReviewStatisticData review;
    @TypeConverters(ReviewStatisticTypeConverter.class)
    public SubjectData subject;

//    @PrimaryKey
//    public final int id;
//
//    @ColumnInfo(name = "characters")
//    public final String characters;



    public LeechSubject(ReviewStatisticData review, SubjectData subject) {
        this.review = review;
        this.subject = subject;
        this.id = review.subjectId;
        // ---
//        this.id = review.subjectId;
//        this.characters = subject.characters;
    }

    public boolean hasCharacterImage() {
        return subject.characterImages != null && subject.characterImages.size() > 0;
    }

    public String getCharacters() {
        return subject.characters;
    }

    public String getReading() {
        return subject.readings != null ? subject.readings.get(0).reading : "";
    }

    public String getMeaning() {
        return subject.meanings.get(0).meaning;
    }

    public String getCharacterUrl() {
        return subject.characterImages.get(0).url;
    }
}
