package net.alcuria.review.calc;

import android.os.Parcel;
import android.os.Parcelable;

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
public class LeechSubject implements Parcelable {

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

    public static final Creator<LeechSubject> CREATOR = new Creator<LeechSubject>() {
        @Override
        public LeechSubject createFromParcel(Parcel in) {
            return new LeechSubject(in);
        }

        @Override
        public LeechSubject[] newArray(int size) {
            return new LeechSubject[size];
        }
    };

    protected LeechSubject(Parcel in) {
        id = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }

    public String getComponentSubjects() {
        return "Components";
//        return subject.componentSubjectIds... fetch from component map;
    }
}
