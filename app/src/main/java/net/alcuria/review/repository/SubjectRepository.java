package net.alcuria.review.repository;

import android.util.Log;

import net.alcuria.review.http.HttpUtil;
import net.alcuria.review.http.models.ResponseData;
import net.alcuria.review.http.models.Subject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SubjectRepository {

    private MutableLiveData<ResponseData<Subject>> cachedData;

    public LiveData<ResponseData<Subject>> getSubjects() {
        Log.i("Home", "Loading subjects");
        if (cachedData != null) {
            return cachedData;
        }
        final MutableLiveData<ResponseData<Subject>> data = new MutableLiveData<>();
        HttpUtil.getInstance().getApi().getSubjects().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<ResponseData<Subject>>() {
            @Override
            public void onNext(ResponseData<Subject> value) {
                cachedData = data;
                data.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        });
        return data;
    }

}
