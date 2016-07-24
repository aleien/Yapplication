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

    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table artists " +
                        "(id integer primary key, name text,tracks integer,albums integer, link text,description text, small_cover text, big_cover text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS artists");
        onCreate(db);
    }




}