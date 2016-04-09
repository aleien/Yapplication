package ru.aleien.yapplication;

import android.support.v4.app.FragmentManager;

import java.util.List;

import ru.aleien.yapplication.dataprovider.WebArtistsProvider;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.ui.ArtistInfoFragment;
import ru.aleien.yapplication.ui.ArtistsListFragment;

/**
 * Created by aleien on 09.04.16.
 * Интерактор, он же контроллер. Запрашивает список музыкантов у поставщика данных, передает их
 * вьюхам.
 */
public class ArtistsInteractor implements ArtistsRequester {
    final ArtistsProvider artistsProvider = new WebArtistsProvider(this);
    final ArtistsListView artistsListView = new ArtistsListFragment();
    final ArtistInfoView artistInfoView = new ArtistInfoFragment();


    public void setup(FragmentManager fragmentManager) {

    }

    public void takeListView(ArtistsListView list) {

    }

    public void takeDetailedView(ArtistInfoView info) {

    }


    @Override
    public void provideData(List<Artist> response) {

    }
}
