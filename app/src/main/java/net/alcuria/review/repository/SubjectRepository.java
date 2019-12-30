package net.alcuria.review.repository;

import android.util.Log;

import net.alcuria.review.calc.LeechCalculator;
import net.alcuria.review.calc.LeechSubject;
import net.alcuria.review.calc.database.LeechDatabase;
import net.alcuria.review.http.HttpUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * A repository for holding application data.
 *
 * @author Andrew Keturi
 */
public class SubjectRepository {

    private static SubjectRepository repository;
    private MutableLiveData<LeechCalculator> cachedData;
    private MutableLiveData<LeechCalculator> data;
    private CompositeDisposable disposables = new CompositeDisposable();

    private SubjectRepository() {}

    public static SubjectRepository instance() {
        if (repository == null) {
            repository = new SubjectRepository();
        }
        return repository;
    }

    public void onDestroy (){
        disposables.dispose();
    }

    public LiveData<LeechCalculator> getSubjects(LeechCalculator calculator, int subjectPage, int reviewPage) {
        Log.i("Home", "Loading subjects " + subjectPage + " " + reviewPage);
        if (cachedData != null) {
            return cachedData;
        }
        if (data == null) {
            data = new MutableLiveData<>();
        }

        disposables.add(LeechDatabase.getInstance().leechSubject().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSubscriber<List<LeechSubject>>() {
            @Override
            public void onNext(List<LeechSubject> leechSubjects) {
                System.err.println("onNext "  + leechSubjects);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error!");
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.err.println("complete");
            }
        }));

//        Flowable<List<LeechSubject>> db = LeechDatabase.getInstance().leechSubject().getAll();
//        Disposable disposable = Flowable.just(db)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Flowable<List<LeechSubject>>>() {
//                    @Override
//                    public void accept(Flowable<List<LeechSubject>> listFlowable) throws Exception {
//                        System.err.println("got item!!");
//                        List<LeechSubject> subjects = listFlowable.blockingFirst();
//                        LeechCalculator calc = new LeechCalculator();
//                        calc.add(subjects);
//                        data.setValue(calc);
//                        if (cachedData == null) {
//                            cachedData = new MutableLiveData<>();
//                        }
//                        cachedData.setValue(calc);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        System.err.println("Error!");
//                        throwable.printStackTrace();
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        System.err.println("oncomplete");
//                    }
//                }, new Consumer<Subscription>() {
//                    @Override
//                    public void accept(Subscription subscription) throws Exception {
//                        System.err.println("on subscribe");
//                    }
//                });
//        disposables.add(disposable);

//        Maybe.just(LeechDatabase.getInstance().leechSubject().getAll()).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Maybe<List<LeechSubject>>>() {
//            @Override
//            public void accept(Maybe<List<LeechSubject>> listMaybe) throws Exception {
//                System.err.println("got item!!");
//                List<LeechSubject> subjects = listMaybe.blockingGet();
//                LeechCalculator calc = new LeechCalculator();
//                calc.add(subjects);
//                data.setValue(calc);
//                if (cachedData == null) {
//                    cachedData = new MutableLiveData<>();
//                }
//                cachedData.setValue(calc);
//
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                System.err.println("Error!");
//                throwable.printStackTrace();
//            }
//        });
//        return data;

        AtomicInteger nextSubjectPage = new AtomicInteger(subjectPage);
        AtomicInteger nextReviewPage = new AtomicInteger(reviewPage);
        disposables.add(Observable.zip(
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
                    if (calculator.hasAllData()){
                        System.err.println("Deleting all data");
                        LeechDatabase.getInstance().leechSubject().deleteAll();
                        System.err.println("Now inserting");
                        LeechDatabase.getInstance().leechSubject().insertAll(calculator.getSubjects(LeechCalculator.LeechLevel.HIGH));
                    }
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
                System.err.println("Fetching more data " + nextSubjectPage.get() + " " + nextReviewPage.get());
                getSubjects(calc, nextSubjectPage.get(), nextReviewPage.get());
            }
        }));
        return data;
    }
}
