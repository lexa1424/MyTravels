package com.example.alexeypc.mytravels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBase {

    private static final String DATABASE_NAME = "travels.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "data";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_LNG = "longitude";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase mDB;

    private static DataBase instance = null;

    public static DataBase getInstance(Context context){
        if(instance == null)
        {
            instance = new DataBase(context);
        }
        return instance;
    }

    private DataBase(Context context) {
        this.context = context;
    }

    public void open() {
        if (DBHelper == null || !mDB.isOpen()) {
            DBHelper = new DatabaseHelper(context, DATABASE_NAME, null, SCHEMA);
            mDB = DBHelper.getWritableDatabase();
        }

    }

    public void close() {
        if (DBHelper != null) DBHelper.close();
    }

    public Cursor getAllData() {
        return mDB.query(TABLE, null, null, null, null, null, null);
    }

    public void addRow(String date, String lat, String lng) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_LAT, lat);
        cv.put(COLUMN_LNG, lng);
        mDB.insert(TABLE, null, cv);
    }

    public String getCoord(String date, String column){
        //String[] columns = new String[] { column };
        String selection = COLUMN_DATE + " = ?";
        String[] selectionArgs = new String[] { date };
        Cursor cursor = mDB.query(TABLE, null, selection, selectionArgs, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
            String coord = cursor.getString(cursor.getColumnIndex(column));
            return coord;
        }
        return null;
    }


    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DATE + " TEXT, "
                    + COLUMN_LAT + " TEXT, "
                    + COLUMN_LNG + " TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
