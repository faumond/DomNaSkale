package com.domnaskale.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.domnaskale.app.IntentManager.SMALLER;
import static com.domnaskale.app.IntentManager.UPPER;

public class Activity_configuration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        IntentManager.changeAllFields(findViewById(R.id.activity_configuration), UPPER);
    }

    public void biggerFont(View view){
        IntentManager.changeAllFields(findViewById(R.id.activity_configuration), UPPER);
    }

    public void smallerFont(View view){
        IntentManager.changeAllFields(findViewById(R.id.activity_configuration), SMALLER);
    }
}
