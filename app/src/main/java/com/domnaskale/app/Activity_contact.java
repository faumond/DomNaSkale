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

public class Activity_contact extends AppCompatActivity {
    private static final int dns_prayer   = 1;
    private static final int dns_news     = 2;
    private static final int dns_info     = 3;
    private static final int app_main     = 4;
    private static final int dns_contact  = 5;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch(item.getOrder()){
            case app_main:
                //Toast.makeText(getBaseContext(),R.string.Menu_app_main,Toast.LENGTH_SHORT).show();
                intent = new Intent(this, MainScreen.class);
                startActivity(intent);
                break;
            case dns_prayer:
                //Toast.makeText(getBaseContext(),R.string.Menu_dns_prayer,Toast.LENGTH_SHORT).show();
                intent = new Intent(this, Activity_dns_prayer.class);
                startActivity(intent);
                break;
            case dns_news:
                //Toast.makeText(getBaseContext(),R.string.Menu_dns_contact,Toast.LENGTH_SHORT).show();
                intent = new Intent(this, Activity_dns_news.class);
                startActivity(intent);
                break;
            case dns_info:
                //Toast.makeText(getBaseContext(),R.string.Menu_dns_info,Toast.LENGTH_SHORT).show();
                intent = new Intent(this, Activity_dns_info.class);
                startActivity(intent);
                break;
            case dns_contact:
                //Toast.makeText(getBaseContext(),R.string.Menu_dns_contact,Toast.LENGTH_SHORT).show();
                intent = new Intent(this, Activity_contact.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(getBaseContext(),"Default option executed",Toast.LENGTH_SHORT).show();
                break;
        }
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
    }

}
