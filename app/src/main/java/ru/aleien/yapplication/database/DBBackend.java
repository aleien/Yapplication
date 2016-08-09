package ru.aleien.yapplication.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.aleien.yapplication.model.Artist;
import rx.Observable;

import static ru.aleien.yapplication.database.DBContract.ARTISTS_COLUMN_ALBUMS;
import static ru.aleien.yapplication.database.DBContract.ARTISTS_COLUMN_BIG_COVER;
import static ru.aleien.yapplication.database.DBContract.ARTISTS_COLUMN_DESCRIPTION;
import static ru.aleien.yapplication.database.DBContract.ARTISTS_COLUMN_ID;
import static ru.aleien.yapplication.database.DBContract.ARTISTS_COLUMN_LINK;
import static ru.aleien.yapplication.database.DBContract.ARTISTS_COLUMN_NAME;
import static ru.aleien.yapplication.database.DBContract.ARTISTS_COLUMN_SMALL_COVER;
import static ru.aleien.yapplication.database.DBContract.ARTISTS_COLUMN_TRACKS;
import static ru.aleien.yapplication.database.DBContract.ARTISTS_TABLE_NAME;
import static ru.aleien.yapplication.database.DBContract.ARTIST_COLUMN_ID;
import static ru.aleien.yapplication.database.DBContract.GENRES_COLUMN_GENRE;
import static ru.aleien.yapplication.database.DBContract.GENRES_COLUMN_ID;
import static ru.aleien.yapplication.database.DBContract.GENRES_TABLE_NAME;
import static ru.aleien.yapplication.database.DBContract.GENRE_TO_ARTIST_COLUMN_ID;
import static ru.aleien.yapplication.database.DBContract.GENRE_TO_ARTIST_TABLE_NAME;
import static ru.aleien.yapplication.database.DBContract.allColumns;

public class DBBackend {

    private DBHelper dbOpenHelper;


    @Inject
    public DBBackend(DBHelper helper) {
        dbOpenHelper = helper;
    }

    public void insertArtist(Artist artist) {
        insertArtist(artist.name,
                artist.tracks,
                artist.albums,
                artist.link,
                artist.description,
                artist.cover.small,
                artist.cover.big,
                artist.genres);
    }

    // Напрашивается билдер
    void insertArtist(String name,
                      int tracks,
                      int albums,
                      String link,
                      String description,
                      String small_cover,
                      String big_cover,
                      List<String> genres) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ARTISTS_COLUMN_NAME, name);
            contentValues.put(ARTISTS_COLUMN_TRACKS, tracks);
            contentValues.put(ARTISTS_COLUMN_ALBUMS, albums);
            contentValues.put(ARTISTS_COLUMN_LINK, link);
            contentValues.put(ARTISTS_COLUMN_DESCRIPTION, description);
            contentValues.put(ARTISTS_COLUMN_SMALL_COVER, small_cover);
            contentValues.put(ARTISTS_COLUMN_BIG_COVER, big_cover);

            long artistId = db.insert(ARTISTS_TABLE_NAME, null, contentValues);
            List<Long> genresIds = insertGenres(genres);

            insertRelation(artistId, genresIds);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }


    }

    void insertRelation(long artistId, List<Long> genresIds) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        for (Long genreId : genresIds) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ARTIST_COLUMN_ID, artistId);
            contentValues.put(GENRES_COLUMN_ID, genreId);
            db.insert(GENRE_TO_ARTIST_TABLE_NAME, null, contentValues);
        }
    }

    List<Long> insertGenres(List<String> genres) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        List<Long> rowIds = new ArrayList<>();
        ContentValues contentValues = new ContentValues();
        for (String genre : genres) {
            contentValues.put(GENRES_COLUMN_GENRE, genre);
            long rowId = db.insertWithOnConflict(GENRES_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            rowIds.add(rowId);
        }

        return rowIds;

    }

    public Observable<List<Artist>> getAllArtists() {
        return Observable.create(subscriber -> {
            subscriber.onNext(loadArtists());
            subscriber.onCompleted();
        });
    }

     List<Artist> loadArtists() {
        List<Artist> artists = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(ARTISTS_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            artists.add(cursorToArtist(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        return artists;
    }

    public void clearArtists() {
        dbOpenHelper.getWritableDatabase().execSQL("DELETE FROM " + ARTISTS_TABLE_NAME);
    }

    private Artist cursorToArtist(Cursor cursor) {
        return new Artist(cursor.getInt(cursor.getColumnIndex(ARTISTS_COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(ARTISTS_COLUMN_NAME)),
                new ArrayList<>(),
                cursor.getInt(cursor.getColumnIndex(ARTISTS_COLUMN_TRACKS)),
                cursor.getInt(cursor.getColumnIndex(ARTISTS_COLUMN_ALBUMS)),
                cursor.getString(cursor.getColumnIndex(ARTISTS_COLUMN_LINK)),
                cursor.getString(cursor.getColumnIndex(ARTISTS_COLUMN_DESCRIPTION)),
                new Artist.Cover(cursor.getString(cursor.getColumnIndex(ARTISTS_COLUMN_SMALL_COVER)),
                        cursor.getString(cursor.getColumnIndex(ARTISTS_COLUMN_BIG_COVER))));
    }
}
