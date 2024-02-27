package com.codeknight.sjapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DatabaseCrudActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_crud);

        databaseHelper = new DatabaseHelper(this);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void createRecord(View view) {
        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            String username = editTextUsername.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Validate email and password format
            if (!isValidEmail(email)) {
                showToast("Invalid email format");
                return;
            }

            if (!isValidPassword(password)) {
                showToast("Invalid password format");
                return;
            }

            // Check if the email already exists in the database
            if (isEmailExists(email)) {
                showToast("Email already exists");
                return;
            }

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.KEY_USERNAME, username);
            values.put(DatabaseHelper.KEY_PASSWORD, password);
            values.put(DatabaseHelper.KEY_EMAIL, email);

            long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);

            if (newRowId != -1) {
                showToast("Record Created with ID: " + newRowId);
            } else {
                showToast("Error creating record");
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            showToast("An error occurred: " + e.getMessage()); // Log the exception message
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    public void readRecord(View view) {
        // Instead of displaying in Toast, start DisplayDataActivity
        Intent intent = new Intent(this, DisplayDataActivity.class);
        startActivity(intent);
    }

    public void updateRecord(View view) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_PASSWORD, password);

        String selection = DatabaseHelper.KEY_USERNAME + " = ?";
        String[] selectionArgs = {username};

        int count = db.update(DatabaseHelper.TABLE_NAME, values, selection, selectionArgs);

        if (count > 0) {
            Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No matching record found for update", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public void deleteRecord(View view) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String email = editTextEmail.getText().toString().trim();

        // Validate email format
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email matches an existing record in the database
        String selection = DatabaseHelper.KEY_EMAIL + " = ?";
        String[] selectionArgs = {email};

        int count = db.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);

        if (count > 0) {
            Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No matching record found for delete", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }


    private boolean isValidEmail(String email) {

        return email.contains("@");
    }

    private boolean isValidPassword(String password) {

        return password.length() >= 6;
    }

    private boolean isEmailExists(String email) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME +
                " WHERE " + DatabaseHelper.KEY_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
