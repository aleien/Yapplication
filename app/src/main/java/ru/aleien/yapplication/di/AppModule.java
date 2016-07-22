package ru.aleien.yapplication.di;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.aleien.yapplication.ArtistsPresenter;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 23.07.16.
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Activity activity) {
        this.context = activity.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return this.context;
    }

    @Provides
    @Singleton
    SQLiteDatabase provideDatabase(Context context) {
        return context.openOrCreateDatabase("ArtistsDB", MODE_PRIVATE, null);
    }

//    @Provides
//    @Singleton
//    ArtistsPresenter provideArtistsPresenter(Activity activity) {
//        return new ArtistsPresenter(activity);
//    }
}
