package ru.aleien.yapplication.model;

import java.util.List;

/**
 * Created by aleien on 05.04.16.
 */
public class Artist {
    public int id;
    public String name;
    public List<String> genres;
    public int tracks;
    public int albums;
    public String link;
    public String description;
    public Cover cover;

    // TODO: builder
    public Artist(int id, String name, List<String> genres, int tracks, int albums, String link, String description, Cover cover) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;
        this.cover = cover;
    }

    public static class Cover {
        public String small;
        public String big;

        public Cover(String small, String big) {
            this.small = small;
            this.big = big;
        }
    }

}
