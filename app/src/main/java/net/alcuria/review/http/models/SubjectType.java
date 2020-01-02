package net.alcuria.review.http.models;

/**
 * Various types of study subjects.
 *
 * @author Andrew Keturi
 */
public enum SubjectType {
    VOCABULARY, KANJI, RADICAL;

    public static SubjectType from(String subjectType) {
        for (SubjectType type : SubjectType.values()) {
            if (type.name().equalsIgnoreCase(subjectType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No subject type for " + subjectType);
    }
}
