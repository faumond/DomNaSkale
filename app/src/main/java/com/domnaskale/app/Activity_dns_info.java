package com.domnaskale.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Activity_dns_info extends AppCompatActivity {


    String DownloadLinkURL = "http://zaryczny.cba.pl/RecentCalendar.txt";

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
        super.onCreate(savedInstanceState);
        setContentView(com.domnaskale.app.R.layout.activity_dns_info);

        new FileDownloadTask().execute();

        ReadNewsFile();
    }

    public String ParseNewsFromFile(String inString){
        String outString, FltMessage;
        int index = -1;
        int index2;

        //<last-release>
        String strDateTime  = "00/00/0000 00:00:00";
        String strMaxEvents = "0";
        String strMaxBooks  = "0";
        String strMaxOthers = "0";

        if (inString.contains("lr-datetime")) {
            index = inString.indexOf("lr-datetime");
            strDateTime = inString.substring(index+13, index+32); inString = inString.substring(index+32);
        }
        if (inString.contains("max-events")) {
            index = inString.indexOf("max-events"); inString = inString.substring(index+10+2);
            index2 = inString.indexOf("\"");
            if (index2 > 0) {
                strMaxEvents = inString.substring(0, index2);
                inString = inString.substring(index2);
            }
        }
        if (inString.contains("max-books")) {
            index = inString.indexOf("max-books"); inString = inString.substring(index+9+2);
            index2 = inString.indexOf("\"");
            if (index2 > 0) {
                strMaxBooks = inString.substring(0, index2);
                inString = inString.substring(index2);
            }
        }
        if (inString.contains("max-other")) {
            index = inString.indexOf("max-other"); inString = inString.substring(index+9+2);
            index2 = inString.indexOf("\"");
            if (index2 > 0) {
                strMaxOthers = inString.substring(0, index2);
                inString = inString.substring(index2);
            }
        }
        if (index == -1) // -1 means "not found"
        {
            FltMessage = getResources().getString(com.domnaskale.app.R.string.KomunikatBledu);
            return outString = FltMessage;
        }
        //<event>
        ArrayList<String> lstEventId       = new ArrayList<String>();
        ArrayList<String> lstEventClass    = new ArrayList<String>();
        ArrayList<String> lstEventDateTime = new ArrayList<String>();
        ArrayList<String> lstEventPlace    = new ArrayList<String>();
        ArrayList<String> lstEventInfo     = new ArrayList<String>();
        ArrayList<String> lstEventDesc     = new ArrayList<String>();
        ArrayList<String> lstEventHtml     = new ArrayList<String>();

        for (int i = 0; i < Integer.parseInt(strMaxEvents); i++) {
            if (inString.contains("event-id")) {
                index = inString.indexOf("event-id"); inString = inString.substring(index+8+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstEventId.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (inString.contains("event-class")) {
                index = inString.indexOf("event-class"); inString = inString.substring(index+11+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    String strTmpClass = inString.substring(0, index2);
                    switch (strTmpClass) {
                        case "wro":  strTmpClass = getResources().getString(com.domnaskale.app.R.string.EventWroc); break;
                        case "sdm":  strTmpClass = getResources().getString(com.domnaskale.app.R.string.EventSDM);  break;
                        case "dns":  strTmpClass = getResources().getString(com.domnaskale.app.R.string.EventDNS);  break;
                        case "cath": strTmpClass = getResources().getString(com.domnaskale.app.R.string.EventCath); break;
                        case "sne":  strTmpClass = getResources().getString(com.domnaskale.app.R.string.EventSNE); break;
                        default:     break;
                    }
                    lstEventClass.add(strTmpClass);
                    inString = inString.substring(index2);
                }
            }
            if (inString.contains("event-datetime")) {
                index = inString.indexOf("event-datetime"); inString = inString.substring(index+14+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstEventDateTime.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (inString.contains("event-place")) {
                index = inString.indexOf("event-place"); inString = inString.substring(index+11+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstEventPlace.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (inString.contains("event-info")) {
                index = inString.indexOf("event-info"); inString = inString.substring(index+10+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstEventInfo.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (inString.contains("event-desc")) {
                index = inString.indexOf("event-desc"); inString = inString.substring(index+10+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstEventDesc.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (inString.contains("event-html")) {
                index = inString.indexOf("event-html"); inString = inString.substring(index+10+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstEventHtml.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (index == -1) // -1 means "not found"
            {
                FltMessage = getResources().getString(com.domnaskale.app.R.string.KomunikatBledu);
                return outString = FltMessage;
            }
        }

        //<books>
        ArrayList<String> lstBookId       = new ArrayList<String>();
        ArrayList<String> lstBookAuthor   = new ArrayList<String>();
        ArrayList<String> lstBookTitle    = new ArrayList<String>();
        ArrayList<String> lstBookDesc     = new ArrayList<String>();

        for (int i = 0; i < Integer.parseInt(strMaxBooks); i++) {
            if (inString.contains("book-id")) {
                index = inString.indexOf("book-id"); inString = inString.substring(index+7+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstBookId.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (inString.contains("book-author")) {
                index = inString.indexOf("book-author"); inString = inString.substring(index+11+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstBookAuthor.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (inString.contains("book-title")) {
                index = inString.indexOf("book-title"); inString = inString.substring(index+10+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstBookTitle.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (inString.contains("book-desc")) {
                index = inString.indexOf("book-desc"); inString = inString.substring(index+9+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstBookDesc.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (index == -1) // -1 means "not found"
            {
                FltMessage = getResources().getString(com.domnaskale.app.R.string.KomunikatBledu);
                return outString = FltMessage;
            }
        }

        //<others>
        ArrayList<String> lstOtherId         = new ArrayList<String>();
        ArrayList<String> lstOtherInfo      = new ArrayList<String>();
        ArrayList<String> lstOtherDesc      = new ArrayList<String>();

        for (int i = 0; i < Integer.parseInt(strMaxOthers); i++) {
            if (inString.contains("other-id")) {
                index = inString.indexOf("other-id"); inString = inString.substring(index+8+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstOtherId.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (inString.contains("other-info")) {
                index = inString.indexOf("other-info"); inString = inString.substring(index+10+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstOtherInfo.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (inString.contains("other-desc")) {
                index = inString.indexOf("other-desc"); inString = inString.substring(index+10+2);
                index2 = inString.indexOf("\"");
                if (index2 > 0) {
                    lstOtherDesc.add(inString.substring(0, index2));
                    inString = inString.substring(index2);
                }
            }
            if (index == -1) // -1 means "not found"
            {
                FltMessage = getResources().getString(com.domnaskale.app.R.string.KomunikatBledu);
                return outString = FltMessage;
            }
        }
        // ***************** HTML Creation based on parsed data *************************
        //html for header
        String strHtmlHeader = getResources().getString(com.domnaskale.app.R.string.ActualizationDate)+": " + strDateTime + "<br>";

        //html for events
        String strHtmlEvents = "";
        if (!lstEventId.isEmpty()) {
            //strHtmlEvents = "<h1>" + getResources().getString(com.domnaskale.app.R.string.HeaderEvent) + "</h1>";
            strHtmlEvents = "<p></p>";
        }
        Iterator iterEventId       = lstEventId.iterator();
        Iterator iterEventClass    = lstEventClass.iterator();
        Iterator iterEventDateTime = lstEventDateTime.iterator();
        Iterator iterEventPlace    = lstEventPlace.iterator();
        Iterator iterEventInfo     = lstEventInfo.iterator();
        Iterator iterEventDesc     = lstEventDesc.iterator();
        Iterator iterEventHtml     = lstEventHtml.iterator();
        while(iterEventId.hasNext() && iterEventClass.hasNext() && iterEventDateTime.hasNext() && iterEventPlace.hasNext() && iterEventInfo.hasNext() & iterEventDesc.hasNext()  & iterEventHtml.hasNext()) {
            Object nextEventId       = iterEventId.next();
            Object nextEventClass    = iterEventClass.next();
            Object nextEventDateTime = iterEventDateTime.next();
            Object nextEventPlace    = iterEventPlace.next();
            Object nextEventInfo     = iterEventInfo.next();
            Object nextEventDesc     = iterEventDesc.next();
            Object nextEventHtml     = iterEventHtml.next();

            strHtmlEvents = strHtmlEvents + "<b>"+getResources().getString(com.domnaskale.app.R.string.HtmlEventClass)+"</b> " + nextEventClass.toString() + "<br>";
            strHtmlEvents = strHtmlEvents + "<b>"+getResources().getString(com.domnaskale.app.R.string.HtmlEventDateTime)+"</b> " + nextEventDateTime.toString() + "<br>";
            strHtmlEvents = strHtmlEvents + "<b>"+getResources().getString(com.domnaskale.app.R.string.HtmlEventPlace)+"</b> " + nextEventPlace.toString() + "<br>";
            strHtmlEvents = strHtmlEvents + "<b>"+getResources().getString(com.domnaskale.app.R.string.HtmlEventInfo)+"</b> " + nextEventInfo.toString() + "<br>";
            strHtmlEvents = strHtmlEvents + "<b>"+getResources().getString(com.domnaskale.app.R.string.HtmlEventDesc)+"</b> " + nextEventDesc.toString() + "<br>";
            strHtmlEvents = strHtmlEvents + "<b>"+getResources().getString(com.domnaskale.app.R.string.HtmlEventWWW)+"</b>  <a href='" + nextEventHtml.toString() + "'>" + nextEventHtml.toString() + "</a><br>";
            strHtmlEvents = strHtmlEvents + "<br>";
        }
        //html for books
        String strHtmlBooks = "";
        if (!lstBookId.isEmpty()) {
            strHtmlBooks = "<h1>" + getResources().getString(com.domnaskale.app.R.string.HeaderBook)+ "</h1>";
        }
        Iterator iterBookId       = lstBookId.iterator();
        Iterator iterBookAuthor   = lstBookAuthor.iterator();
        Iterator iterBookTitle    = lstBookTitle.iterator();
        Iterator iterBookDesc     = lstBookDesc.iterator();
        while(iterBookId.hasNext() && iterBookAuthor.hasNext() && iterBookTitle.hasNext() && iterBookDesc.hasNext()) {
            Object nextBookId      = iterBookId.next();
            Object nextBookAuthor  = iterBookAuthor.next();
            Object nextBookTitle   = iterBookTitle.next();
            Object nextBookDesc    = iterBookDesc.next();

            strHtmlBooks = strHtmlBooks + "<b>"+getResources().getString(com.domnaskale.app.R.string.HtmlBooksAuthor)+"</b> " + nextBookAuthor.toString() + "<br>";
            strHtmlBooks = strHtmlBooks + "<b>"+getResources().getString(com.domnaskale.app.R.string.HtmlBooksTitle)+"</b> " + nextBookTitle.toString() + "<br>";
            strHtmlBooks = strHtmlBooks + "<b>"+getResources().getString(com.domnaskale.app.R.string.HtmlBooksDesc)+"</b> " + nextBookDesc.toString() + "<br>";
            strHtmlBooks = strHtmlBooks + "<br>";
        }
        //html for others
        String strHtmlOthers = "";
        if (!lstOtherId.isEmpty()) {
            strHtmlOthers = "<h1>" + getResources().getString(com.domnaskale.app.R.string.HeaderOthers)+ "</h1>";
        }
        Iterator iterOthersId      = lstOtherId.iterator();
        Iterator iterOthersInfo    = lstOtherInfo.iterator();
        Iterator iterOthersDesc    = lstOtherDesc.iterator();
        while(iterOthersId.hasNext() && iterOthersInfo.hasNext() && iterOthersDesc.hasNext()) {
            Object nextOthersId    = iterOthersId.next();
            Object nextOthersInfo  = iterOthersInfo.next();
            Object nextOthersDesc  = iterOthersDesc.next();

            strHtmlOthers = strHtmlOthers + "<b>"+getResources().getString(com.domnaskale.app.R.string.HtmlOthersInfo)+"</b> " + nextOthersInfo.toString() + "<br>";
            strHtmlOthers = strHtmlOthers + "<b>"+getResources().getString(com.domnaskale.app.R.string.HtmlOthersDesc)+"</b> " + nextOthersDesc.toString() + "<br>";
            strHtmlOthers = strHtmlOthers + "<br>";
        }

        // output
        outString = "";
        outString = outString + strHtmlHeader;
        outString = outString + strHtmlEvents;
        outString = outString + strHtmlBooks;
        outString = outString + strHtmlOthers;

        return outString;
    }

    public void ReadNewsFile(){

        // wyświetlenie pobranego testu na ekranie
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    String NewFromFile = new Scanner(new File("/storage/emulated/0/Download/DomNaSkale/RecentCalendar.txt")).useDelimiter("\\A").next();
                    TextView textView = (TextView) findViewById(com.domnaskale.app.R.id.News);
                    textView.setText(Html.fromHtml(ParseNewsFromFile(NewFromFile)));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public class FileDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params){
            try {
                // definicja połączenia
                URL dataURL = new URL(DownloadLinkURL);
                String FolderName = "DomNaSkale";

                HttpURLConnection connection = (HttpURLConnection)dataURL.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                connection.connect();

                // utworzenie folderu / sprawdzenie, czy istnieje
                File rootDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FolderName);

                if(!rootDirectory.exists()){
                    rootDirectory.mkdirs();
                }

                String nameOfFile = URLUtil.guessFileName(DownloadLinkURL, null, MimeTypeMap.getFileExtensionFromUrl(DownloadLinkURL));
                File file = new File(rootDirectory, nameOfFile);

                // pobranie pliku
                HttpURLConnection urlConnection = (HttpURLConnection) dataURL.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    try {
                        OutputStream out = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int len;
                        while((len=in.read(buf))>0){
                            out.write(buf,0,len);
                        }
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

}
