package com.domnaskale.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Activity_dns_prayer extends AppCompatActivity {
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
        setContentView(com.domnaskale.app.R.layout.activity_dns_prayer);
    }

}
