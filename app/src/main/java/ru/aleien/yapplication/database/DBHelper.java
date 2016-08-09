package ru.aleien.yapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static ru.aleien.yapplication.database.DBContract.ARTISTS_TABLE_NAME;
import static ru.aleien.yapplication.database.DBContract.ARTIST_COLUMN_ID;
import static ru.aleien.yapplication.database.DBContract.DROP_TABLE_IF_EXISTS;
import static ru.aleien.yapplication.database.DBContract.GENRES_COLUMN_GENRE;
import static ru.aleien.yapplication.database.DBContract.GENRES_COLUMN_ID;
import static ru.aleien.yapplication.database.DBContract.GENRES_TABLE_NAME;
import static ru.aleien.yapplication.database.DBContract.GENRE_COLUMN_ID;
import static ru.aleien.yapplication.database.DBContract.GENRE_TO_ARTIST_COLUMN_ID;
import static ru.aleien.yapplication.database.DBContract.GENRE_TO_ARTIST_TABLE_NAME;

/**
 * Created by user on 22.07.16.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE ARTISTS " +
                        "(" +
                        "ID INTEGER PRIMARY KEY," +
                        "NAME TEXT," +
                        "TRACKS INTEGER," +
                        "ALBUMS INTEGER," +
                        "LINK TEXT," +
                        "DESCRIPTION TEXT," +
                        "SMALL_COVER TEXT," +
                        "BIG_COVER TEXT" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE GENRES" +
                        " (" +
                        "ID INTEGER PRIMARY KEY, " +
                        "GENRE STRING UNIQUE" +
                        ")");

        db.execSQL(
                "CREATE TABLE " + GENRE_TO_ARTIST_TABLE_NAME +
                        " (" + GENRE_TO_ARTIST_COLUMN_ID + " INTEGER PRIMARY KEY, "
                        + ARTIST_COLUMN_ID + " INTEGER," + GENRE_COLUMN_ID + " INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TABLE_IF_EXISTS + ARTISTS_TABLE_NAME);
        db.execSQL(DROP_TABLE_IF_EXISTS + GENRES_TABLE_NAME);
        db.execSQL(DROP_TABLE_IF_EXISTS + GENRE_TO_ARTIST_TABLE_NAME);
        onCreate(db);
    }


}
