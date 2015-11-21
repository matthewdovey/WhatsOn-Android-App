package com.matthew.whatsonandroidapp;

/**
 * Created by matthew on 20/11/15.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eventsDB.db";
    private static final String TABLE_EVENTS = "events";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EVENTLINK = "link";
    public static final String COLUMN_EVENTNAME = "event";
    public static final String COLUMN_EVENTINFO = "info";
    public static final String COLUMN_EVENTDESC = "description";
    public static final String COLUMN_EVENTIMAGE = "image";

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTS_TABLE = "CREATE TABLE " +
                TABLE_EVENTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_EVENTLINK
                + " TEXT," + COLUMN_EVENTNAME + " TEXT," + COLUMN_EVENTINFO
                + " TEXT," + COLUMN_EVENTDESC + " TEXT," + COLUMN_EVENTIMAGE
                + " TEXT," + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);

    }

}