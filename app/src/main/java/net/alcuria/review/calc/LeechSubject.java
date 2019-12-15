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

    public String getReading() {
        return subject.characters;
    }

    public String getMeaning() {
        return subject.meanings.get(0).meaning;
    }
}
