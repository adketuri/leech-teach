package net.alcuria.review.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import net.alcuria.review.http.models.ResponseData;
import net.alcuria.review.http.models.Subject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class HttpUtil {

    public static final String ENDPOINT = "https://api.wanikani.com/v2/";

    private static HttpUtil instance;
    private WaniKaniApi api;

    private HttpUtil() {
        //https://stackoverflow.com/questions/41078866/retrofit2-authorisation-with-bearer-token
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new ServiceInterceptor()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(WaniKaniApi.class);
    }

    public static HttpUtil getInstance() {
        if (instance == null) {
            instance = new HttpUtil();
        }
        return instance;
    }

    public void getSubjects(final ResponseListener<ResponseData<Subject>> listener, String key) {
        api.getSubjects().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<ResponseData<Subject>>() {
            @Override
            public void onNext(ResponseData<Subject> value) {
                listener.invoke(value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public interface WaniKaniApi {
        @GET("subjects/")
        Observable<ResponseData<Subject>> getSubjects();
    }
}
