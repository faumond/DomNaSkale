package com.domnaskale.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static com.domnaskale.app.IntentManager.UPPER;

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
        }
        catch(NullPointerException ex)
        {
            ex.printStackTrace();
        }
        IntentManager.changeAllFields(findViewById(R.id.activity_contact), UPPER);
    }

}
