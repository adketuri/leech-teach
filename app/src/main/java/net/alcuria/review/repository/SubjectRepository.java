package net.alcuria.review.repository;

import android.util.Log;

import net.alcuria.review.calc.LeechCalculator;
import net.alcuria.review.http.HttpUtil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SubjectRepository {

    private MutableLiveData<LeechCalculator> cachedData;

    public LiveData<LeechCalculator> getSubjects() {
        Log.i("Home", "Loading subjects");
        if (cachedData != null) {
            return cachedData;
        }
        final MutableLiveData<LeechCalculator> data = new MutableLiveData<>();


        Disposable subscribe = Observable.zip(
                HttpUtil.getInstance().getApi().getSubjects().subscribeOn(Schedulers.io()),
                HttpUtil.getInstance().getApi().getReviewStatistics().subscribeOn(Schedulers.io()),
                (subjectResponseData, reviewStatisticResponseData) -> new LeechCalculator(subjectResponseData.data, reviewStatisticResponseData.data)
        ).observeOn(AndroidSchedulers.mainThread()).subscribe(value -> {
            data.setValue(value);
            if (cachedData == null) {
                cachedData = new MutableLiveData<>();
            }
            cachedData.setValue(value);
        });
        return data;
    }
}
