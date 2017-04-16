package com.domnaskale.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class Activity_dns_groupsmat extends AppCompatActivity {

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
        String strAccessCode;

        super.onCreate(savedInstanceState);
        setContentView(com.domnaskale.app.R.layout.activity_dns_groupsmat);

        // odczyt kodu dostępu
        strAccessCode = ReadAccessCode();
        // jeżeli kod jest pusty, zaloguj
        if (strAccessCode.length() == 0){
            Intent myIntent = new Intent(Activity_dns_groupsmat.this, Activity_dns_login.class);
            Activity_dns_groupsmat.this.startActivity(myIntent);
        }else {
            // jeżeli kod nie jest pusty, to:

            WebView wvMaterials = (WebView) findViewById(R.id.wvMaterials);

            try {
                if (wvMaterials != null) {
                    wvMaterials.setWebChromeClient(new WebChromeClient());
                    wvMaterials.getSettings().setJavaScriptEnabled(true);
                    wvMaterials.loadUrl("http://domnaskale.eu/api/?accessKey=" + strAccessCode + "&smallGroupMaterials=true");
                }
                // uruchom stronę i pobierz kod html
            } catch (Exception e) {
                Toast.makeText(Activity_dns_groupsmat.this, "Wystąpił błąd podczas generowania zawartości strony", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String ReadAccessCode(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String strAccessCode = preferences.getString("WWW_ACCESS_CODE", "");
        //Toast.makeText(Activity_dns_groupsmat.this, "Kod:" + strAccessCode, Toast.LENGTH_SHORT).show();

        return strAccessCode;
    }

    private void SaveAccessCode(String strArg){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("WWW_ACCESS_CODE",strArg);
        editor.apply();
    }
}

