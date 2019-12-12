package com.example.ex95;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText urlEditText, urlContentEditText;
    //Here we define an array of some URLs
    String[] urls = new String[]{
            "http://www.puv.fi/en/",
            "https://yle.fi/uutiset",
            "https://www.vaasa.fi/",
    };
    //Here we create a Random object
    Random random = new Random();
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //Here we define a WebClient object to prevent the device
        //from loading the page on the device's browser in case
        //the original page is redirected.
        webView.setWebViewClient(new Callback());
        webSettings.setBuiltInZoomControls(true);
        //In the following we create UI components
        urlEditText = findViewById(R.id.et_url);
        Button goButton = findViewById(R.id.btn_go);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(urlEditText.getText().toString());
            }
        });
        urlEditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Here we randomly choose a value from urls array to be set as the
                //text of urlEditText
                urlEditText.setText(urls[random.nextInt(urls.length)]);
                webView.loadUrl(urlEditText.getText().toString());
                return true;
            }
        });
        //Here we create some dynamic content to be displayed on the WebView object
        final String mimeType="text/html";
        final String encoding="UTF-8";
        String htmlContent="<h1>Welcome Page</h1>" +
                "<html><head><style> p { font-size:24;}   </style></head><body><h1>This is a welcome page created dynamically!</h1></body>";
        webView.loadData(htmlContent, mimeType, encoding);
        //Here we load a an external file located under assets folder of the application
        //webView.loadUrl("file:///android_asset/info.html");
    }
    //Here we define the Callback class
    private class Callback extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            return false;
        }
    }

    //Here we define BackgroundTask class, which inherits AsyncTask class.
    //We declare the class as static to prevent possible memory leakage.
    private  class BackgroundTask extends AsyncTask<String, Integer, Bitmap> {
        //This method receives an array of URLs as strings and returns
        //an object of type Bitmap. This method calls getInputStream() method
        //which makes HTTP connection to the given URL and returns the input stream
        @Override
        protected Bitmap doInBackground(String...urls) {
            Bitmap bitmap=null;
            try {
                //Here we create an InputStream object
                InputStream inputStream = getInputStream(urls[0]);
                //Here we create a bitmap out of the received input stream
                bitmap= BitmapFactory.decodeStream(inputStream);
                if(bitmap!=null)
                    //Here we update the progress of the background job
                    //by passing the number o fbytes in the bitmap
                    publishProgress(bitmap.getByteCount());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        //This method updates the progress
        @Override
        protected void onProgressUpdate(Integer...progress){
            //Here we display the size of the bitmap
            displayToast("Bitmap size: " + progress[0]);
        }
        //This method will be called when the background job has finished
        @Override
        protected void onPostExecute(Bitmap bitmap){
            //Here we se the image of the imageView object
            imageView.setImageBitmap(bitmap);
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
    private void displayToast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
}
