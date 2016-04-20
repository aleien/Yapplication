package ru.aleien.yapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.aleien.yapplication.model.Artist;

/**
 * Created by aleien on 18.04.16.
 */
public class FakeDataProvider implements ArtistsProvider {
    ArtistsPresenter artistsPresenter;
    private Artist goodArtist = new Artist(1, "The Good", Arrays.asList("jazz", "swing"), 10, 2, "http://thegood.com", "The Good's description", new Artist.Cover("http://fs145.www.ex.ua/show/1245865/1245865.jpg", "http://pastdaily.com/wp-content/uploads/2015/08/The-Good-The-Bad-and-The-Queen-resize-1.jpg"));

    public FakeDataProvider(ArtistsPresenter interactor) {
        this.artistsPresenter = interactor;
    }

    @Override
    public void requestData() {
        List<Artist> data = new ArrayList<>();
        data.add(goodArtist);
        artistsPresenter.provideData(data);
    }
}
