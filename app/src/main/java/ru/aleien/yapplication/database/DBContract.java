package ru.aleien.yapplication.database;

/**
 * Created by aleien on 08.08.16.
 */

public interface DBContract {
    public static final String ARTISTS_TABLE_NAME = "artists";
    public static final String ARTISTS_COLUMN_ID = "id";
    public static final String ARTISTS_COLUMN_NAME = "name";
    public static final String ARTISTS_COLUMN_TRACKS = "tracks";
    public static final String ARTISTS_COLUMN_ALBUMS = "albums";
    public static final String ARTISTS_COLUMN_LINK = "link";
    public static final String ARTISTS_COLUMN_DESCRIPTION = "description";
    public static final String ARTISTS_COLUMN_SMALL_COVER = "small_cover";
    public static final String ARTISTS_COLUMN_BIG_COVER = "big_cover";

    public static final String GENRES_TABLE_NAME = "genres";
    public static final String GENRES_COLUMN_ID = "id";
    public static final String GENRES_COLUMN_GENRE = "genre";

    public static final String GENRE_TO_ARTIST_TABLE_NAME = "genre_to_artist";
    public static final String GENRE_TO_ARTIST_COLUMN_ID = "id";
    public static final String ARTIST_COLUMN_ID = "artist_id";
    public static final String GENRE_COLUMN_ID = "genre_id";

    String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";

    String[] allColumns = new String[]{
            ARTISTS_COLUMN_ID,
            ARTISTS_COLUMN_NAME,
            ARTISTS_COLUMN_TRACKS,
            ARTISTS_COLUMN_ALBUMS,
            ARTISTS_COLUMN_LINK,
            ARTISTS_COLUMN_DESCRIPTION,
            ARTISTS_COLUMN_SMALL_COVER,
            ARTISTS_COLUMN_BIG_COVER
    };
}
