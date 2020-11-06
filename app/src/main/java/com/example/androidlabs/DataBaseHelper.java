package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String MESSAGES = "MESSAGES";
    public static final String COLUMN_MESSAGES = "S_MESSAGES";
    public static final String SID = "SID";
    public static final String COLUMN_SENDER = "COLUMN_SENDER";

    public DataBaseHelper(@Nullable Context context ) {
        super(context, "Messages.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + MESSAGES + " (" + SID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MESSAGES + " TEXT, " + COLUMN_SENDER + " TEXT) ";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGES);

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + MESSAGES);


        onCreate(db);
    }

    }

