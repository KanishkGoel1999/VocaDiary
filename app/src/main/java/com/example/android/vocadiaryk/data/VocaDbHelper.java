package com.example.android.vocadiaryk.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.vocadiaryk.data.VocaContract.VocaEntry;

import androidx.annotation.Nullable;

public class VocaDbHelper extends SQLiteOpenHelper {


    //create table
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + VocaEntry.TABLE_NAME + " (" +
            VocaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + VocaEntry.COLUMN_WORD + " TEXT NOT NULL ,"
            + VocaEntry.COLUMN_MEANING + " TEXT ," + VocaEntry.COLUMN_MNEMONIC + " TEXT ," + VocaEntry.COLUMN_SENTENCE + " TEXT ,"
            + VocaEntry.COLUMN_STAGE + " INTEGER)";


    //delete table
    private static final String SQL_DELETE_TABLE = "DROP TABLE " + VocaEntry.TABLE_NAME;



    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "VocaDiary.db";




    public VocaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
