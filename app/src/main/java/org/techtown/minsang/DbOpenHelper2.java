package org.techtown.minsang;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper2 {

    private static final String DATABASE_NAME = "Database2(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreateDB1._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.CreateDB1._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper2(Context context) {
        this.mCtx = context;
    }

    public DbOpenHelper2 open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void close() {
        mDB.close();
    }

///////////////////////////////////////////////////////////////////////3대

    // Insert DB
    public long insertColumn(double date, double squart, double benchpress, double deadlift, double total) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB1.DATE, date);
        values.put(DataBases.CreateDB1.SQUART, squart);
        values.put(DataBases.CreateDB1.BENCHPRESS, benchpress);
        values.put(DataBases.CreateDB1.DEADLIFT, deadlift);
        values.put(DataBases.CreateDB1.TOTAL, total);
        return mDB.insert(DataBases.CreateDB1._TABLENAME0, null, values);
    }

    // Update DB
    public boolean updateColumn(long id, double date, double squart, double benchpress, double deadlift, double total) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.DATE, date);
        values.put(DataBases.CreateDB1.SQUART, squart);
        values.put(DataBases.CreateDB1.BENCHPRESS, benchpress);
        values.put(DataBases.CreateDB1.DEADLIFT, deadlift);
        values.put(DataBases.CreateDB1.TOTAL, total);
        return mDB.update(DataBases.CreateDB1._TABLENAME0, values, "_id=" + id, null) > 0;
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(DataBases.CreateDB1._TABLENAME0, null, null);
    }

    // Delete DB
    public boolean deleteColumn(long id) {
        return mDB.delete(DataBases.CreateDB1._TABLENAME0, "_id=" + id, null) > 0;
    }

    // Select DB
    public Cursor selectColumns() {
        return mDB.query(DataBases.CreateDB1._TABLENAME0, null, null, null, null, null, null);
    }

    // sort by column
    public Cursor sortColumn(String sort) {
        Cursor c = mDB.rawQuery("SELECT * FROM usertable"+Login.loginpos+" ORDER BY " + sort + ";", null);
        return c;
    }
}
