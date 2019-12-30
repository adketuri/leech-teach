package net.alcuria.review.calc.converter;

import com.google.gson.Gson;

import net.alcuria.review.http.models.ReviewStatisticData;
import net.alcuria.review.http.models.SubjectData;

import androidx.room.TypeConverter;

public class ReviewStatisticTypeConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static ReviewStatisticData jsonToObject(String data) {
        if (data == null) {
            return new ReviewStatisticData();
        }
        return gson.fromJson(data, ReviewStatisticData.class);
    }

    @TypeConverter
    public static String objectToJson(ReviewStatisticData review) {
        return gson.toJson(review);
    }

    @TypeConverter
    public static SubjectData jsonToSubject(String data) {
        if (data == null) {
            return new SubjectData();
        }
        return gson.fromJson(data, SubjectData.class);
    }

    @TypeConverter
    public static String objectToJson(SubjectData review) {
        return gson.toJson(review);
    }

}
