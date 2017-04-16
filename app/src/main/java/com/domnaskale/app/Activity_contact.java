package com.domnaskale.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static com.domnaskale.app.IntentManager.NO_CHANGE;

public class Activity_contact extends AppCompatActivity {
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
        try {
            super.onCreate(savedInstanceState);
            setContentView(com.domnaskale.app.R.layout.activity_contact);

            // clickable button - facebook link
            ImageView img_fb = (ImageView) findViewById(R.id.fb);
            assert img_fb != null;
            img_fb.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/wmedns/"));
                    startActivity(browserIntent);
                }
            });

            // clickable button - webpage link
            ImageView img_www = (ImageView) findViewById(R.id.www);
            assert img_www != null;
            img_www.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.domnaskale.eu"));
                    startActivity(browserIntent);
                }
            });

            // clickable button - email link
            ImageView img_mail = (ImageView) findViewById(R.id.mail);
            assert img_mail != null;
            img_mail.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("mailto:wroclaw.sne@gmail.com"));
                    startActivity(browserIntent);
                }
            });

            // clickable button - email link
            Button btnLogout = (Button) findViewById(R.id.logoutbutton);
            assert btnLogout != null;
            btnLogout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SaveAccessCode("");
                    Toast.makeText(Activity_contact.this, "Wylogowano ze strony www", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(NullPointerException ex)
        {
            ex.printStackTrace();
        }
    }

    private void SaveAccessCode(String strArg){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("WWW_ACCESS_CODE",strArg);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentManager.changeAllFields(findViewById(R.id.activity_contact), NO_CHANGE);
    }
}
