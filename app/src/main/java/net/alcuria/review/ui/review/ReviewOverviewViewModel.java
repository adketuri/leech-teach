package net.alcuria.review.ui.review;

import net.alcuria.review.calc.LeechCalculator;
import net.alcuria.review.quiz.QuizManager;
import net.alcuria.review.repository.SubjectRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReviewOverviewViewModel extends ViewModel {

    private LiveData<LeechCalculator> calculator;
    private LiveData<QuizManager> quiz;

    public ReviewOverviewViewModel() {
    }

    public LiveData<LeechCalculator> getCalculator() {
        if (calculator == null) {
            calculator = SubjectRepository.instance().getSubjects(new LeechCalculator(), 0, 0);
        }
        return calculator;
    }

    public LiveData<QuizManager> getQuizManager() {
        if (quiz == null) {
            quiz = new MutableLiveData<>();
        }
        return quiz;
    }

}
