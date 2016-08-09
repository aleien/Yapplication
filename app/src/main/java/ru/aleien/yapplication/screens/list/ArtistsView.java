package ru.aleien.yapplication.screens.list;

import java.util.List;

import ru.aleien.yapplication.ArtistClickHandler;
import ru.aleien.yapplication.model.Artist;

/**
 * Created by aleien on 09.08.16.
 */

public interface ArtistsView {
    void showContent(List<Artist> artists, ArtistClickHandler handler);
}
