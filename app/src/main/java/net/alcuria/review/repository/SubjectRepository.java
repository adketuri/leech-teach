package net.alcuria.review.repository;

import android.util.Log;

import net.alcuria.review.calc.LeechCalculator;
import net.alcuria.review.http.HttpUtil;

import java.util.concurrent.atomic.AtomicInteger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SubjectRepository {

    private MutableLiveData<LeechCalculator> cachedData;
    private MutableLiveData<LeechCalculator> data;

    private static SubjectRepository repository;

    public static SubjectRepository instance() {
        if (repository == null) {
            repository = new SubjectRepository();
        }
        return repository;
    }

    public LiveData<LeechCalculator> getSubjects(LeechCalculator calculator, int subjectPage, int reviewPage) {
        Log.i("Home", "Loading subjects " + subjectPage + " " + reviewPage);
        if (cachedData != null) {
            return cachedData;
        }
        if (data == null) {
            data = new MutableLiveData<>();
        }
        AtomicInteger nextSubjectPage = new AtomicInteger(subjectPage);
        AtomicInteger nextReviewPage = new AtomicInteger(reviewPage);
        Disposable subscribe = Observable.zip(
                HttpUtil.getInstance().getApi().getSubjects(subjectPage).subscribeOn(Schedulers.io()),
                HttpUtil.getInstance().getApi().getReviewStatistics(reviewPage).subscribeOn(Schedulers.io()),
                (subjectResponseData, reviewStatisticResponseData) -> {
                    if (subjectResponseData.pages.nextUrl != null) {
                        nextSubjectPage.set(Integer.parseInt(subjectResponseData.pages.nextUrl.split("=")[1]));
                    }
                    if (reviewStatisticResponseData.pages.nextUrl != null) {
                        nextReviewPage.set(Integer.parseInt(reviewStatisticResponseData.pages.nextUrl.split("=")[1]));
                    }
                    calculator.add(subjectResponseData, reviewStatisticResponseData);
                    return calculator;
                }
        ).observeOn(AndroidSchedulers.mainThread()).subscribe(calc -> {
            if (calc.hasAllData()) {
                calc.calculate();
                data.setValue(calc);
                if (cachedData == null) {
                    cachedData = new MutableLiveData<>();
                }
                cachedData.setValue(calc);
            } else {
                getSubjects(calc, nextSubjectPage.get(), nextReviewPage.get());
            }
        });
        return data;
    }


}
