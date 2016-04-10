package ru.aleien.yapplication;

import ru.aleien.yapplication.model.Artist;

/**
 * Created by aleien on 10.04.16.
 */
public interface UIReactor {
    void onArtistClicked(Artist artist);

    void onBackClicked();
}
