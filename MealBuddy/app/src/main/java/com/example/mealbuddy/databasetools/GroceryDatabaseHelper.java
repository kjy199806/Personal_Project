package com.example.mealbuddy.databasetools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GroceryDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "GroceryDatabaseHelper";
    private static final String DATABASE_NAME = "groceryList";
    private static final String TABLENAME ="grocery";
    private static final String COL1 = "IngredientName";
    private static final String COL2 = "Quantity";

    public GroceryDatabaseHelper(Context context){

        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String SavedTable = "CREATE TABLE " + TABLENAME
                + " ("
                + COL1 + " TEXT,"
                + COL2 + " TEXT"
                + ")";

        db.execSQL(SavedTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i , int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }

    public boolean addData(String table,String IngredientName, String Quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, IngredientName);
        contentValues.put(COL2, Quantity);

        long result = db.insert(table, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteAllRows(String tablename){
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

    public void deleteSpecificRows(String tablename, String ingredientName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tablename, "IngredientName = ?", new String[]{ingredientName});
        db.execSQL("VACUUM;");
        db.close();
    }


    public boolean doesExist(String tablename, String ingredientName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+ tablename + " WHERE DATE = ? AND DAYTIME = ? AND Title = ?" , new String[]{ingredientName});
        return data==null;
    }
}
