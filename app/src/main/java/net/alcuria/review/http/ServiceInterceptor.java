package net.alcuria.review.http;

import net.alcuria.review.util.PrefUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An {@link Interceptor} that adds the bearer token to http requests.
 *
 * @author Andrew Keturi
 */
public class ServiceInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.header("No-Authentication") == null) {
            String token = PrefUtil.getInstance().apiKey();
            if (token != null) {
                request = request.newBuilder().addHeader("Authorization", "Bearer " + token).build();
            }
        }
        return chain.proceed(request);
    }

}
