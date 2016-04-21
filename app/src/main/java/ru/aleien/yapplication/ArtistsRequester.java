package ru.aleien.yapplication;

import java.util.List;

import ru.aleien.yapplication.model.Artist;
import ru.aleien.yapplication.screens.detailedinfo.ArtistInfoView;
import ru.aleien.yapplication.screens.list.ArtistsListView;

/**
 * Created by aleien on 09.04.16.
 * Интерфейс для поставщика данных. Сделано так для того, чтобы не было зависимости от
 * определенной реализации и было не важно, поступают данные от сервера, базы данных или еще откуда-то.
 * Плюс: легко замокать! (:
 */
public interface ArtistsRequester {
    void takeDetailedView(ArtistInfoView infoView, Artist artist);
    void takeListView(ArtistsListView listView);
    void provideData(List<Artist> response);
}
