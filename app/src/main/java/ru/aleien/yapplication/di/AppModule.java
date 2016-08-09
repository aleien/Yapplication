package ru.aleien.yapplication.di;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import ru.aleien.yapplication.model.Artist;
import rx.Observable;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 23.07.16.
 */
@Module
public class AppModule {
    private static final String BASE_URL = "http://cache-default03g.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/";

    private Context context;
    private String dbName = "ArtistsDB";

    public AppModule(Application application) {
        this.context = application;
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
    @Named("dbName")
    String provideDBName() {
        return dbName;
    }

    @Provides
    @Singleton
    Retrofit provideRestClient(OkHttpClient client) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
    }

    @Provides
    Api provideApi(Retrofit rest) {
        return rest.create(Api.class);
    }

    public interface Api {
        @GET("artists.json")
        Observable<List<Artist>> getArtists();
    }

}
