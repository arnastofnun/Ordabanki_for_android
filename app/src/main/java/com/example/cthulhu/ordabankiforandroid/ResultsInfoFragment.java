package com.example.cthulhu.ordabankiforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.cthulhu.ordabankiforandroid.REST.OrdabankiRestClientUsage;
import com.example.cthulhu.ordabankiforandroid.interfaces.OnTermResultObtainedListener;
import com.example.cthulhu.ordabankiforandroid.jsonHandlers.TermResultJsonHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment for the results info screen
 */
public class ResultsInfoFragment extends Fragment implements OnTermResultObtainedListener {
    private Globals globals = (Globals) Globals.getContext();
    //Language names
    private final ArrayList<ArrayList<String>> languages = globals.getLanguages();



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
        TextView resultCountView = (TextView) rootView.findViewById(R.id.result_count);
        TextView termGlossaryView = (TextView) rootView.findViewById(R.id.termGlossaryView);
        wv = (WebView) rootView.findViewById(R.id.webViewTerm);



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



        //Set header text
        wordTextView.setText(word);
        //Set results count
        resultCountView.setText(String.valueOf(resultIndex+1) + " / " + String.valueOf(resultList.size()));
        termGlossaryView.setText(glossaryName);
        getTermResult(idTerm);

        return rootView;
    }



    private void setupWebView(TermResult[] termResult, final ArrayList<ArrayList<String>> languages){

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

                if(termResult[0].term.words[0] != null){
                    wordHTML +="<div id=\"container\">";
                    for(TermResult.Term.Word word: termResult[0].term.words){
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
                    if(termResult[0].term.sbr[0] != null){
                        sbr_refsHTML += "<div id=\"sbr_refs\">";
                        for(TermResult.Term.Sbr sbr: termResult[0].term.sbr){
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
                    if(termResult[0].term.einnig[0] != null){
                        einnig_refsHTML += "<div id=\"sbr_refs\">";
                        for(TermResult.Term.Einnig einnig: termResult[0].term.einnig){
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



    private void getTermResult(String idTerm){
        TermResultJsonHandler tJsonHandler = new TermResultJsonHandler(this);
        OrdabankiRestClientUsage tClient = new OrdabankiRestClientUsage();
        try{
            tClient.setTermResults(OrdabankiURLGen.createTermURL(idTerm),tJsonHandler);
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * real javadoc will be in final class-see
     * @param tResult
     */
    @Override
    public void onTermResultObtained(TermResult[] tResult){
        setupWebView(tResult,languages);
    }

    /**
     * real javadoc will be in final class-see
     * @param statusCode
     */
    @Override
    public void onTermResultFailure(int statusCode){

    }
}
