package farmacias.peruanas.com.farmaciaschecklistapp.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class AuthInterceptor implements Interceptor {


    private String appVersion;
    private String sessionKey;

    AuthInterceptor(String appVersion, String sessionKey) {
        this.appVersion = appVersion;
        this.sessionKey = sessionKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Timber.d("intercept " + chain.request().url().toString());
        if (chain.request().url().toString().endsWith("loginMobile")) {
            Timber.d("chain.request().urlString() " + chain.request().url().toString());
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("X-Version-App", appVersion)
                    .build();
            return chain.proceed(request);
        }

        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", sessionKey)
                .addHeader("X-Version-App", appVersion)
                .build();
        Timber.d("sessionKey " + sessionKey);
        Timber.d( "Version " + appVersion);
        return chain.proceed(request);

    }
}
