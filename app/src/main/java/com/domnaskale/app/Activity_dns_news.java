package com.domnaskale.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static com.domnaskale.app.IntentManager.NO_CHANGE;

public class Activity_dns_news extends AppCompatActivity {
    String DownloadLinkURL = "http://www.domnaskale.eu";
    String HTML_WebPage = "";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(IntentManager.itemSelected(item, getBaseContext(), this));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(com.domnaskale.app.R.menu.options_menu, menu);

        new FileDownloadTask().execute();

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.domnaskale.app.R.layout.activity_dns_news);
    }

    public void ReadNewsFile() {

        // wyświetlenie pobranego testu na ekranie
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    String[] HTML_Lines = HTML_WebPage.split("<div class=\"newsflash\">");
                    char prefix;
                    String strNewLine = HTML_Lines[2]; // line that contains "newsflash"
                    int end = strNewLine.indexOf("</div>");
                    prefix = strNewLine.charAt(62);

                    if (Character.isLetter(prefix)) {
                        strNewLine = strNewLine.substring(0, end);
                    } else {
                        strNewLine = strNewLine.substring(0, end);
                    }

                    TextView textView = (TextView) findViewById(com.domnaskale.app.R.id.News);
                    textView.setText(Html.fromHtml(strNewLine));
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
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
                // definicja połączenia
                URL dataURL = new URL(DownloadLinkURL);
                String FolderName = "DomNaSkale";

                // prośba o udzielenie uprawnień do zapisu
                isStoragePermissionGranted();

                HttpURLConnection connection = (HttpURLConnection) dataURL.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                connection.connect();

                // utworzenie folderu / sprawdzenie, czy istnieje
                File rootDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FolderName);

                if (!rootDirectory.exists()) {
                    rootDirectory.mkdirs();
                }

                String nameOfFile = "News.html";
                File file = new File(rootDirectory, nameOfFile);

                // pobranie pliku
                HttpURLConnection urlConnection = (HttpURLConnection) dataURL.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    try {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        HTML_WebPage = out.toString("UTF-8");

                        out.close();
                        in.close();

                        ReadNewsFile();

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

    @Override
    protected void onResume() {
        super.onResume();
        IntentManager.changeAllFields(findViewById(R.id.activity_dns_news), NO_CHANGE);
    }

}
