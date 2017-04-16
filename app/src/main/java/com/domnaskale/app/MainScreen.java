package com.domnaskale.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import static com.domnaskale.app.IntentManager.NO_CHANGE;
import static com.domnaskale.app.IntentManager.setFontSize;


public class MainScreen extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(IntentManager.itemSelected(item,getBaseContext(),this));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(com.domnaskale.app.R.menu.options_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.domnaskale.app.R.layout.activity_main_screen);
        SharedPreferences sharedPref = getSharedPreferences("screenConfig", Context.MODE_PRIVATE);
        setFontSize(sharedPref, sharedPref.edit());
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentManager.changeAllFields(findViewById(R.id.activity_main_screen), NO_CHANGE);
    }
}
