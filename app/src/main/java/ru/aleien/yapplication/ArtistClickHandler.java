package ru.aleien.yapplication;

import java.io.Serializable;

import ru.aleien.yapplication.model.Artist;

public interface ArtistClickHandler extends Serializable {
    void artistClicked(Artist artist);
}
