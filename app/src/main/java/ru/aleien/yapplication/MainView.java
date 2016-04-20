package ru.aleien.yapplication;

import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.ui.ArtistInfoFragment;

/**
 * Created by aleien on 18.04.16.
 */
public interface MainView {
    ArtistInfoFragment openArtistInfo(Artist artist);
}
