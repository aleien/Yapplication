package ru.aleien.yapplication.di;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.aleien.yapplication.ArtistsPresenter;
import ru.aleien.yapplication.model.DBHelper;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 23.07.16.
 */
@Module
public class AppModule {

    private Context context;
    public static String dbName = "ArtistsDB";

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
        return context.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(Context context) {
        return new DBHelper(context, dbName);
    }

}
