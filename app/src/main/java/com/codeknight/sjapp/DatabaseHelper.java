package com.codeknight.sjapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 2;  // Increment the version number

    // Table name and columns
    public static final String TABLE_NAME = "user";
    public static final String KEY_ID = "_id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email"; // Added this line
    public static final String KEY_PASSWORD = "password";

    // Create table query
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
//                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_USERNAME + " TEXT, " +
                    KEY_EMAIL + " TEXT PRIMARY KEY, " +
                    KEY_PASSWORD + " TEXT);";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
        // Typically used to drop and recreate the table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Additional methods for CRUD operations

    // Example of inserting a record
    public long insertRecord(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);

        long newRowId = db.insert(TABLE_NAME, null, values);

        db.close();
        return newRowId;
    }

    // Example of reading records
    public Cursor readRecords() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_ID, KEY_USERNAME, KEY_EMAIL, KEY_PASSWORD};
        return db.query(TABLE_NAME, columns, null, null, null, null, null);
    }

    // Example of updating a record
    public int updateRecord(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, newPassword);

        String selection = KEY_EMAIL + " = ?";
        String[] selectionArgs = {email};

        int count = db.update(TABLE_NAME, values, selection, selectionArgs);

        db.close();
        return count;
    }

    // Example of deleting a record
    public int deleteRecord(String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = KEY_EMAIL + " = ?";
        String[] selectionArgs = {email};

        int count = db.delete(TABLE_NAME, selection, selectionArgs);

        db.close();
        return count;
    }
}
