package com.example.movietimez;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.movietimez.Models.User;
import com.google.common.hash.HashCode;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MovieTime.db";

    public static final String USERS_TABLE= "users";
    public static final String USERS_NAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_PHOTO = "photo";

    public static final String LIKES_TABLE= "likes";
    public static final String LIKES_USER = "username";
    public static final String LIKES_MOVIE = "movie";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USERS_TABLE + " (USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, PHOTO TEXT)");

        db.execSQL("CREATE TABLE " + LIKES_TABLE + " (LIKE_ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, MOVIE TEXT, FOREIGN KEY (USERNAME)" +
                "REFERENCES "+ USERS_TABLE + "(USERNAME))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LIKES_TABLE);
        this.onCreate(db);
    }

    public boolean saveUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_NAME, username);
        contentValues.put(USERS_PASSWORD, password);
        long result = db.insert(USERS_TABLE, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllFavourites(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+ LIKES_TABLE +" where username = ? ",
                new String[]{username});
        return res;
    }

//    public int removeData(String hobbyType){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_NAME, "HobbyType = ?",  new String[] {hobbyType} );
//    }

//    public int renameData(String hobbyTypeOLD, String hobbyTypeNEW){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1, hobbyTypeNEW);
//        return db.update(TABLE_NAME, contentValues,"HobbyType = ?",  new String[] {hobbyTypeOLD} );
//    }

    public Cursor findUserByUsername(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+ USERS_TABLE + " where " + USERS_NAME +" = ?", new String[]{username});

        return res;
    }

    public boolean userExists(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+ USERS_TABLE + " where " + USERS_NAME +" = ?", new String[]{username});
        if (res.getCount() == 0){
            return false;
        }

        return true;
    }

    public void addToFavourites(int movieId, String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+ LIKES_TABLE +" where username = ? and movie =? ",
                new String[]{username, String.valueOf(movieId)});

        if(res.getCount() <= 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(LIKES_USER, username);
            contentValues.put(LIKES_MOVIE, movieId);
            db.insert(LIKES_TABLE, null, contentValues);
        }
    }

    public void saveProfilePicture(String username, Uri data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_PHOTO, data.toString());
        db.update(USERS_TABLE, contentValues,USERS_NAME + " = ?",  new String[] {username} );
    }

    public void changePassword(String username, String hashCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_PASSWORD, hashCode);
        db.update(USERS_TABLE, contentValues,USERS_NAME + " = ?",  new String[] {username} );
    }
}
