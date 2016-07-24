package ru.aleien.yapplication.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by user on 22.07.16.
 */

public class DBHelper extends SQLiteOpenHelper {
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

    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table artists " +
                        "(id integer primary key, name text,tracks integer,albums integer, link text,description text, small_cover text, big_cover text)"
        );

        db.execSQL(
                "CREATE TABLE " + GENRES_TABLE_NAME +
                        " (" + GENRES_COLUMN_ID + " integer primary key, " + GENRES_COLUMN_GENRE + " string)");

        db.execSQL(
                "CREATE TABLE " + GENRE_TO_ARTIST_TABLE_NAME +
                        " (" + GENRE_TO_ARTIST_COLUMN_ID + " integer primary key, "
                        + ARTIST_COLUMN_ID + " integer," + GENRE_COLUMN_ID + " integer )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + ARTISTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GENRES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GENRE_TO_ARTIST_TABLE_NAME);
        onCreate(db);
    }


}
