package ru.aleien.yapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ru.aleien.yapplication.model.Artist;

import static ru.aleien.yapplication.database.DBContract.DROP_TABLE_IF_EXISTS;

/**
 * Created by user on 22.07.16.
 */

@Singleton
public class DBHelper extends SQLiteOpenHelper {

    @Inject
    public DBHelper(Context context, @Named("dbName") String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE  " + DBContract.Artists.TABLE +
                        "(" +
                        DBContract.Artists.NAME + " TEXT," +
                        DBContract.Artists.TRACKS + " INTEGER," +
                        DBContract.Artists.ALBUMS + " INTEGER," +
                        DBContract.Artists.LINK + " TEXT," +
                        DBContract.Artists.DESCRIPTION + " TEXT," +
                        DBContract.Artists.SMALL_COVER + " TEXT," +
                        DBContract.Artists.BIG_COVER + " TEXT" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE " +
                        DBContract.Genres.TABLE +
                        " (" +
                        DBContract.Genres.NAME + " STRING UNIQUE" +
                        ")");

        db.execSQL(
                "CREATE TABLE " + DBContract.GenreToArtist.TABLE +
                        " (" +
                        DBContract.GenreToArtist.ARTIST_ID + " INTEGER," +
                        DBContract.GenreToArtist.GENRE_ID + " INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TABLE_IF_EXISTS + DBContract.Artists.TABLE);
        db.execSQL(DROP_TABLE_IF_EXISTS + DBContract.Genres.TABLE);
        db.execSQL(DROP_TABLE_IF_EXISTS + DBContract.GenreToArtist.TABLE);
        onCreate(db);
    }

}