package ru.aleien.yapplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.aleien.yapplication.model.Artist;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by aleien on 09.04.16.
 * Тесты!
 * Тестируем логику контроллера (интерактора).
 */

@RunWith(org.robolectric.RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ArtistsInteractorTest {
    private ListArtistsActivity activity;

    private Artist goodArtist = new Artist(1, "The Good", Arrays.asList("jazz", "swing"), 10, 2, "http://thegood.com", "The Good's description", new Artist.Cover("smallImage", "bigImage"));
    private Artist badArtist = new Artist(2, "The Bad", null, 0, 0, null, null, null);

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(ListArtistsActivity.class)
                .create()
                .get();
    }

    @Test
    public void testSetup() {
        assertThat("ArtistsListView visible", activity.getSupportFragmentManager().getFragments().get(0) instanceof ArtistsListView);
    }

    @Test
    public void testCachedData() {

    }

    @Test
    public void testRequestData() {

    }

    @Test
    public void testCorrectDataProvided() {
        List<Artist> data = new ArrayList<>();
        data.add(goodArtist);


//        activity.artistsInteractor.provideData(data);

    }

    @Test
    public void testIncorrectDataProvided() {

    }

    @Test
    public void connectionError() {

    }

}
