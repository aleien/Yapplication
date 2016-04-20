package ru.aleien.yapplication;

import java.util.List;

import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.ui.ArtistInfoFragment;
import ru.aleien.yapplication.ui.adapters.ArtistsListAdapter;

/**
 * Created by aleien on 09.04.16.
 * Интерактор, он же контроллер. Запрашивает список музыкантов у поставщика данных, передает их
 * вьюхам.
 */
public class ArtistsPresenter implements ArtistsRequester, ArtistClickHandler {
    final ArtistsProvider artistsProvider = new FakeDataProvider(this);
    ArtistsListView artistsListView;
    ArtistInfoView artistInfoView;
    MainView mainView;

    List<Artist> artists;

    @Override
    public void takeListView(ArtistsListView list) {
        artistsListView = list;
        artistsProvider.requestData();
    }

    @Override
    public void takeDetailedView(ArtistInfoView info, Artist artist) {
        artistInfoView = info;
        artistInfoView.setInfo(artist);
    }

    @Override
    public void takeMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void provideData(List<Artist> response) {
        artists = response;
        artistsListView.setAdapter(new ArtistsListAdapter(artists, this));
    }

    @Override
    public void artistClicked(Artist artist) {
        ArtistInfoFragment infoFragment = mainView.openArtistInfo(artist);
        takeDetailedView(infoFragment, artist);
    }

}
