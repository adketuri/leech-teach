package net.alcuria.review.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import net.alcuria.review.http.models.ResponseData;
import net.alcuria.review.http.models.ReviewStatistic;
import net.alcuria.review.http.models.Subject;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Singleton for storing our webservice instance. This is a little ugly; we'd eventually prefer to
 * substitute this out for DI with something like Dagger 2.
 *
 * @author Andrew Keturi
 */
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

    public WaniKaniApi getApi() {
        return api;
    }

    public interface WaniKaniApi {
        @GET("subjects/")
        Observable<ResponseData<Subject>> getSubjects();

        @GET("subjects/")
        Observable<ResponseData<Subject>> getSubjects(@Query("page_after_id") int pageId);

        @GET("review_statistics/")
        Observable<ResponseData<ReviewStatistic>> getReviewStatistics();

    }
}
