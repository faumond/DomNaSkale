package com.domnaskale.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity_dns_login extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(IntentManager.itemSelected(item,getBaseContext(),this));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dns_login);

        Button btnSave = (Button) findViewById(R.id.savebutton);
        final EditText edtAccessCode = (EditText) findViewById(R.id.accesscode);
        assert btnSave != null;
        assert edtAccessCode != null;
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveAccessCode(edtAccessCode.getText().toString());
                //finish();
                Intent myIntent = new Intent(Activity_dns_login.this, Activity_dns_groupsmat.class);
                Activity_dns_login.this.startActivity(myIntent);
            }
        });
    }

    private void SaveAccessCode(String strArg){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("WWW_ACCESS_CODE",strArg);
        editor.apply();
    }
}