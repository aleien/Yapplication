package ru.aleien.yapplication;

import android.app.Application;

import com.facebook.stetho.Stetho;

import ru.aleien.yapplication.di.AppComponent;
import ru.aleien.yapplication.di.AppModule;
import ru.aleien.yapplication.di.DaggerAppComponent;
import timber.log.Timber;

public class App extends Application {
    private AppComponent component;


    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
        Stetho.initializeWithDefaults(this.getApplicationContext());
         component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    public AppComponent dagger() {
        return component;
    }
}
