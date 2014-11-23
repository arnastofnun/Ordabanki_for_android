package com.example.cthulhu.ordabankiforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment for the results info screen
 */
public class ResultsInfoFragment extends Fragment {



    /**
     * wv interprets and displays the html in it's container
     */
    private WebView wv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_results_info, container, false);

        Bundle args = getArguments();
        int resultIndex = args.getInt("resultIndex");

        TextView wordTextView = (TextView) rootView.findViewById(R.id.termWordView);
        TextView termGlossaryView = (TextView) rootView.findViewById(R.id.termGlossaryView);
        wv = (WebView) rootView.findViewById(R.id.webViewTerm);


        Globals globals = (Globals) Globals.getContext();
        //Get the result at the right place in the results list
        List<Result> resultList = globals.getResults();
        Result result = resultList.get(resultIndex);
        /**
         * idTerm is the id of the term, it is used to perform a get request
         * to fetch all info on selected term
         */
        String idTerm = result.getId_term();
        /**
         * idWord is the word that the user selected and is displayed at
         * the top of the screen
         */
        String word = result.getWord();
        /**
         * dictCode is the code of the glossary that
         * the selected term belongs to
         */
        String dictCode = result.getDictionary_code();


        //Fetch glossary name of selected term
        ArrayList<ArrayList<String>> glossaries = globals.getLoc_dictionaries();
        int dictCodeIndex = glossaries.get(0).indexOf(dictCode);
        /**
         * glossaryName is the name of the glossary that
         * the selected term belongs to
         */
        String glossaryName = glossaries.get(1).get(dictCodeIndex);

        //Language names
        final ArrayList<ArrayList<String>> languages = globals.getLanguages();

        //Set header text
        wordTextView.setText(word);
        termGlossaryView.setText(glossaryName);
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
                        "<style>#sbr_refs{text-align:left;color:white;font-family: 'PT Serif', serif;font-size:0.9em} h3{color:white} body{margin-left:auto;margin-right:auto;width:75%;background-color:#616161;color:#616161;}p{margin:0pt;padding:0pt;} " +
                        "#synonym{padding:5px;background-color:white;margin-top:5px;margin:0px;-webkit-border-radius: 7px;\n" +
                        "-moz-border-radius: 7px;margin-left:auto;margin-right:auto;\n" +
                        "border-radius: 7px;}" +
                        "#word{padding:5px;background-color:#DCEDC8;margin:0px;-webkit-border-radius: 7px;\n" +
                        "-moz-border-radius: 7px;margin-left:auto;margin-right:auto;\n" +
                        "border-radius: 7px;}" +
                        "#container{margin-top:6px;margin-left:auto;margin-right:auto} " +
                        "#textBlock{text-align:center;margin-left:auto;margin-right:auto}" +
                        "</style>";
                String synonymHTML;
                String sbr_refsHTML = "";
                String einnig_refsHTML = "";
                boolean synParent; //true if div word exists (synonym parent), false otherwise
                TermResult[] terms = gson.fromJson(response.toString(), TermResult[].class);
                if(terms[0].term.words[0] != null){
                    wordHTML +="<div id=\"container\">";
                    for(TermResult.Term.Word word: terms[0].term.words){
                        synonymHTML = "";
                        wordHTML += "<div id=\"container\"><div id=\"textBlock\">" +
                                "<b><h3>"+word.word + " - " +
                                languages.get(1).get(languages.get(0).indexOf(word.lang_code)) + //language of term fetched
                                "</h3></b>";
                        wordHTML += "<p>";

                        if(word.abbreviation != null || word.definition != null || word.dialect != null
                                || word.domain != null|| word.example != null || word.explanation != null
                                || word.othergrammar != null)
                        {
                            wordHTML+="<div id=\"word\">";
                            synParent = true;
                        }else{
                            synParent = false;
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
                            if(synParent){
                                synonymHTML += "<div id=\"synonym\"><b>" + getString(R.string.word_synyonym) + " </b>";
                            }else{
                                synonymHTML += "<div id=\"synonym\" style=\"color:white;padding:0px;background-color:#616161;margin-top:0px;\"><b>" + getString(R.string.word_synyonym) + " </b>";
                            }

                            for(TermResult.Term.Word.Synonym synonym: word.synonyms){

                                synonymHTML += "<div id=\"word\" style =\"width:80%;margin-top:5px;font-family:'PT serif';color:#616161;\"><b><i>"+synonym.synonym + "</i></b>";
                                String synChild = "";
                                if(synonym.abbreviation != null){
                                    synChild +=  getString(R.string.word_abbreviation)+" " + synonym.abbreviation+ "<br>";
                                }
                                if(synonym.dialect != null){
                                    synChild +=  getString(R.string.word_dialect)+" " + synonym.dialect + "<br>";
                                }
                                if(synonym.pronunciation != null){
                                    synChild += getString(R.string.word_pronunciation)+" " + synonym.pronunciation + "<br>";
                                }
                                if(synonym.othergrammar != null){
                                    synChild += getString(R.string.word_othergrammar)+" " + synonym.othergrammar + "<br>";
                                }

                                if(!synChild.equals("")){
                                    synonymHTML += "<div id =\"synonym\">" + synChild + "</div>";
                                }
                                synonymHTML += "</div>";
                            }
                            synonymHTML += "</div>";
                        }
                        wordHTML += synonymHTML;
                        wordHTML+="</p></div>";

                    }
                    if(terms[0].term.sbr[0] != null){
                        sbr_refsHTML += "<div id=\"sbr_refs\">";
                        for(TermResult.Term.Sbr sbr: terms[0].term.sbr){
                            sbr_refsHTML += "<br><i>"+getString(R.string.word_sbr)+"</i> ";
                            if(sbr.refs[0] != null){
                                for(TermResult.Term.Sbr.Refs ref: sbr.refs){
                                    //get language string with index of language name in array languages
                                    sbr_refsHTML += ref.word +" - " + languages.get(1).get(languages.get(0).indexOf(ref.lang_code));
                                    sbr_refsHTML += ", ";
                                }
                                sbr_refsHTML = sbr_refsHTML.substring(0,sbr_refsHTML.length()-2)+"</div>";
                            }
                        }
                    }
                    if(terms[0].term.einnig[0] != null){
                        einnig_refsHTML += "<div id=\"sbr_refs\">";
                        for(TermResult.Term.Einnig einnig: terms[0].term.einnig){
                            einnig_refsHTML += "<br><i>"+getString(R.string.word_einnig)+"</i> ";
                            if(einnig.refs[0] != null){
                                for(TermResult.Term.Einnig.Refs ref : einnig.refs){
                                    //get language string with index of language name in array languages
                                    einnig_refsHTML += ref.word +" - " +languages.get(1).get(languages.get(0).indexOf(ref.lang_code));
                                    einnig_refsHTML += ", ";
                                }
                                einnig_refsHTML = einnig_refsHTML.substring(0,einnig_refsHTML.length()-2)+"</div>";
                            }

                        }
                    }
                    wordHTML+="</div>";
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



















        return rootView;
    }

}
