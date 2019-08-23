package farmacias.peruanas.com.farmaciaschecklistapp.api;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import farmacias.peruanas.com.farmaciaschecklistapp.BuildConfig;
import farmacias.peruanas.com.farmaciaschecklistapp.base.LiveDataCallAdapterFactory;
import farmacias.peruanas.com.farmaciaschecklistapp.login.service.LoginService;
import farmacias.peruanas.com.farmaciaschecklistapp.utils.AppConstants;
import farmacias.peruanas.com.farmaciaschecklistapp.utils.SecurePreferences;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static farmacias.peruanas.com.farmaciaschecklistapp.utils.AppConstants.CONNECT_TIMEOUT;
import static farmacias.peruanas.com.farmaciaschecklistapp.utils.AppConstants.READ_TIMEOUT;
import static farmacias.peruanas.com.farmaciaschecklistapp.utils.AppConstants.WRITE_TIMEOUT;

public class RetrofitInstance {

    public static <S> S createService(Class<S> serviceClass, String baseUrl, Context context) {
        baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";


        /*SharedPreferences prefs = context.getSharedPreferences(
                LoginService.PREFERENCES_LOGIN,
                Context.MODE_PRIVATE);
        final String sessionKey = prefs.getString(LoginService.SESSION_KEY, "");*/

        SecurePreferences securePreferences = new SecurePreferences(context, AppConstants.KEY_PREFERENCES_CHECKLIST, true);
        HashMap<String, String> usuarioStringHashMap = securePreferences.getUserDetails();
        String sessionKey = usuarioStringHashMap.get(SecurePreferences.KEY_SESSION);
        // Obtener la versión de la app (no de la librería)
        String appVersion1 = "1.1.8";
        Timber.d("VERSION222 : " + sessionKey);

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersion1 = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final String appVersion = "1.1.8";
        Timber.d("appVersion : " + appVersion);

        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor().
                        setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                        //.addInterceptor(new AuthInterceptor(appVersion,sessionKey))
                        .addInterceptor(chain -> {
                            if (chain.request().url().toString().endsWith("loginMobile")) {
                                Timber.d("chain.request().urlString() " + chain.request().url().toString());
                                Request request = chain.request()
                                        .newBuilder()
                                        .addHeader("X-Version-App", appVersion)
                                        .build();
                                Timber.d("Version2 " + appVersion);
                                return chain.proceed(request);
                            }

                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("Authorization", sessionKey)
                                    .addHeader("X-Version-App", appVersion)
                                    .build();
                            Timber.d("sessionKey222" + sessionKey);
                            Timber.d("Version " + appVersion);
                            return chain.proceed(request);
                        })
                        .addInterceptor(httpLoggingInterceptor)
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .build();

        Retrofit provideRetrofitClient
                = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) // Serialize Objects
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //Set call to return {@link Observable}
                .build();

        return provideRetrofitClient.create(serviceClass);
    }
}
