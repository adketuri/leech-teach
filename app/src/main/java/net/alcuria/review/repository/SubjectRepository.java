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
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

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

    private SubjectRepository() {
        Log.i("SubjectRepository", "Creating");
        disposables.add(LeechDatabase.getInstance().leechSubject().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(leechSubjects -> {
            Log.i("SubjectRepository", "Fetched leech subjects from DB, rows=" + leechSubjects.size());
            LeechCalculator calculator = new LeechCalculator();
            calculator.calculateFromLeechSubjects(leechSubjects);
            setLiveData(calculator);
        }, t -> {
            Log.e("SubjectRepository", "Error fetching leech subjects from db");
            t.printStackTrace();
        }));
    }

    public static SubjectRepository instance() {
        if (repository == null) {
            repository = new SubjectRepository();
        }
        return repository;
    }

    public void onDestroy() {
        Log.i("SubjectRepository", "Disposing all disposables");
        disposables.dispose();
    }

    public LiveData<LeechCalculator> getSubjects(LeechCalculator calculator, int subjectPage, int reviewPage) {
        Log.i("SubjectRepository", "Fetching subjects " + subjectPage + " " + reviewPage);
        if (data == null) {
            data = new MutableLiveData<>();
        }
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
                    calculator.calculateFromLeechSubjects(subjectResponseData, reviewStatisticResponseData);
                    return calculator;
                }
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(calc -> {
            if (calc.hasAllData()) {
                calc.calculate();
                // TODO: is this necessary to delete? since we replace on inserting duplicates, maybe fine not to
//                Log.i("SubjectRepository", "Deleting existing data from leech db");
//                LeechDatabase.getInstance().leechSubject().deleteAll();
                List<LeechSubject> subjects = calculator.getAllSubjects();
                Log.i("SubjectRepository", "Inserting leech subjects into database, size=" + subjects.size());
                disposables.add(LeechDatabase.getInstance().leechSubject().insertAll(subjects).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i("SubjectRepository", "Leech insertion completed successfully");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("SubjectRepository", "Error inserting");
                        e.printStackTrace();
                    }
                }));
                setLiveData(calc);
            } else {
                Log.i("SubjectRepository", "Fetching more data " + nextSubjectPage.get() + " " + nextReviewPage.get());
                getSubjects(calc, nextSubjectPage.get(), nextReviewPage.get());
            }
        }));
        return data;
    }

    private void setLiveData(LeechCalculator calc) {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        data.setValue(calc);
        if (cachedData == null) {
            cachedData = new MutableLiveData<>();
        }
        cachedData.setValue(calc);
    }
}
