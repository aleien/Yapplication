package ru.aleien.yapplication;

import java.util.List;

import ru.aleien.yapplication.model.Artist;

/**
 * Created by aleien on 09.04.16.
 * Интерфейс для вьюхи, отображающей список музыкантов.
 */
public interface ArtistsListView {
    void setList(List<Artist> artists);
}
