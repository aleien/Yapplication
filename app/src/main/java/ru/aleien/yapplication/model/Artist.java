package ru.aleien.yapplication.model;

import java.util.List;

public class Artist {
    public final int id;
    public final String name;
    public final List<String> genres;
    public final int tracks;
    public final int albums;
    public final String link;
    public final String description;
    public final Cover cover;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (id != artist.id) return false;
        if (tracks != artist.tracks) return false;
        if (albums != artist.albums) return false;
        if (!name.equals(artist.name)) return false;
        if (!genres.equals(artist.genres)) return false;
        if (!link.equals(artist.link)) return false;
        if (!description.equals(artist.description)) return false;
        return cover.equals(artist.cover);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + genres.hashCode();
        result = 31 * result + tracks;
        result = 31 * result + albums;
        result = 31 * result + link.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + cover.hashCode();
        return result;
    }

    public static class Cover {
        public final String small;
        public final String big;

        public Cover(String small, String big) {
            this.small = small;
            this.big = big;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Cover cover = (Cover) o;

            if (!small.equals(cover.small)) return false;
            return big.equals(cover.big);

        }

        @Override
        public int hashCode() {
            int result = small.hashCode();
            result = 31 * result + big.hashCode();
            return result;
        }
    }

}
