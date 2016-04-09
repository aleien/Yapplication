package ru.aleien.yapplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.aleien.yapplication.model.Artist;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by aleien on 09.04.16.
 * Тесты!
 * Тестируем логику контроллера (интерактора).
 */
@RunWith(org.robolectric.RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ArtistsInteractorTest {
    private OneAndOnlyActivity activity;

    private Artist goodArtist = new Artist(1, "The Good", Arrays.asList("jazz", "swing"), 10, 2, "http://thegood.com", "The Good's description", new Artist.Cover("smallImage", "bigImage"));
    private Artist badArtist = new Artist(2, "The Bad", null, 0, 0, null, null, null);

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(OneAndOnlyActivity.class)
                .create()
                .get();
    }

    @Test
    public void testSetup() {
        assertTrue(activity.artistsInteractor != null);
        assertTrue(activity.artistsInteractor.artistsProvider != null);
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
        List data = mock(List.class);
        when(data).thenReturn(Collections.singletonList(goodArtist));
        when(data.get(0)).thenReturn(goodArtist);

        activity.artistsInteractor.provideData(data);

    }

    @Test
    public void testIncorrectDataProvided() {

    }

    @Test
    public void connectionError() {

    }
}
