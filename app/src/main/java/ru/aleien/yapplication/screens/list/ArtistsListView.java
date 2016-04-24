package ru.aleien.yapplication.screens.list;

/**
 * Created by aleien on 09.04.16.
 * Интерфейс для вьюхи, отображающей список музыкантов.
 */
public interface ArtistsListView<T> {
    void setAdapter(T adapter);

}
