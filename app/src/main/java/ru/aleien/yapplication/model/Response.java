package ru.aleien.yapplication.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleien on 09.04.16.
 * Класс-обертка для чтения ответа от сервера.
 */
public class Response<T> {
    public List<T> toList() {
        return new ArrayList<>();
    }
}
