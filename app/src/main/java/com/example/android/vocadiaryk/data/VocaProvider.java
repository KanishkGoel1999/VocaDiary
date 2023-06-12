package com.example.android.vocadiaryk.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.Selection;
import android.util.Log;
import android.widget.Toast;

import com.example.android.vocadiaryk.data.VocaContract.VocaEntry;


public class VocaProvider extends ContentProvider {

    private VocaDbHelper vocaDbHelper;

    private static final int VOCA = 100;
    private static final int VOCA_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(VocaContract.CONTENT_AUTHORITY, VocaContract.PATH_VOCA, VOCA);
        sUriMatcher.addURI(VocaContract.CONTENT_AUTHORITY, VocaContract.PATH_VOCA + "/#", VOCA_ID);
    }

    /** Tag for log messages */
    public static final String LOG_TAG = VocaProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        vocaDbHelper = new VocaDbHelper(getContext());
        return false;
    }


    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        SQLiteDatabase sqLiteDatabase = vocaDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match){
            case VOCA:
                cursor = sqLiteDatabase.query(VocaEntry.TABLE_NAME, null,null,null,null,null,null);
                break;

            case VOCA_ID:
                selection = VOCA_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(VocaEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default: throw new IllegalArgumentException("Query not possible for " + uri);
        }
        return cursor;
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    private Uri insertWord(Uri uri, ContentValues values){
        String vWord = values.getAsString(VocaEntry.COLUMN_WORD);
        if(vWord == null){
            throw new IllegalArgumentException("no insertion for this " + uri);
        }

        SQLiteDatabase sqLiteDatabase = vocaDbHelper.getWritableDatabase();
        long id = sqLiteDatabase.insert(VocaEntry.TABLE_NAME, null, values);

        if (id == -1){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);

        switch (match){
            case VOCA:
                return insertWord(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for this uri: " + uri);
        }
    }

    private int updateWord(Uri uri, ContentValues values, String selection, String[] selectionArgs){


        SQLiteDatabase sqLiteDatabase = vocaDbHelper.getWritableDatabase();

        long id = sqLiteDatabase.update(VocaEntry.TABLE_NAME, values, selection, selectionArgs);


        if (id == -1){
            throw new IllegalArgumentException("Cannot update this " + uri);
        }

        return  (int)id;
    }


    @Override
    public int update( Uri uri,  ContentValues values, String selection, String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);

        switch (match){
            case VOCA:
                return updateWord(uri, values, selection,selectionArgs);
            case VOCA_ID:
                selection = VOCA_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateWord(uri, values, selection, selectionArgs);
            default: throw new IllegalArgumentException("Update not possible for " + uri);
        }

    }


    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase = vocaDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case VOCA:
                return sqLiteDatabase.delete(VocaEntry.TABLE_NAME, null, null);
            case VOCA_ID:
                selection = VOCA_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return sqLiteDatabase.delete(VocaEntry.TABLE_NAME, selection, selectionArgs);
            default: throw new IllegalArgumentException("deletion not possible ");
        }
    }
}
