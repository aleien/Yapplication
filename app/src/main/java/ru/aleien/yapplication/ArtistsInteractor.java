package ru.aleien.yapplication;

import java.util.List;

import ru.aleien.yapplication.model.Artist;

/**
 * Created by aleien on 09.04.16.
 * Интерактор, он же контроллер. Запрашивает список музыкантов у поставщика данных, передает их
 * вьюхам.
 */
public class ArtistsInteractor implements ArtistsRequester {
    final ArtistsProvider artistsProvider = new FakeDataProvider(this);
    ArtistsListView artistsListView;
    ArtistInfoView artistInfoView;

    List<Artist> artists;



    public void takeListView(ArtistsListView list) {
        artistsListView = list;
        artistsProvider.requestData();
    }

    public void takeDetailedView(ArtistInfoView info, Artist artist) {
        artistInfoView = info;
        artistInfoView.setInfo(artist);
    }


    @Override
    public void provideData(List<Artist> response) {
        artists = response;
        artistsListView.setList(artists);
    }

}
