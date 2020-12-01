package com.example.androidlabs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


//android:theme="@style/Theme.AndroidExamples"

public class TestToolbar extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView==null) {
            Log.e("TestToolbar", " NULL");
        }
        else{
            navigationView.setNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.Star:
                        Intent cr = new Intent(TestToolbar.this, ChatRoomActivity.class);
                        startActivity(cr);
                        break;
                    case R.id.poo:
                        Intent wf = new Intent(TestToolbar.this, WeatherForecast.class);
                        startActivity(wf);
                        break;
                    case R.id.crown:
                        setResult(500);
                        finish();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;

            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.Star:
                Toast ctoast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.Star_toast_message), Toast.LENGTH_SHORT);
                ctoast.show();
                break;
            case R.id.poo:
                Toast ptoast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.poo_toast_message), Toast.LENGTH_SHORT);
                ptoast.show();
                break;
            case R.id.crown:
                Toast stoast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.crown_toast_message), Toast.LENGTH_SHORT);
                stoast.show();
                break;
            case R.id.overflow:
                Toast otoast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.overflow_toast_message), Toast.LENGTH_SHORT);
                otoast.show();
                break;
        }
        return true;
    }



}
