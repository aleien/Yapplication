package ru.aleien.yapplication.dataprovider;

import java.util.List;

import ru.aleien.yapplication.model.Artist;
import rx.Observable;

/**
 * Created by aleien on 09.04.16.
 * Интерфейс между контроллером и поставщиком данных
 */
public interface ArtistsProvider {
    // Лучше дженерик?
    Observable<List<Artist>> requestData();
}
