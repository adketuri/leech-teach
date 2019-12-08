package net.alcuria.review.http;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import net.alcuria.review.http.models.SubjectResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

public class HttpUtil {

    public static final String ENDPOINT = "https://api.wanikani.com/v2/";

    private static HttpUtil sInstance;
    private Retrofit mRetrofit;

    private HttpUtil() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static HttpUtil getInstance() {
        if (sInstance == null) {
            sInstance = new HttpUtil();
        }
        return sInstance;
    }

    public void getSubjects(final ResponseListener<SubjectResponse> listener, String key) {
        Call<SubjectResponse> call = mRetrofit.create(WaniKaniApi.class).getSubjects("Bearer " + key);
        call.enqueue(new Callback<SubjectResponse>() {
            @Override
            public void onResponse(Call<SubjectResponse> call, Response<SubjectResponse> response) {
                Log.i("Http", "Got response call");
                listener.invoke(response.body());
            }

            @Override
            public void onFailure(Call<SubjectResponse> call, Throwable t) {
                Log.e("Http", "getSubjects failure");
                t.printStackTrace();
            }
        });
    }

    public interface WaniKaniApi {
        @GET("subjects/")
        Call<SubjectResponse> getSubjects(@Header("Authorization") String auth);
    }

//    public void fetchSubjects(CompositeDisposable compositeDisposable){
//        compositeDisposable.add(mWaniKaniApi.getSubjects()
//                .subscribeOn(Schedulers.computation())
//                .map(trendsResponses -> trendsResponses.get(0))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(trendsResponse -> view.load(trendsResponse.trends()),
//                        throwable -> view.error(throwable)));
//    }
}
