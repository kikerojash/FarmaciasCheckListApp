package farmacias.peruanas.com.farmaciaschecklistapp.api;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.concurrent.TimeUnit;
import farmacias.peruanas.com.farmaciaschecklistapp.BuildConfig;
import farmacias.peruanas.com.farmaciaschecklistapp.base.LiveDataCallAdapterFactory;
import farmacias.peruanas.com.farmaciaschecklistapp.login.service.LoginService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import static farmacias.peruanas.com.farmaciaschecklistapp.utils.AppConstants.CONNECT_TIMEOUT;
import static farmacias.peruanas.com.farmaciaschecklistapp.utils.AppConstants.READ_TIMEOUT;
import static farmacias.peruanas.com.farmaciaschecklistapp.utils.AppConstants.WRITE_TIMEOUT;

public class RetrofitInstance {

    public static <S> S createService(Class<S> serviceClass, String baseUrl, Context context) {
        baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";


        SharedPreferences prefs = context.getSharedPreferences(
                LoginService.PREFERENCES_LOGIN,
                Context.MODE_PRIVATE);
        final String sessionKey = prefs.getString(LoginService.SESSION_KEY, "");

        // Obtener la versión de la app (no de la librería)
        String appVersion1 = "Desconocido (>= 1.1.8)";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersion1 = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final String appVersion = appVersion1;

        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor().
                        setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                        .addInterceptor(new AuthInterceptor(appVersion,sessionKey))
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
