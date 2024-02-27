package com.codeknight.sjapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnCreateContextMenuListener(this);

        // Set custom title for ActionBar
        getSupportActionBar().setTitle("SJMovieApp");



        // Check if the device is in portrait or landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            loadFragment(new WhiteFragment());
        } else {
            loadFragment(new DarkFragment());
        }
    }





    public void redirectToSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
    public void redirectToSecondActivity2(View view) {
        Intent intent = new Intent(this, message_activity.class);
        startActivity(intent);
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    public void openMapsActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
//    public void openGoogleMaps(View view) {
//        String googleMapsUrl = "https://www.google.com/maps";
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsUrl));
//        startActivity(intent);
//    }



    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        createOptionsMenu(menu);
//        return true;
//    }
//
//    private void createOptionsMenu(Menu menu) {
//        MenuItem item1 = menu.add(0, 0, 0, "Item 1");
//        item1.setIcon(R.mipmap.ic_launcher);
//        item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//
//        MenuItem item2 = menu.add(0, 1, 1, "Item 2");
//        item2.setIcon(R.mipmap.ic_launcher);
//        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//
//        MenuItem item3 = menu.add(0, 2, 2, "Item 3");
//        item3.setIcon(R.mipmap.ic_launcher);
//        item3.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//
//        MenuItem item4 = menu.add(0, 3, 3, "Item 4");
//        item4.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//
//        MenuItem item5 = menu.add(0, 4, 4, "Item 5");
//        item5.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//    }
//
//    private boolean menuChoice(MenuItem item) {
//        switch (item.getItemId()) {
//            case 0:
//                showToast("You clicked on Item 1");
//                return true;
//            case 1:
//                showToast("You clicked on Item 2");
//                return true;
//            case 2:
//                showToast("You clicked on Item 3");
//                return true;
//            case 3:
//                showToast("You clicked on Item 4");
//                return true;
//            case 4:
//                showToast("You clicked on Item 5");
//                return true;
//        }
//        return false;
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return menuChoice(item) || super.onOptionsItemSelected(item);
//    }
public void redirectToDatabaseCrudActivity(View view) {
    Intent intent = new Intent(this, DatabaseCrudActivity.class);
    startActivity(intent);
}
@Override
public void onCreateContextMenu(ContextMenu menu, View view,
                                ContextMenu.ContextMenuInfo menuInfo)
{
    super.onCreateContextMenu(menu, view, menuInfo);
    createMenu(menu);
}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    createMenu(menu);
    return true;
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return menuChoice(item);
    }
    private void createMenu(Menu menu) {
        MenuItem mnu1 = menu.add(0, 0, 0, "480p");
        {
            mnu1.setAlphabeticShortcut('a');
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "720p");
        {
            mnu2.setAlphabeticShortcut('b');
        }
        MenuItem mnu3 = menu.add(0, 2, 2, "1080p");
        {
            mnu3.setAlphabeticShortcut('c');
        }
//        MenuItem mnu4 = menu.add(0, 3, 3, "Item 4");
//        {
//            mnu4.setAlphabeticShortcut('d');
//        }
//        menu.add(0, 4, 4, "Item 5");
//        menu.add(0, 5, 5, "Item 6");
//        menu.add(0, 6, 6, "Item 7");
    }
    private boolean menuChoice(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this, "You clicked on Item 1",
                        Toast.LENGTH_LONG).show();
                return true;
            case 1:
                Toast.makeText(this, "You clicked on Item 2",
                        Toast.LENGTH_LONG).show();
                return true;
            case 2:
                Toast.makeText(this, "You clicked on Item 3",
                        Toast.LENGTH_LONG).show();
                return true;
            case 3:
                Toast.makeText(this, "You clicked on Item 4",
                        Toast.LENGTH_LONG).show();
                return true;
            case 4:
                Toast.makeText(this, "You clicked on Item 5",
                        Toast.LENGTH_LONG).show();
                return true;
            case 5:
                Toast.makeText(this, "You clicked on Item 6",
                        Toast.LENGTH_LONG).show();
                return true;
            case 6:
                Toast.makeText(this, "You clicked on Item 7",
                        Toast.LENGTH_LONG).show();
                return true;
        }
        return false;
    }
}



