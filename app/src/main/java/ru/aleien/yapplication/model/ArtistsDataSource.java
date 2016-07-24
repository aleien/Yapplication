package ru.aleien.yapplication.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static ru.aleien.yapplication.model.DBHelper.ARTISTS_COLUMN_ALBUMS;
import static ru.aleien.yapplication.model.DBHelper.ARTISTS_COLUMN_BIG_COVER;
import static ru.aleien.yapplication.model.DBHelper.ARTISTS_COLUMN_DESCRIPTION;
import static ru.aleien.yapplication.model.DBHelper.ARTISTS_COLUMN_ID;
import static ru.aleien.yapplication.model.DBHelper.ARTISTS_COLUMN_LINK;
import static ru.aleien.yapplication.model.DBHelper.ARTISTS_COLUMN_NAME;
import static ru.aleien.yapplication.model.DBHelper.ARTISTS_COLUMN_SMALL_COVER;
import static ru.aleien.yapplication.model.DBHelper.ARTISTS_COLUMN_TRACKS;
import static ru.aleien.yapplication.model.DBHelper.ARTISTS_TABLE_NAME;

/**
 * Created by aleien on 24.07.16.
 */

public class ArtistsDataSource {

    private SQLiteDatabase mDatabase;

    DBHelper mHelper;

    private String[] allColumns = new String[]{
            ARTISTS_COLUMN_ID,
            ARTISTS_COLUMN_NAME,
            ARTISTS_COLUMN_TRACKS,
            ARTISTS_COLUMN_ALBUMS,
            ARTISTS_COLUMN_LINK,
            ARTISTS_COLUMN_DESCRIPTION,
            ARTISTS_COLUMN_SMALL_COVER,
            ARTISTS_COLUMN_BIG_COVER
    };

    @Inject
    public ArtistsDataSource(DBHelper helper) {
        mHelper = helper;
    }

    public void open() {
        mHelper.getWritableDatabase();
    }

    public void close() {
        mHelper.close();
    }

    public boolean insertArtist(Artist artist) {
        return insertArtist(artist.name, artist.tracks, artist.albums, artist.link, artist.description, artist.cover.small, artist.cover.big);
    }

    public boolean insertArtist(String name, int tracks, int albums, String link, String description, String small_cover, String big_cover) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ARTISTS_COLUMN_NAME, name);
        contentValues.put(ARTISTS_COLUMN_TRACKS, tracks);
        contentValues.put(ARTISTS_COLUMN_ALBUMS, albums);
        contentValues.put(ARTISTS_COLUMN_LINK, link);
        contentValues.put(ARTISTS_COLUMN_DESCRIPTION, description);
        contentValues.put(ARTISTS_COLUMN_SMALL_COVER, small_cover);
        contentValues.put(ARTISTS_COLUMN_BIG_COVER, big_cover);
        db.insert(ARTISTS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Observable<List<Artist>> getAllArtists() {
        return Observable.create(subscriber -> {
            subscriber.onNext(loadArtists());
            subscriber.onCompleted();
        });
    }

    private List<Artist> loadArtists() {
        List<Artist> artists = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
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
        mHelper.getWritableDatabase().execSQL("DELETE FROM " + DBHelper.ARTISTS_TABLE_NAME);
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
