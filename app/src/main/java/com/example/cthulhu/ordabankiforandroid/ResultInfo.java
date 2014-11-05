package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import com.example.cthulhu.ordabankiforandroid.TermResult.Term.Word;
import com.example.cthulhu.ordabankiforandroid.TermResult.Term.Word.Synonym;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * When a specific result is clicked, it opens a new activity
 * with more information about the result and translations
 *
 * @author Trausti
 * @since 05/11/14
 *
 */

public class ResultInfo extends Activity {

    private WebView wv;
    private String idTerm;
    private String idWord;
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
                String wordHTML = "<style>h3{color:white} body{background-color:#616161;color:#616161;}p{margin:0pt;padding:0pt;} " +
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
                            synonymHTML += "<div id=\"synonym\"><b>" + getString(R.string.word_synyonym) + " </b><br>";
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
                }

                wv.loadDataWithBaseURL(null, wordHTML, "text/html", "UTF-8", null);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result_info, menu);
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
