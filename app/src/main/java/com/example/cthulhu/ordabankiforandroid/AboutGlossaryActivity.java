package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 *  This class displays the glossary info screen
 *
 *  @author Trausti
 * @since 29.10.2014
 */
public class AboutGlossaryActivity extends Activity {
    /*
     *  Data invariants:
     *
     *  url is  the url of the glossary info page
     *  webView is the view used to display html content
     *  client is used to retrieve html from the web page containing glossary info
     *  contents is the html itself
     *  css is a string that contains css to change the look of the page
     *
     */
    private String url;
    private WebView webView;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String contents;
    private String css;
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

       OrdabankiRESTClient.get(url,null, new AsyncHttpResponseHandler() {
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

                try{
                    contents = new String(responseBody,"Windows-1252");
                    contents = css+contents.substring(contents.indexOf("<p>"),contents.length());

                    webView.loadDataWithBaseURL(null, contents, "text/html", "UTF-8", null);

                }catch(Exception e){
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about_glossary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
