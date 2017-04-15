package com.domnaskale.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import static com.domnaskale.app.IntentManager.NO_CHANGE;

public class MainScreen extends AppCompatActivity {
    private static final int dns_prayer   = 1;
    private static final int dns_news     = 2;
    private static final int dns_info     = 3;
    private static final int app_main     = 4;
    private static final int dns_contact  = 5;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentManager.changeAllFields(findViewById(R.id.activity_main_screen), NO_CHANGE);
    }
}
