package com.example.mealbuddy.databasetools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class databasehelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "MealBuddies3";
    private static final String TABLENAME ="ALLDAYS";
    private static final String COL1 = "Title"; //0
    private static final String COL2 = "Description";
    private static final String COL3 = "INGRDLIST";
    private static final String COL4 = "STEPS";
    private static final String COL5 = "INGIMG";
    private static final String COL6 = "TITLEIMG";
    private static final String COL7 = "INSIDEIMG";
    private static final String COL8 = "DAYTIME";
    private static final String COL9 = "DATE";

    public databasehelper(Context context){

        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String SavedTable = "CREATE TABLE " + TABLENAME
                + " ("
                + COL1 + " TEXT,"
                + COL2 + " TEXT,"
                + COL3 + " TEXT,"
                + COL4 + " TEXT,"
                + COL5 + " TEXT,"
                + COL6 + " TEXT,"
                + COL7 + " TEXT,"
                + COL8 + " TEXT,"
                + COL9 + " TEXT"
                + ")";

        db.execSQL(SavedTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i , int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);

        onCreate(db);
    }

    public boolean addData(String table,String title, String description, String ingrdlist, String fullsteps, String INGIMG, String TITLIMG, String INSIDIMG, String daytime, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, title);
        contentValues.put(COL2, description);
        contentValues.put(COL3, ingrdlist);
        contentValues.put(COL4, fullsteps);
        contentValues.put(COL5, INGIMG);
        contentValues.put(COL6, TITLIMG);
        contentValues.put(COL7, INSIDIMG);
        contentValues.put(COL8, daytime);
        contentValues.put(COL9, date);
        Log.d(TAG, "addData: Adding " + title +" to " + table);

        long result = db.insert(table, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteallrows(String tablename){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ tablename);
        db.close();
    }

    public Cursor getData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + name;
        Cursor data = db.rawQuery(query, null);
        db.execSQL("VACUUM;");

        return data;
    }

    public Cursor getDates(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT DISTINCT DATE FROM " + name;
        Cursor data = db.rawQuery(query, null);
        db.execSQL("VACUUM;");

        return data;
    }

    public Cursor getItemID(String tablename, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ tablename + " WHERE ROWID = ?" , new String[]{name});
        return data;
    }

    public Cursor getItemByDayTime(String tablename, String daytime){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ tablename + " WHERE DAYTIME = ?" , new String[]{daytime});
        return data;
    }

    public Cursor getItemByDateandTime(String tablename, String date, String daytime){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ tablename + " WHERE DATE = ? AND DAYTIME = ?" , new String[]{date, daytime});
        return data;
    }

    public void deletespecificrows(String tablename, String somthing, String daytime, String datetime){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tablename, "Title = ? AND DAYTIME = ? AND DATE = ?", new String[]{somthing, daytime, datetime});
        db.execSQL("VACUUM;");
        db.close();
    }

    public void deleteallrowsbydate(String tablename, String todaydate){
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("delete from "+ tablename + "WHERE DATE LIKE = ?" , new String[]{todaydate});
//        db.close();
        String whereClause = "DATE=?";
        db.delete(tablename, whereClause, new String[]{todaydate} );
        db.execSQL("VACUUM;");
        db.close();
    }

    public Cursor getItembyName(String tablename, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ tablename + " WHERE Title = ?" , new String[]{name});
        return data;
    }

    public Cursor findifexist(String tablename, String date, String daytime, String recipyname){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ tablename + " WHERE DATE = ? AND DAYTIME = ? AND Title = ?" , new String[]{date, daytime, recipyname});
        return data;
    }
}
