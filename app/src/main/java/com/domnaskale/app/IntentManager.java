package com.domnaskale.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

public class IntentManager {

    private static final int dns_prayer = 1;
    private static final int dns_news = 2;
    private static final int dns_info = 3;
    private static final int app_main = 4;
    private static final int dns_contact = 5;
    private static final int dns_groupsmat = 6;


    public static Intent itemSelected(MenuItem item, Context baseContext, Activity activity_contact) {
        Intent intent;

        switch (item.getOrder()) {
            case app_main:
                //Toast.makeText(getBaseContext(),R.string.Menu_app_main,Toast.LENGTH_SHORT).show();
                intent = new Intent(activity_contact, MainScreen.class);
                return intent;
            case dns_prayer:
                //Toast.makeText(getBaseContext(),R.string.Menu_dns_prayer,Toast.LENGTH_SHORT).show();
                intent = new Intent(activity_contact, Activity_dns_prayer.class);
                return intent;
            case dns_news:
                //Toast.makeText(getBaseContext(),R.string.Menu_dns_contact,Toast.LENGTH_SHORT).show();
                intent = new Intent(activity_contact, Activity_dns_news.class);
                return intent;
            case dns_info:
                //Toast.makeText(getBaseContext(),R.string.Menu_dns_info,Toast.LENGTH_SHORT).show();
                intent = new Intent(activity_contact, Activity_dns_info.class);
                return intent;
            case dns_contact:
                //Toast.makeText(getBaseContext(),R.string.Menu_dns_contact,Toast.LENGTH_SHORT).show();
                intent = new Intent(activity_contact, Activity_contact.class);
                return intent;
            case dns_groupsmat:
                //Toast.makeText(getBaseContext(),R.string.Menu_dns_groupsmat,Toast.LENGTH_SHORT).show();
                intent = new Intent(activity_contact, Activity_dns_groupsmat.class);
                return intent;
            default:
                Toast.makeText(baseContext, "Default option executed", Toast.LENGTH_SHORT).show();
                break;
        }
        return null;
    }
}
