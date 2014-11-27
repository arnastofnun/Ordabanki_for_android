package com.arnastofnun.idordabanki.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.arnastofnun.idordabanki.REST.OrdabankiRESTClient;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.Settings;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

/**
 *  This class displays the glossary info screen
 * @author Trausti
 * @since 29.10.2014
 */
public class AboutGlossaryActivity extends Activity {

    /**
     *  url is  the url of the glossary info page
     */     
    private String url;
    
    /** 
     *  webView is the view used to display html content
     */     
    private WebView webView;

    /**  
     * contents is the html itself
     */    
    private String contents;
    /**  
     * css is a string that contains css to change the look of the page
     */    
    private String css;

    /**
     * display activity with info about selected glossary
     * <p>Activity is called when it first starts up</p>
     *
     * @param  savedInstanceState state when activity starts up
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_glossary);

        url = getIntent().getStringExtra("url_string");
        url = Uri.parse(url).toString();

        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        css = "<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>" +
                "<link href='http://fonts.googleapis.com/css?family=PT+Serif' rel='stylesheet' type='text/css'>" +
                "<style>body {;color:#616161;background-color: #DCEDC8;" +
                "font-family: 'Droid Sans', sans-serif;font-size:1.3em; } a{font-family: 'Droid Sans', sans-serif;}" +
                "p{font-family: 'PT Serif', serif;}</style>" +
                "<h4 style=\"text-align:center;\">Orðabanki Íslenskrar málstöðvar</h1>" ;

       OrdabankiRESTClient.get(url, null, new AsyncHttpResponseHandler() {
           /*
            *  Called when get request has started
            */
           @Override
           public void onStart() {
           }

           /**
            * Parses html text with glossary info
            * @param statusCode HTTP status code
            * @param headers headers array
            * @param responseBody response in byte array
            */
           @Override
           public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

               try {
                   contents = new String(responseBody, "Windows-1252");
                   contents = css + contents.substring(contents.indexOf("<p>"), contents.length());

                   webView.loadDataWithBaseURL(null, contents, "text/html", "UTF-8", null);

               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

           /**
            * called when response HTTP status is "4XX" (eg. 401, 403, 404)
            * @param statusCode HTTP status code
            * @param headers headers array
            * @param errorResponse error response
            * @param e throws an exception
            */
           @Override
           public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

           }

           /**
            * called when request is retried
            * @param retryNo number of retries
            */
           @Override
           public void onRetry(int retryNo) {
           }
       });

    }

    /**
     *@param menu the menu
     *@return true or false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about_glossary, menu);
        return true;
    }

    /**
     * @param item the menu item that was clicked
     * @return true or false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            View v = findViewById(R.id.action_settings);
            //Open up the popup options menu
            Settings settings = new Settings(this);
            settings.createOptionsPopupMenu(v);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
