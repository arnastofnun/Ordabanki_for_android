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
import android.webkit.WebViewClient;

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
public class Beygingar extends Activity {

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
     * Runs when the activity is created and loads the declensions website
     * <p>Activity is called when it first starts up</p>
     *
     * @param  savedInstanceState state when activity starts up
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.setCurrentTheme(this); //Set the current theme
        super.onCreate(savedInstanceState);
        //We use the same layout as in the about glossary layout (contains only a webview
        setContentView(R.layout.activity_about_glossary);
        //Hide the action bar
        if (getActionBar() != null){
            getActionBar().hide();
        }
        //Get the url from the intent
        url = getIntent().getStringExtra("url_string");
        url = Uri.parse(url).toString();

        //Find, and hide the webview (to be displayed when ready)
        webView = (WebView) findViewById(R.id.webView);
        webView.setVisibility(View.INVISIBLE);
        //Set a few settings for the web view
        WebSettings settings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.setVisibility(View.VISIBLE); //Display the webview when the page has been loaded
            }
        });
        settings.setDefaultTextEncodingName("UTF-8"); //Set encoding
        settings.setJavaScriptEnabled(true); //Enable javascript
        //Get the colors from the theme
        ThemeHelper themeHelper = new ThemeHelper(this);
        String primaryText = themeHelper.getHexColorFromAttr(R.attr.primaryTextColor);
        String primaryBackground = themeHelper.getHexColorFromAttr(R.attr.primaryBackgroundColor);
        String secondaryText = themeHelper.getHexColorFromAttr(R.attr.secondaryTextColor);
        String secondaryBackground = themeHelper.getHexColorFromAttr(R.attr.secondaryBackgroundColor);
        String thirdBackground = themeHelper.getHexColorFromAttr(R.attr.thirdBackgroundColor);

        //Custom css to make the page look as a part of our app
        css = "<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>" +
                "<link href='http://fonts.googleapis.com/css?family=PT+Serif' rel='stylesheet' type='text/css'>" +
                "<style>body {padding:4pt;margin:4pt;color:"+primaryText+";background-color: "+primaryBackground+";" +
                "font-family: 'Droid Sans', sans-serif;font-size:13pt !important; } a{text-decoration:none !important;border-radius:5pt !important;padding:5pt;margin:5pt !important;background-color:"+secondaryBackground+";color:"+secondaryText+" !important;font-weight:bold;font-family: 'Droid Sans' , sans-serif !important;}" +
                "p{font-family: 'PT Serif', serif !important;padding:6pt !important;margin:7pt !important} h2{margin:8pt !important} blockquote{margin:1pt !important} img, #header, #colOne, #leit_form, #footer{display:none !important;} ul{list-style:none; width:100%; margin-left:-10px;} li{padding:5pt;} li strong a{display: inline-block; margin-right:10pt;}" +
                ".page-header{padding:4pt; margin-top:-4pt; margin-left: -4pt width:100%; text-align:center; color:" + primaryText +"; background-color:" + thirdBackground+ ";} " +
                "h4{font-size:1.2em;color:" + primaryText +"; background-color:none; padding:2pt; margin-bottom:2pt; text-align:center} table{width:100%;} th{background-color: "+secondaryBackground + "; color:" + primaryBackground + ";}" +
                ".VO_beygingarmynd{color:"+primaryText+"; background-color:"+thirdBackground+"; padding:3pt ;border-radius:5pt;} p:nth-child(5){display:none;}</style>";
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

                    contents = new String(responseBody, "UTF-8");

                    contents = contents + css;

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

}