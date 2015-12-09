package com.matthew.whatsonandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ayuub2 on 02/12/2015.
 */
public class webscraperAdapter {
    webscraperDatabase database;

    private ArrayList<String> eventLinks = new ArrayList<String>();
    private ArrayList<String> eventTitles = new ArrayList<String>();
    private ArrayList<String> eventInfos = new ArrayList<String>();
    private ArrayList<String> eventImages = new ArrayList<String>();
    private ArrayList<String> eventDescs = new ArrayList<String>();

    public webscraperAdapter(Context context){
        database = new webscraperDatabase(context);
    }

    public ArrayList<String> getEventLinks() {return eventLinks;}
    public ArrayList<String> getEventTitles() {return eventTitles;}
    public ArrayList<String> getEventInfos() {return eventInfos;}
    public ArrayList<String> getEventDescs() {return eventDescs;}
    public ArrayList<String> getEventImages() {return eventImages;}

    public long insertData(String links, String images, String titles, String info,String desc){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(webscraperDatabase.LINKS, links);
        contentValues.put(webscraperDatabase.IMAGES, images);
        contentValues.put(webscraperDatabase.TITLES, titles);
        contentValues.put(webscraperDatabase.INFO, info);
        contentValues.put(webscraperDatabase.DESCRIPTION, desc);
        long id = db.insert(webscraperDatabase.TABLE_NAME,null,contentValues);
        return id;
    }
    public void getAllData(){
        SQLiteDatabase db = database.getWritableDatabase();
        String[] columns = {webscraperDatabase.UID, webscraperDatabase.LINKS
                ,webscraperDatabase.IMAGES,webscraperDatabase.TITLES
                ,webscraperDatabase.INFO,webscraperDatabase.DESCRIPTION};

        Cursor cursor =  db.query(webscraperDatabase.TABLE_NAME, columns, null, null, null, null, null);

        int rowLink = cursor.getColumnIndex(webscraperDatabase.LINKS);
        int rowTitle = cursor.getColumnIndex(webscraperDatabase.TITLES);
        int rowInfo = cursor.getColumnIndex(webscraperDatabase.INFO);
        int rowDesc = cursor.getColumnIndex(webscraperDatabase.DESCRIPTION);
        int rowImage = cursor.getColumnIndex(webscraperDatabase.IMAGES);

        while(cursor.moveToNext()){
            System.out.println(cursor.getString(rowLink));
            System.out.println(cursor.getString(rowTitle));

            eventLinks.add(cursor.getString(rowLink));
            eventTitles.add(cursor.getString(rowTitle));
            eventInfos.add(cursor.getString(rowInfo));
            eventDescs.add(cursor.getString(rowDesc));
            eventImages.add(cursor.getString(rowImage));
        }

    }

    static class webscraperDatabase extends SQLiteOpenHelper{
        private static final String DATABASE_NAME = "WebScraperDatabase";
        private static final String TABLE_NAME = "APPDATA";
        private static final String UID = "_id";
        private static final String LINKS = "Links";
        private static final String IMAGES = "Images";
        private static final String TITLES = "Titles";
        private static final String INFO = "Info";
        private static final String DESCRIPTION = "Description";
        private Context context;
        private static int DATABASE_VERSION = 1;
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+LINKS+" VARCHAR(255), "+IMAGES+" VARCHAR(255), "+TITLES+" VARCHAR(255), "+INFO+" VARCHAR(255), "+DESCRIPTION+" VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

        public webscraperDatabase(Context context){
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
            this.context = context;
            Log.d("databaseCreated", "DATABASE HAS BEEN CREATED!");
            Log.d("Table",CREATE_TABLE);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        /* this method creates the first instance of the database, so you need to fill this with tables
        and data from the arrays in main activity.
         */
            try {
                db.execSQL(CREATE_TABLE);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            Log.d("databaseFullyCreated", "DATABASE HAS BEEN CREATED THROUGH ON CREATE!");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*this will upgrade the database, so when the refresh button is called the new
        data from the webscraper will be re added. and old ones will need to be removed so that we can
        keep the size down. call on create at the end with the db name in parameters
         */
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
