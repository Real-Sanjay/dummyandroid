package com.codeknight.sjapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayDataActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        databaseHelper = new DatabaseHelper(this);

        displayData();
    }

    private void displayData() {
        // Read records from the database
        Cursor cursor = databaseHelper.readRecords();

        // Display the data in a TableLayout
        TableLayout tableLayout = findViewById(R.id.tableLayoutData);

        // Add heading row
        TableRow headingRow = new TableRow(this);
        headingRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TextView headingUser = createHeadingTextView("User");
        TextView headingEmail = createHeadingTextView("Email");
        TextView headingPassword = createHeadingTextView("Password");

        headingRow.addView(headingUser);
        headingRow.addView(headingEmail);
        headingRow.addView(headingPassword);

        // Add the heading row to the TableLayout
        tableLayout.addView(headingRow);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int usernameIndex = cursor.getColumnIndex(DatabaseHelper.KEY_USERNAME);
                int emailIndex = cursor.getColumnIndex(DatabaseHelper.KEY_EMAIL);
                int passwordIndex = cursor.getColumnIndex(DatabaseHelper.KEY_PASSWORD);

                // Check if columns exist in the cursor
                if (usernameIndex >= 0 && emailIndex >= 0 && passwordIndex >= 0) {
                    String username = cursor.getString(usernameIndex);
                    String email = cursor.getString(emailIndex);
                    String password = cursor.getString(passwordIndex);

                    // Create a new row and add TextViews
                    TableRow row = new TableRow(this);
                    row.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

                    TextView usernameTextView = createTextView(username);
                    TextView emailTextView = createTextView(email);
                    TextView passwordTextView = createWrappedTextView(password);

                    row.addView(usernameTextView);
                    row.addView(emailTextView);
                    row.addView(passwordTextView);

                    // Add the row to the TableLayout
                    tableLayout.addView(row);
                } else {
                    // Handle the case when one or more columns are not found in the cursor
                    Log.e("DisplayDataActivity", "One or more columns not found in the cursor");
                    // You can show an error message, log more details, or take other actions
                }
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            // Handle the case when no records are found
            TextView noRecordsTextView = new TextView(this);
            noRecordsTextView.setText("No records found");
            noRecordsTextView.setGravity(Gravity.CENTER);
            tableLayout.addView(noRecordsTextView);
        }
    }

    private TextView createHeadingTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(18);
        textView.setTypeface(null, android.graphics.Typeface.BOLD);
        textView.setPadding(16, 8, 16, 8);
        return textView;
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(16);
        textView.setPadding(16, 8, 16, 8);
        return textView;
    }

    private TextView createWrappedTextView(String text) {
        TextView textView = createTextView(text);
        textView.setLayoutParams(new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f));
        return textView;
    }
}
