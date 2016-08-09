package ru.aleien.yapplication.dataprovider;

import java.util.List;

import javax.inject.Inject;

import ru.aleien.yapplication.di.AppModule;
import ru.aleien.yapplication.model.Artist;
import rx.Observable;

/**
 * Created by aleien on 09.04.16.
 * Поставщик данных о музыкантах с некоторого url.
 */

public class WebArtistsProvider implements ArtistsProvider {

    private AppModule.Api api;

    @Inject
    public WebArtistsProvider(AppModule.Api api) {
        this.api = api;
    }

    @Override
    public Observable<List<Artist>> requestData() {
        return api.getArtists();
    }

}
