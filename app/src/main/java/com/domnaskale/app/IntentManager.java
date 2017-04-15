package com.domnaskale.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class IntentManager {

    private static final int dns_prayer = 1;
    private static final int dns_news = 2;
    private static final int dns_info = 3;
    private static final int app_main = 4;
    private static final int dns_contact = 5;
    private static final int dns_configuration = 6;
    public static final int UPPER = 1;
    public static final int NO_CHANGE = 0;
    public static final int SMALLER = -1;
    private static final String FONT_SIZE = "fontSize";
    private static float fontSize = 14;
    private static SharedPreferences.Editor sharedPreferences;


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
                intent = new Intent(activity_contact, Activity_calendar.class);
                return intent;
            case dns_contact:
                //Toast.makeText(getBaseContext(),R.string.Menu_dns_contact,Toast.LENGTH_SHORT).show();
                intent = new Intent(activity_contact, Activity_contact.class);
                return intent;
            case dns_configuration:
                //Toast.makeText(getBaseContext(),R.string.Menu_dns_contact,Toast.LENGTH_SHORT).show();
                intent = new Intent(activity_contact, Activity_configuration.class);
                return intent;
            default:
                Toast.makeText(baseContext, "Default option executed", Toast.LENGTH_SHORT).show();
                break;
        }
        return null;
    }

    public static void changeAllFields(View rootView, int fontChangeDirection) {
        int childs = ((ViewGroup) rootView).getChildCount();
        if (fontChangeDirection == 1) fontSize++;
        if (fontChangeDirection == -1) fontSize--;
        sharedPreferences.putFloat(FONT_SIZE, fontSize);
        sharedPreferences.commit();
        for (int i = 0; i < childs; i++) {
            changeGraphicalObject(((ViewGroup) rootView).getChildAt(i), fontSize);
        }
    }

    public static void changeGraphicalObject(View view, float fontSize) {
        try {
            if (view instanceof TextView) {
                ((TextView) view).setTextSize(fontSize);
            }
            if (view instanceof Button) {
                ((TextView) view).setTextSize(fontSize);
            }
        } catch (Exception e) {
        }
    }

    public static void setFontSize(SharedPreferences sharedPref, SharedPreferences.Editor edit) {
        sharedPreferences = edit;
        fontSize = sharedPref.getFloat(FONT_SIZE, 14f);
    }
}
