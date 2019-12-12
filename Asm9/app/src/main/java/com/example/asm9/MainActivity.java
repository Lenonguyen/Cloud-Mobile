package com.example.asm9;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {
    EditText urlEditText;
    WebView webView;
    Button load_btn;
    Button loaddb_btn;
    Button getdb_btn;
    Button clear_btn;
    Button delete_btn;
    TextView webContent;
    DBAdapter dbAdapter = null;
    //Here we create a Random object
    Random random = new Random();
    //Here we declare an instance of BackgroundTask class
    BackgroundTask backgroundTask=null;
    //Here we define an array of some URLs
    String[] urls = new String[]{
            "http://www.puv.fi/en/",
            "https://portal.vamk.fi",
            "https://yle.fi/uutiset",
            "https://www.vaasa.fi/"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbAdapter = new DBAdapter(getApplicationContext());
        //In the following we create UI components
        urlEditText =  findViewById(R.id.et_url);
        webView = findViewById(R.id.webview);
        load_btn = findViewById(R.id.load_btn);
        loaddb_btn = findViewById(R.id.loaddb_btn);
        getdb_btn = findViewById(R.id.getdb_btn);
        clear_btn = findViewById(R.id.clear_btn);
        delete_btn = findViewById(R.id.delete_btn);
        webContent= findViewById(R.id.webContent);
        // webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        //Here we define a WebClient object to prevent the device
        //from loading the page on the device's browser in case
        //the original page is redirected.
        webView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        urlEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here we randomly choose a value from urls array to be set as the
                //text of urlEditText
                urlEditText.setText(urls[random.nextInt(urls.length)]);
            }
        });
        load_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (urlEditText.getText().length() == 0) {
                    Toast.makeText(getApplication(), getResources().getString(R.string.empty_fields_txt), Toast.LENGTH_LONG).show();
                }
                else {
                    //Here we initialize the backgroundTask object
                    backgroundTask = new BackgroundTask();
                    //Here we call the execute() method of backgroundTask object.
                    //This will cause calling methods of backgroundTask object.
                    backgroundTask.execute(urlEditText.getText().toString());
                }
            }
        });
        getdb_btn.setOnClickListener(Get_webs);
        loaddb_btn.setOnClickListener(loadDB);
        clear_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                webContent.setText("");
            }
        });
        /*delete_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dbAdapter.deleteDB();
            }
        });*/
    }
    //Here we define BackgroundTask class, which inherits AsyncTask class.
    private class BackgroundTask extends AsyncTask<String, Integer, String> {
        //This method receives an array of URLs as strings and returns
        //an object of type Bitmap. This method calls getInputStream() method
        //which makes HTTP connection to the given URL and returns the input stream
        @Override
        protected String doInBackground(String...urls) {
            String downloadedContent;
            //Here we initialize downloadedContent by calling
            //downLoadText() method
            downloadedContent=downLoadText(urls[0]);
            //Here we update the progress of the background job
            //by passing the size of downloaded text
            publishProgress(downloadedContent.length());
            return downloadedContent;
        }
        //This method updates the progress
        @Override
        protected void onProgressUpdate(Integer...progress){
            //Here we display the size of the bitmap
            displayToast("The amount of downloaded text: " + progress[0]);
        }
        //This method will be called when the background job has finished
        @Override
        protected void onPostExecute(String text){
            //Here we load the text on the WebView
            webView.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);
        }
    }
    // Makes HttpURLConnection and returns InputStream
    private InputStream getInputStream(String urlString) throws IOException {
        //Here we declare an object of type InputStream
        InputStream inputStream = null;
        //Here we create an URL object
        URL url = new URL(urlString);
        //Here we make an URL connection
        URLConnection urlConnection = url.openConnection();
        try {
            //Here we create a URLConnection with support for HTTP-specific features
            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            //Here we determine whether if required the system can ask the user
            //for additional input
            httpConnection.setAllowUserInteraction(false);
            // Sets whether HTTP redirects (requests with response code 3xx)
            //should be automatically followed by this HttpURLConnection instance.
            httpConnection.setInstanceFollowRedirects(true);
            //Set the method for the URL request
            httpConnection.setRequestMethod("GET");
            //Here we establish HttpURLConnection
            httpConnection.connect();
            //Here we make sure that the HttpURL connection has been successfully
            //established
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //Here we get the input stream that reads from this open connection
                inputStream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inputStream;
    }
    //This method will read text from the given URL
    private String downLoadText(String urlString){
        int BUFFER_SIZE=2000;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        int charRead;
        //String content="";
        StringBuilder content = new StringBuilder();
        try {
            inputStream=getInputStream(urlString);
            //Here we create an InputStreamReader object to read the content
            //of the stream
            if(inputStream !=null) {
                inputStreamReader = new InputStreamReader(inputStream);
                //Here we define inputBuffer array with which we read the content
                //of the input stream
                char[] inputBuffer = new char[BUFFER_SIZE];
                //In the following we read the content of the input stream and attach it
                //to content variable, which is of type String
                while ((charRead = inputStreamReader.read(inputBuffer)) > 0) {
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    //content+=readString;
                    content.append(readString);
                    inputBuffer = new char[BUFFER_SIZE];
                }
                writetoDB(urlString, content.toString());
                inputStream.close();
            }
        } catch (IOException e) {
            displayToast(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return content.toString();
    }
    private void writetoDB(String url, String content) {
        dbAdapter.open();
        dbAdapter.addWeb(url, content);
        dbAdapter.close();
    }
    private void displayToast(String text){
        Toast.makeText(getApplication(), text, Toast.LENGTH_LONG).show();
    }
    //Here we define the WebViewClient class. We need to implement a WebViewClient to
    // intercept the custom URI before it is loaded.
    private class CustomWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            return false;
        }
    }
    private View.OnClickListener Get_webs = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dbAdapter.open();
            Cursor cursor = dbAdapter.getAllWebs();
            webContent.setText("");
            if(cursor.moveToFirst()) {
                do {
                    webContent.append(displayURLData(cursor) + "\n");
                } while(cursor.moveToNext());
            }
            //Here we close database connection
            dbAdapter.close();
        }
    };
    private String displayURLData(Cursor cursor) {
        return "Address : " + cursor.getString(0);
    }
    private String displayContentData(Cursor cursor) {
        return cursor.getString(1);
    }
    private View.OnClickListener loadDB = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (urlEditText.getText().length() == 0) {
                Toast.makeText(getApplication(), getResources().getString(R.string.empty_fields_txt), Toast.LENGTH_LONG).show();
            } else {
                //Here we add a new customer data
                dbAdapter.open();
                String urlg = urlEditText.getText().toString();
                Cursor cursor = dbAdapter.getWebByUrl(urlg);
                if(cursor.moveToFirst()) {
                    webContent.setText(displayContentData(cursor));
                } else {
                    Toast.makeText(getApplication(), getResources().getString(R.string.url_txt) + urlg + getResources().getString(R.string.not_found_txt), Toast.LENGTH_LONG).show();
                }
                //Here we close database connection
                dbAdapter.close();
            }
        }
    };
}
