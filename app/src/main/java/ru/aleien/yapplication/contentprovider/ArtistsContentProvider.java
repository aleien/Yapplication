package ru.aleien.yapplication.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import ru.aleien.yapplication.database.DBContract;
import ru.aleien.yapplication.database.DBHelper;
import timber.log.Timber;

import static ru.aleien.yapplication.contentprovider.ProviderContract.ARTISTS_PATH;
import static ru.aleien.yapplication.contentprovider.ProviderContract.AUTHORITY;
import static ru.aleien.yapplication.contentprovider.ProviderContract.URI_ARTISTS;
import static ru.aleien.yapplication.contentprovider.ProviderContract.URI_ARTISTS_ID;

public class ArtistsContentProvider extends ContentProvider {

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, ARTISTS_PATH, URI_ARTISTS);
        uriMatcher.addURI(AUTHORITY, ARTISTS_PATH + "/#", URI_ARTISTS_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Timber.d("Content provider created");
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Timber.v("Making query to content provider");
        Timber.d("Uri: " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_ARTISTS:
                Timber.v("Resolving query to ARTISTS");
                break;
            case URI_ARTISTS_ID:
                Timber.v("Resolving query to specific ARTIST");
                String id = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection)) {
                    selection = DBContract.Artists.ID + " = " + id;
                } else {
                    selection = selection + " AND " + DBContract.Artists.ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        db = dbHelper.getReadableDatabase();
        return db.query(DBContract.Artists.TABLE,
                projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != ProviderContract.URI_ARTISTS) {
            throw new IllegalArgumentException(
                    "Unsupported URI for insertion: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (uriMatcher.match(uri) == URI_ARTISTS) {
            long id = db.insert(
                    DBContract.Artists.TABLE,
                    null,
                    values);
            return getUriForId(id, uri);
        }

        return null;
    }

    private Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);
            ContentResolver resolver = getContext() == null ? null : getContext().getContentResolver();
            if (resolver != null) {
                getContext().getContentResolver()
                        .notifyChange(itemUri, null);
            }

            return itemUri;
        }
        throw new SQLException(
                "Problem while inserting into uri: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int delCount = 0;
        switch (uriMatcher.match(uri)) {
            case URI_ARTISTS:
                delCount = db.delete(
                        DBContract.Artists.TABLE,
                        selection,
                        selectionArgs);
                break;
            case URI_ARTISTS_ID:
                String idStr = uri.getLastPathSegment();
                String where = DBContract.Artists.ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                delCount = db.delete(
                        DBContract.Artists.TABLE,
                        where,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        if (delCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return delCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updateCount = 0;
        switch (uriMatcher.match(uri)) {
            case URI_ARTISTS:
                updateCount = db.update(
                        DBContract.Artists.TABLE,
                        values,
                        selection,
                        selectionArgs);
                break;
            case URI_ARTISTS_ID:
                String idStr = uri.getLastPathSegment();
                String where = DBContract.Artists.ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                updateCount = db.update(
                        DBContract.Artists.TABLE,
                        values,
                        where,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }
}
