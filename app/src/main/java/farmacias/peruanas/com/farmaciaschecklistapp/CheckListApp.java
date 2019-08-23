package farmacias.peruanas.com.farmaciaschecklistapp;

import android.app.Application;

import timber.log.Timber;

public class CheckListApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
