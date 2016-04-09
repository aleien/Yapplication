package ru.aleien.yapplication;

import java.util.List;

import ru.aleien.yapplication.model.Artist;

/**
 * Created by aleien on 09.04.16.
 * Интерфейс для поставщика данных. Сделано так для того, чтобы не было зависимости от
 * определенной реализации и было не важно, поступают данные от сервера, базы данных или еще откуда-то.
 * Плюс: легко замокать! (:
 */
public interface ArtistsRequester {
    void provideData(List<Artist> response);
}
