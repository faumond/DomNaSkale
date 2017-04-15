package com.domnaskale.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import static com.domnaskale.app.IntentManager.NO_CHANGE;


public class Activity_calendar extends AppCompatActivity {

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
        setContentView(com.domnaskale.app.R.layout.activity_dns_info);

        WebView wvCalendar = (WebView) findViewById(R.id.webview);

        try {
            if (wvCalendar != null) {
                wvCalendar.setWebChromeClient(new WebChromeClient());
                wvCalendar.getSettings().setJavaScriptEnabled(true);
                wvCalendar.loadUrl("https://calendar.google.com/calendar/embed?src=7gf9dgphjmcfsn5tqstv84qkos%40group.calendar.google.com&ctz=Europe/Warsaw&mode=WEEK&showPrint=0&showTabs=0&showCalendars=0");
            }
        }
        catch (Exception e) {
            Toast.makeText(Activity_calendar.this, "Napotkano błąd podczas próby wyświetlenia kalendarza", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentManager.changeAllFields(findViewById(R.id.activity_dns_info), NO_CHANGE);
    }
}
