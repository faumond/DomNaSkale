package com.domnaskale.app;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class Activity_dns_groupsmat extends AppCompatActivity {
    String HTML_WebPage = "";
    String[][] MaterialsLibrary;
    int MaterialsLastChoice = 1;

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

        // pobranie danych z serwera
        new FileDownloadTask().execute();
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

    public String[][] DivideMaterialTable(String[] MaterialTable){
        String[][] DividedMaterials = new String[MaterialTable.length][4];


        for (int i=0; i<MaterialTable.length; i++){
            DividedMaterials[i] = MaterialTable[i].split("\",\"");
            if (i>0) {
                DividedMaterials[i][0] = DividedMaterials[i][0].substring( 7);
                DividedMaterials[i][1] = DividedMaterials[i][1].substring( 9);
                DividedMaterials[i][2] = DividedMaterials[i][2].substring( 9);
                DividedMaterials[i][3] = DividedMaterials[i][3].substring(19, 19+19);
            }
        }

        return DividedMaterials;
    }

    public void ReadServerResults() {

        // wyświetlenie pobranego testu na ekranie
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    if(HTML_WebPage.length()<5)
                    {
                        Intent myIntent = new Intent(Activity_dns_groupsmat.this, Activity_dns_login.class);
                        Activity_dns_groupsmat.this.startActivity(myIntent);
                    }

                    String[] MaterialTable = HTML_WebPage.split("\\{");
                    MaterialsLibrary = DivideMaterialTable(MaterialTable);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void UpdateSpinner() {
        // wyświetlenie pobranego testu na ekranie
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    // Array of choices
                    List<String> spinner_choices = new ArrayList<String>();

                    for (int i=1; i<MaterialsLibrary.length; i++){
                        spinner_choices.add(MaterialsLibrary[i][1]);
                    }
                    // Selection of the spinner
                    final Spinner spinner = (Spinner) findViewById(R.id.spnMaterialsGroups);

                    // Application of the Array to the Spinner
                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinner_choices);
                    spinner.setAdapter(spinnerArrayAdapter);
                    spinnerArrayAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);

                    spinnerArrayAdapter.notifyDataSetChanged();

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1, int id, long position) {
                            // ta metoda wykonuje się za każdym razem, gdy zostanie wybrany jakiś element z naszej listy
                            MaterialsLastChoice = spinner.getSelectedItemPosition()+1;

                            if(MaterialsLastChoice>0) {
                                TextView tvMaterialText = (TextView) findViewById(R.id.webmaterials);
                                String ChosenText = MaterialsLibrary[MaterialsLastChoice][2];
                                String napis = Integer.toString(MaterialsLastChoice);

                                assert ChosenText != null;
                                ChosenText = ChosenText.replaceAll("&amp*","");
                                tvMaterialText.setText(Html.fromHtml(ChosenText).toString());
                                spinner.setSelection(spinnerArrayAdapter.getPosition(Long.toString(position)));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // ta metoda wykonuje sie gdy lista zostanie wybrana, ale nie zostanie wybrany żaden element z listy

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class FileDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String DownloadLinkURL = "http://domnaskale.eu/api/?accessKey=" + ReadAccessCode() + "&smallGroupMaterials=true";

                // definicja połączenia
                URL dataURL = new URL(DownloadLinkURL);

                // prośba o udzielenie uprawnień do zapisu
                isStoragePermissionGranted();

                HttpURLConnection connection = (HttpURLConnection) dataURL.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                connection.connect();

                // pobranie pliku
                HttpURLConnection urlConnection = (HttpURLConnection) dataURL.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    try {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buf = new byte[4096];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        HTML_WebPage = out.toString("UTF-8");

                        out.close();
                        in.close();

                        ReadServerResults();
                        UpdateSpinner();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } finally {

                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}

