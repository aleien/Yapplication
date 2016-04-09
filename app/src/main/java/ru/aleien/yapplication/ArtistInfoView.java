package ru.aleien.yapplication;

import ru.aleien.yapplication.model.Artist;

/**
 * Created by aleien on 09.04.16.
 * Интерфейс для вьюхи, отображающей информацию о музыканте
 */
public interface ArtistInfoView {
    void setInfo(Artist artist);
}
