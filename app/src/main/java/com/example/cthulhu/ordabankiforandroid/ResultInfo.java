package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import com.example.cthulhu.ordabankiforandroid.TermResult.Term.Word;
import com.example.cthulhu.ordabankiforandroid.TermResult.Term.Word.Synonym;
import com.example.cthulhu.ordabankiforandroid.TermResult.Term.Sbr;
import com.example.cthulhu.ordabankiforandroid.TermResult.Term.Einnig;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * ResultInfo displays more info when result has been selected
 *
 * When a specific result is clicked, it opens a new activity
 * with more information about the result and translations
 *
 * @author Trausti
 * @since 05/11/14
 *
 */

public class ResultInfo extends Activity {

    /**
     * wv interprets and displays the html in it's container
     */
    private WebView wv;
    /**
     * idTerm is the id of the term, it is used to perform a get request
     * to fetch all info on selected term
     */
    private String idTerm;
    /**
     * idWord is the word that the user selected and is displayed at
     * the top of the screen
     */
    private String idWord;

    /**
     * display activity with info about selected term in results screen
     * <p> After all info on results has been fetched the data is parsed, HTML is
     *   generated inside the method and interpeted and displayed on the screen </p>
     *  written by Trausti
     * 
     * @param  savedInstanceState saved instances
     * @return nothing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_info);
        Bundle extras = getIntent().getExtras();
        idTerm = extras.getString("idTerm");
        idWord = extras.getString("idWord");
        TextView wordTextView = (TextView)findViewById(R.id.termWordView);
        wv = (WebView)findViewById(R.id.webViewTerm);
        wordTextView.setText(idWord);
        String url = "http://api.arnastofnun.is/ordabanki.php?term="+idTerm+"&agent=ordabankaapp";
        OrdabankiRESTClient.get(url,null, new JsonHttpResponseHandler(){
            /**
             * Parses raw Json result and returns to activity
             * @param statusCode HTTP status code
             * @param headers headers array
             * @param response raw Json response
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response){
                Gson gson = new Gson();
                String wordHTML = "<link href='http://fonts.googleapis.com/css?family=PT+Serif' rel='stylesheet' type='text/css'>" +
                        "<style>#sbr_refs{color:white;font-family: 'PT Serif', serif;font-size:0.9em} h3{color:white} body{background-color:#616161;color:#616161;}p{margin:0pt;padding:0pt;} " +
                        "#synonym{padding:5px;background-color:white;margin-top:0px;-webkit-border-radius: 7px;\n" +
                        "-moz-border-radius: 7px;\n" +
                        "border-radius: 7px;}" +
                        "#word{padding:5px;background-color:#DCEDC8;margin-top:0px;-webkit-border-radius: 7px;\n" +
                        "-moz-border-radius: 7px;\n" +
                        "border-radius: 7px;}" +
                        "#container{width:300px;margin-left: auto; margin-right: auto;margin-top:6px;} " +
                        "#textBlock{text-align:center;}" +
                        "</style>";
                String synonymHTML;
                String sbr_refsHTML = "";
                String einnig_refsHTML = "";
                TermResult[] terms = gson.fromJson(response.toString(), TermResult[].class);
                if(terms[0].term.words[0] != null){
                    for(Word word: terms[0].term.words){
                        synonymHTML = "";
                        wordHTML += "<div id=\"container\"><div id=\"textBlock\">" +
                                "<b><h3>"+word.word + " - " + word.lang_code + "</h3></b>";
                        wordHTML += "<p>";

                        if(word.abbreviation != null || word.definition != null || word.dialect != null
                        || word.domain != null|| word.example != null || word.explanation != null
                        || word.othergrammar != null)
                        {
                            wordHTML+="<div id=\"word\">";
                        }

                        if(word.abbreviation != null){
                            wordHTML += "<b>"+getString(R.string.word_abbreviation)+"</b> "+ word.abbreviation + "<br>";
                        }
                        if(word.definition != null){
                            wordHTML += "<b>"+getString(R.string.word_definition)+"</b> "+ word.definition + "<br>";
                        }
                        if(word.dialect != null){
                            wordHTML += "<b>"+getString(R.string.word_dialect)+"</b> " + word.dialect + "<br>";

                        }
                        if(word.domain != null){
                            wordHTML += "<b>"+getString(R.string.word_domain)+"</b> " +  word.domain + "<br>";

                        }
                        if(word.example != null) {
                            wordHTML += "<b>"+getString(R.string.word_example)+"</b> " +  word.example + "<br>";
                        }
                        if(word.explanation != null){
                            wordHTML += "<b>"+getString(R.string.word_explanation)+"</b> "+ word.explanation + "<br>";
                        }
                        if(word.othergrammar != null){
                            wordHTML += "<b>"+getString(R.string.word_othergrammar)+"</b> "+ word.othergrammar + "<br>";
                        }
                        if(word.synonyms[0] != null){
                            synonymHTML += "<div id=\"synonym\"><b>" + getString(R.string.word_synyonym) + " </b>";
                            for(Synonym synonym: word.synonyms){

                                synonymHTML += synonym.synonym + "<br>";

                                if(synonym.abbreviation != null){
                                    synonymHTML +=  getString(R.string.word_abbreviation)+" " + synonym.abbreviation+ "<br>";
                                }
                                if(synonym.dialect != null){
                                    synonymHTML +=  getString(R.string.word_dialect)+" " + synonym.dialect + "<br>";
                                }
                                if(synonym.pronunciation != null){
                                    synonymHTML += getString(R.string.word_pronunciation)+" " + synonym.pronunciation + "<br>";
                                }
                                if(synonym.othergrammar != null){
                                    synonymHTML += getString(R.string.word_othergrammar)+" " + synonym.othergrammar + "<br>";
                                }

                            }
                            synonymHTML += "</div>";
                        }
                        wordHTML += synonymHTML;
                        wordHTML+="</p></div></div>";

                }
                if(terms[0].term.sbr[0] != null){
                    sbr_refsHTML += "<div id=\"sbr_refs\">";
                    for(Sbr sbr: terms[0].term.sbr){
                        sbr_refsHTML += "<br><i>"+getString(R.string.word_sbr)+"</i>: ";
                        if(sbr.refs[0] != null){
                            for(Sbr.Refs ref: sbr.refs){
                                sbr_refsHTML += ref.word +" - " +ref.lang_code;
                                sbr_refsHTML += ", ";
                            }
                            sbr_refsHTML = sbr_refsHTML.substring(0,sbr_refsHTML.length()-2)+"</div>";
                        }
                    }
                }
                if(terms[0].term.einnig[0] != null){
                    einnig_refsHTML += "<div id=\"sbr_refs\">";
                    for(Einnig einnig: terms[0].term.einnig){
                        einnig_refsHTML += "<br><i>"+getString(R.string.word_einnig)+"</i>: ";
                       if(einnig.refs[0] != null){
                           for(Einnig.Refs ref : einnig.refs){
                               einnig_refsHTML += ref.word +" - " +ref.lang_code;
                               einnig_refsHTML += ", ";
                           }
                           einnig_refsHTML = einnig_refsHTML.substring(0,einnig_refsHTML.length()-2)+"</div>";
                       }

                    }
                }
            }

                wv.loadDataWithBaseURL(null, wordHTML+sbr_refsHTML+einnig_refsHTML, "text/html", "UTF-8", null);

            }

            /**
             * All onFailure methods only return HTTP status code to activity
             * @param statusCode HTTP status code
             * @param headers headers array
             * @param throwable thrown exception
             * @param errorResponse Json array response (error)
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse){
            }

            /**
             *
             * @param statusCode HTTP status code
             * @param headers headers array
             * @param responseString string response (error)
             * @param throwable thrown exception
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
            }

            /**
             *
             * @param statusCode HTTP status code
             * @param headers headers array
             * @param throwable thrown exception
             * @param errorResponse Json object response (error)
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse){
            }
        });

    }

    /**
     * Inflates the options menu
     * @param menu the options menu
     * @return true or false
     * TODO inflate the correct options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result_info, menu);
        return true;
    }

    /**
     * Handles when clicked on items in the action bar
     * @param item the menu item that was clicked
     * @return true or false
     * TODO handle on click actions for the action bar
     */
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