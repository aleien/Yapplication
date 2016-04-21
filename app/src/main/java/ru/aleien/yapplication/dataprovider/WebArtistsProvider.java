package ru.aleien.yapplication.dataprovider;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.aleien.yapplication.ArtistsRequester;
import ru.aleien.yapplication.model.Artist;

/**
 * Created by aleien on 09.04.16.
 * Поставщик данных о музыкантах с некоторого url.
 */
// TODO: Кэширование резульатов
public class WebArtistsProvider implements ArtistsProvider {
    static final String JSON_URL = "http://cache-default03g.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";

    final OkHttpClient client = new OkHttpClient();
    final ArtistsRequester artistsRequester;

    public WebArtistsProvider(ArtistsRequester artistsRequester) {
        this.artistsRequester = artistsRequester;
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
                List<Artist> responseList = decodeResponse(response);
                mainHandler.post(() -> artistsRequester.provideData(responseList));
            }
        });
    }

    private List<Artist> decodeResponse(Response response) {
        List<Artist> resultList = null;
        Type listType = new TypeToken<List<Artist>>() {
        }.getType();
        try {
            resultList = new Gson().fromJson(response.body().string(), listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
