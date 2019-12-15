package net.alcuria.review.calc;

import android.os.Build;

import net.alcuria.review.http.models.ReviewStatistic;
import net.alcuria.review.http.models.Subject;
import net.alcuria.review.http.models.SubjectData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;

/**
 * Contains logic for calculating leeches; depends on both {@link Subject} data and {@link ReviewStatistic} data.
 *
 * @author Andrew Keturi
 */
public class LeechCalculator {

    private final List<Subject> subjects;
    private final List<ReviewStatistic> reviewStatistics;
    private final Map<LeechLevel, List<LeechSubject>> leechData = new HashMap<>();
    private final Map<Integer, SubjectData> subjectData = new HashMap<>();

    {
        leechData.put(LeechLevel.LOW, new ArrayList<>());
        leechData.put(LeechLevel.MEDIUM, new ArrayList<>());
        leechData.put(LeechLevel.HIGH, new ArrayList<>());
    }

    public LeechCalculator(@NonNull List<Subject> subjects, @NonNull List<ReviewStatistic> reviewStatistics) {
        this.subjects = subjects;
        this.reviewStatistics = reviewStatistics;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            subjects.forEach(subject -> {
                System.err.println("Adding " + subject.id + " " + subject.data);
                subjectData.put(subject.id, subject.data);
            });
            List<ReviewStatistic> sorted = reviewStatistics.stream()
                    .filter(reviewStatistic -> reviewStatistic.data.meaningMaxStreak < 8 && reviewStatistic.data.readingMaxStreak < 8)
                    .sorted((o1, o2) -> (o1.data.meaningIncorrect + o1.data.readingIncorrect) - (o2.data.meaningIncorrect + o2.data.readingIncorrect))
                    .collect(Collectors.toList());
            AtomicInteger index = new AtomicInteger();
            sorted.forEach(reviewStatistic -> {
                float progress = index.getAndAdd(1) / (float) sorted.size();
                System.err.println("Progress: " + progress);
                for (LeechLevel level : LeechLevel.values()) {
                    if (progress < level.threshold) {
                        System.err.println("  Adding to " + level);
                        System.err.println(reviewStatistic.data);
                        System.err.println(subjectData.get(reviewStatistic.data.subjectId));
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
