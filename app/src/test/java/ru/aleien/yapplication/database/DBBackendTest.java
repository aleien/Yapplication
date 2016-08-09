package ru.aleien.yapplication.database;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import ru.aleien.yapplication.BuildConfig;
import ru.aleien.yapplication.model.Artist;

/**
 * Created by aleien on 09.08.16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class DBBackendTest {
    DBHelper dbHelper = new DBHelper(RuntimeEnvironment.application, "testDB");
    DBBackend dbBackend = new DBBackend(dbHelper);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        dbBackend.clearArtists();

    }

    @Test
    public void insertArtist() throws Exception {
        List<String> genres = new ArrayList<>();
        genres.add("pop");
        genres.add("jazz");
        Artist artist = new Artist(500, "Tove Lo", genres, 20, 2, "http://lalala.com", "Description", new Artist.Cover("http://firstimage", "http://seconimage"));
        dbBackend.insertArtist(artist);

        List<Artist> artists = dbBackend.loadArtists();
        Assert.assertEquals(artists.size(), 1);
    }

    @Test
    public void insertArtist1() throws Exception {

    }

    @Test
    public void insertRelation() throws Exception {

    }

    @Test
    public void insertGenres() throws Exception {

    }

    @Test
    public void getAllArtists() throws Exception {

    }

    @Test
    public void clearArtists() throws Exception {

    }

}