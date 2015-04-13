package com.arnastofnun.idordabanki.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.arnastofnun.idordabanki.sync.REST.OrdabankiRESTClient;
import com.arnastofnun.idordabanki.R;
import com.arnastofnun.idordabanki.preferences.Settings;
import com.arnastofnun.idordabanki.helpers.ThemeHelper;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
/**
 *  This class displays the glossary info screen
 * @author Trausti
 * @since 29.10.2014
 */
public class AboutGlossaryActivity extends Activity {

    /**
     *  url is the url of the glossary info page
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
        ThemeHelper.setCurrentTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_glossary);

        url = getIntent().getStringExtra("url_string");
        url = Uri.parse(url).toString();

        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        ThemeHelper themeHelper = new ThemeHelper(this);
        String primaryText = themeHelper.getHexColorFromAttr(R.attr.primaryTextColor);
        String primaryBackground = themeHelper.getHexColorFromAttr(R.attr.primaryBackgroundColor);
        String secondaryText = themeHelper.getHexColorFromAttr(R.attr.secondaryTextColor);
        String secondaryBackground = themeHelper.getHexColorFromAttr(R.attr.secondaryBackgroundColor);
        String thirdBackground = themeHelper.getHexColorFromAttr(R.attr.thirdBackgroundColor);

        Log.v("url",url);

        css = "<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>" +
                "<link href='http://fonts.googleapis.com/css?family=PT+Serif' rel='stylesheet' type='text/css'>" +
                "<style>body {text-alignment:center; padding:4pt;margin:4pt;color:"+primaryText+";background-color: "+primaryBackground+";" +
                "font-family: 'Droid Sans', sans-serif;font-size:13pt !important; } a{display:inline-block;text-decoration:none !important;border-radius:5pt !important;padding:5pt;margin:5pt !important;background-color:"+secondaryBackground+";color:"+secondaryText+" !important;font-weight:bold;font-family: 'Droid Sans' , sans-serif !important;}" +
                "p{font-family: 'PT Serif', serif !important;padding:6pt !important;margin:7pt !important} h2:nth-child(1), h2:nth-child(2){display:none !important} h2{margin:8pt !important} blockquote{margin:1pt !important} img{display:none !important} </style>";
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

                    contents = contents.replace("&nbsp;","").
                            replace("blockquote","section").
                            replace("font-size","font-weight") + css;

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