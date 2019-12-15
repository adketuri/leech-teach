package net.alcuria.review.calc;

import net.alcuria.review.http.models.ReviewStatisticData;
import net.alcuria.review.http.models.SubjectData;

/**
 * Subject data with associated review statistics.
 */
public class LeechSubject {

    private final ReviewStatisticData review;
    private final SubjectData subject;

    public LeechSubject(ReviewStatisticData review, SubjectData subject) {
        this.review = review;
        this.subject = subject;
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
