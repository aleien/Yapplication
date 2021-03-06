package ru.aleien.yapplication;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import ru.aleien.yapplication.dataprovider.ArtistsProvider;
import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoFragment;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoView;
import ru.aleien.yapplication.screens.list.ArtistsListView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by aleien on 09.04.16.
 * Тесты!.
 */

@RunWith(org.robolectric.RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ArtistsPresenterTest {
    ArtistsPresenter presenter;
    @Mock Context context;
    @Mock ArtistsProvider provider;
    @Mock Artist artistMock;
    @Mock MainView mainMock;
    @Mock ArtistsListView listMock;
    @Mock ArtistInfoView infoMock;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new ArtistsPresenter(context);
        presenter.artistsProvider = provider;
        presenter.attachView(mainMock);

        doAnswer((invocation -> {
            ArrayList<Artist> items = new ArrayList<>();
            items.add(artistMock);

            presenter.provideData(items);
            return null;
        })).when(provider).requestData();
    }


    @Test
    public void requestData() {
        presenter.takeListView(listMock);
        verify(provider, times(1)).requestData();
    }

    @Test
    public void takeDetailedView() {
        presenter.takeDetailedView(infoMock, artistMock);
        verify(infoMock, times(1)).setInfo(artistMock);
    }

    @Test
    public void artistClicked() {
        presenter.artistClicked(artistMock);
        verify(mainMock, times(1)).changeFragmentTo(any(ArtistInfoFragment.class), anyBoolean());
    }




}
