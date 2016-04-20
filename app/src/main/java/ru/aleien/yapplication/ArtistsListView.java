package ru.aleien.yapplication;

import android.widget.ListAdapter;

/**
 * Created by aleien on 09.04.16.
 * Интерфейс для вьюхи, отображающей список музыкантов.
 */
public interface ArtistsListView {
    void setAdapter(ListAdapter adapter);

}
