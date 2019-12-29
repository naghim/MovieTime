package com.example.movietimez;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MovieTime.db";
    public static final String TABLE_NAME = "hobby_table";
    public static final String COL_1 = "HobbyType";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (HOBBYTYPE TEXT PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public boolean insertData(String hobbyType){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, hobbyType);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME,null);
        return res;
    }

    public int removeData(String hobbyType){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "HobbyType = ?",  new String[] {hobbyType} );
    }

    public int renameData(String hobbyTypeOLD, String hobbyTypeNEW){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, hobbyTypeNEW);
        return db.update(TABLE_NAME, contentValues,"HobbyType = ?",  new String[] {hobbyTypeOLD} );
    }

    ////////// mocks ////////////
    public User findUserByUsername()
    {
        return new User("test", "test");
    }

    public boolean userExists(String username, String password)
    {
        return true;
    }

    public void saveUser(String username, String toString) {
        // save user
    }
}
