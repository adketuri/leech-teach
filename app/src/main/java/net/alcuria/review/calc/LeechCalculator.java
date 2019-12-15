package net.alcuria.review.calc;

import android.os.Build;

import net.alcuria.review.http.models.ResponseData;
import net.alcuria.review.http.models.ReviewStatistic;
import net.alcuria.review.http.models.Subject;
import net.alcuria.review.http.models.SubjectData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Contains logic for calculating leeches; depends on both {@link Subject} data and {@link ReviewStatistic} data.
 *
 * @author Andrew Keturi
 */
public class LeechCalculator {

    private final List<Subject> subjects = new ArrayList<>();
    private final List<ReviewStatistic> reviewStatistics = new ArrayList<>();
    private boolean allSubjects;
    private boolean allReviews;
    private final Map<LeechLevel, List<LeechSubject>> leechData = new HashMap<>();
    private final Map<Integer, SubjectData> subjectData = new HashMap<>();

    {
        leechData.put(LeechLevel.LOW, new ArrayList<>());
        leechData.put(LeechLevel.MEDIUM, new ArrayList<>());
        leechData.put(LeechLevel.HIGH, new ArrayList<>());
    }

    public void calculate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            subjects.forEach(subject -> {
                subjectData.put(subject.id, subject.data);
            });
            List<ReviewStatistic> sorted = reviewStatistics.stream()
                    .filter(reviewStatistic -> reviewStatistic.data.meaningMaxStreak < 8 && reviewStatistic.data.readingMaxStreak < 8 && reviewStatistic.data.meaningCurrentStreak < 5 && reviewStatistic.data.readingCurrentStreak < 5)
                    .sorted((o1, o2) -> ((o2.data.meaningIncorrect + o2.data.readingIncorrect) - (o1.data.meaningIncorrect + o1.data.readingIncorrect)))
                    .collect(Collectors.toList());
            AtomicInteger index = new AtomicInteger();
            sorted.forEach(reviewStatistic -> {
                float progress = index.getAndAdd(1) / (float) sorted.size();
                for (LeechLevel level : LeechLevel.values()) {
                    if (progress < level.threshold) {
                        if (subjectData.containsKey(reviewStatistic.data.subjectId)) {
                            leechData.get(level).add(new LeechSubject(reviewStatistic.data, subjectData.get(reviewStatistic.data.subjectId)));
                        }
                        break;
                    }
                }
            });
        } else {
            // TODO \o/
        }
    }

    public List<LeechSubject> getSubjects(LeechLevel level) {
        return leechData.get(level);
    }

    public void add(ResponseData<Subject> subjects, ResponseData<ReviewStatistic> statistics) {
        if (!allSubjects) {
            this.subjects.addAll(subjects.data);
            if (subjects.pages.nextUrl == null) {
                allSubjects = true;
            }
        }
        if (!allReviews) {
            this.reviewStatistics.addAll(statistics.data);
            if (statistics.pages.nextUrl == null) {
                allReviews = true;
            }
        }
    }

    public boolean hasAllData() {
        return allSubjects && allReviews;
    }

    public enum LeechLevel {
        HIGH("High Priority", 0.15f),
        MEDIUM("Medium Priority", 0.50f),
        LOW("Low Priority", 1f);

        private String title;
        private float threshold;

        LeechLevel(String title, float threshold) {
            this.title = title;
            this.threshold = threshold;
        }

        public String getTitle() {
            return title;
        }

        public float getThreshold() {
            return threshold;
        }
    }
}
