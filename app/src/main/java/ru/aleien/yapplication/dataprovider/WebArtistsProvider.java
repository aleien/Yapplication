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
import ru.aleien.yapplication.ArtistsRequester;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.utils.Utils;

/**
 * Created by aleien on 09.04.16.
 * Поставщик данных о музыкантах с некоторого url.
 */

public class WebArtistsProvider implements ArtistsProvider {
    static final String JSON_URL = "http://cache-default03g.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
    final ArtistsRequester artistsRequester;
    OkHttpClient client;

    public WebArtistsProvider(ArtistsRequester artistsRequester, Context context) {
        this.artistsRequester = artistsRequester;
        Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = Utils.createInterceptor(context);
        Cache cache = Utils.getCache(context);
        client = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .build();
    }

    @Override
    public void requestData() {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Request request = new Request.Builder()
                .url(JSON_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Main", "FAIL");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<Artist> responseList = Utils.decodeResponse(response);
                mainHandler.post(() -> artistsRequester.provideData(responseList)); // Очень странно, что onResponse выполняется не в main-треде
                // Вместо хэндлера можно, например, использовать rx-яву, которая очень хорошо дружит с ретрофитом
            }
        });
    }

}
