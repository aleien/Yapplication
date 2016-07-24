package ru.aleien.yapplication.dataprovider;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import ru.aleien.yapplication.ArtistsRequester;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.utils.Utils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by aleien on 09.04.16.
 * Поставщик данных о музыкантах с некоторого url.
 */

public class WebArtistsProvider implements ArtistsProvider {
    private static final String BASE_URL = "http://cache-default03g.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/";
    private final ArtistsRequester artistsRequester;
    private OkHttpClient client;

    private Api api;

    interface Api {
        @GET("artists.json")
        Observable<List<Artist>> getArtists();
    }

    public WebArtistsProvider(ArtistsRequester artistsRequester, Context context) {
        this.artistsRequester = artistsRequester;
        OkHttpClient loggingClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(loggingClient)
                .build();

        api = retrofit.create(Api.class);
    }

    //TODO: переделать через rx, параллельным запросом?
    @Override
    public void requestData() {
        api.getArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artistsRequester::provideData,
                        Throwable::printStackTrace);
    }

}
