package com.arnastofnun.idordabanki;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.arnastofnun.idordabanki.REST.OrdabankiRestClientUsage;
import com.arnastofnun.idordabanki.interfaces.OnTermResultObtainedListener;
import com.arnastofnun.idordabanki.jsonHandlers.TermResultJsonHandler;

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
    private WebView wv;
    private TextView wordTextView;
    private TextView resultCountView;
    private TextView termGlossaryView;
    private String idTerm;
    private boolean hasResult;




    /**
     * wv interprets and displays the html in its container
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_results_info, container, false);


        wordTextView = (TextView) rootView.findViewById(R.id.termWordView);
        resultCountView = (TextView) rootView.findViewById(R.id.result_count);
        termGlossaryView = (TextView) rootView.findViewById(R.id.termGlossaryView);
        wv = (WebView) rootView.findViewById(R.id.webViewTerm);
        Bundle args = getArguments();
        if(args.containsKey("resultIndex")){
            hasResult = true;
            int resultIndex = args.getInt("resultIndex");
            //Get the result at the right place in the results list
            List<Result> resultList = globals.getResults();
            Result result = resultList.get(resultIndex);
            /**
             * idTerm is the id of the term, it is used to perform a get request
             * to fetch all info on selected term
             */
            idTerm = result.getId_term();
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
        }
        else{
            hasResult = false;
            wordTextView.setVisibility(View.INVISIBLE);
            //Set results count
            resultCountView.setVisibility(View.INVISIBLE);
            termGlossaryView.setVisibility(View.INVISIBLE);
            idTerm = args.getString("TermID");
        }
        getTermResult(idTerm);

        return rootView;
    }

    /**
     * A method that sets up the basic info
     * @param tResult list of term results
     */
    public void setupBaseInfo(TermResult[] tResult){
        /**
         * idWord is the word that the user selected and is displayed at
         * the top of the screen
         */
        String word = tResult[0].getWords()[0].getWord();
        /**
         * dictCode is the code of the glossary that
         * the selected term belongs to
         */
        String dictCode = tResult[0].getDictCode();

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
        wordTextView.setVisibility(View.VISIBLE);
        //Set results count
        termGlossaryView.setText(glossaryName);
        termGlossaryView.setVisibility(View.VISIBLE);
    }


    /**
     * A method that sets up the web view
     * @param termResult a list of term results
     */
    private void setupWebView(TermResult[] termResult){
        String wordHTML = initialiseHtmlStyle();
        String sbr_refsHTML = "";
        String einnig_refsHTML = "";
        TermResult.Term.Word[] termNames = termResult[0].getWords();
        TermResult.Term.Sbr[] termSamanber = termResult[0].getSbr();
        TermResult.Term.Einnig[] termEinnig = termResult[0].getEinnig();
        if(termNames[0] != null){
            wordHTML +="<div id=\"container\">";
            for(TermResult.Term.Word word: termNames){
                wordHTML += addWord(word);
            }
            if(termSamanber[0] != null){
                sbr_refsHTML += "<div id=\"sbr_refs\">";
                for(TermResult.Term.Sbr sbr: termSamanber){
                    sbr_refsHTML += addSamanber(sbr);
                }
            }
            if(termEinnig[0] != null){
                einnig_refsHTML += "<div id=\"sbr_refs\">";
                for(TermResult.Term.Einnig einnig: termEinnig){
                    einnig_refsHTML +=  addEinnig(einnig);
                }
            }
            wordHTML+="</div>";
        }
        wv.loadDataWithBaseURL(null, wordHTML+sbr_refsHTML+einnig_refsHTML, "text/html", "UTF-8", null);
        wv.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
                searchManager.startSearch(url,true,getActivity().getComponentName(),null,false);
                return true;
            }
        });
    }


    /**
     * This method sets up the einnig section of the html
     * @param einnig the einnig section to set up
     * @return the html for the einnig section
     */
    private String addEinnig(TermResult.Term.Einnig einnig){
        String einnig_refsHTML = "<br><table>\n" +
                "<tr><th><i>"+getString(R.string.word_einnig)+"</i></th>";
        if(einnig.getRefs()[0] != null){
            for(TermResult.Term.Einnig.Refs ref : einnig.getRefs()){
                //get language string with index of language name in array languages
                einnig_refsHTML += "<tr><td><div id=\"word\"><a href=\""+ref.getWord()+"\">"+ref.getWord()+"</a></div></td>" +
                        "<td>" +getLanguage(ref.getLangCode())+"</td></tr>";
            }
        }
        return einnig_refsHTML.substring(0,einnig_refsHTML.length())+"</table><br></div>";
    }


    /**
     * This method sets up the samanber section of the html
     * @param sbr the samanber section to set up
     * @return the html for the samanber section
     */
    private String addSamanber(TermResult.Term.Sbr sbr){
        String sbr_refsHTML = "<br><i>"+getString(R.string.word_sbr)+"</i> ";
        if(sbr.getRefs()[0] != null){
            for(TermResult.Term.Sbr.Refs ref: sbr.getRefs()){
                //get language string with index of language name in array languages
                sbr_refsHTML += ref.getWord() +" - " + getLanguage(ref.getLangCode());
                sbr_refsHTML += ", ";
            }
            sbr_refsHTML = sbr_refsHTML.substring(0,sbr_refsHTML.length()-2)+"</div>";
        }
        return sbr_refsHTML;
    }

    /**
     * This method sets up the word section of the html
     * @param word the word to set up
     * @return the html for the word
     */
    private String addWord(TermResult.Term.Word word){
        String synonymHTML = "";
        String wordHTML = "<div id=\"container\"><div id=\"textBlock\">" +
                "<b><h3>"+word.getWord() + " - " +
                getLanguage(word.getLangCode()) + //language of term fetched
                "</h3></b>";

        wordHTML += "<p>";
        if(word.hasSynParent()){
            wordHTML+="<div id=\"word\">";
        }

        if(word.getAbbreviation() != null){
            wordHTML += "<b>"+getString(R.string.word_abbreviation)+"</b> "+ word.abbreviation + "<br>";
        }
        if(word.getDefinition() != null){
            wordHTML += "<b>"+getString(R.string.word_definition)+"</b> "+ word.definition + "<br>";
        }
        if(word.getDialect() != null){
            wordHTML += "<b>"+getString(R.string.word_dialect)+"</b> " + word.dialect + "<br>";

        }
/*        if(word.getDomain() != null){
            wordHTML += "<b>"+getString(R.string.word_domain)+"</b> " +  word.domain + "<br>";

        }*/
        if(word.getExample() != null) {
            wordHTML += "<b>"+getString(R.string.word_example)+"</b> " +  word.example + "<br>";
        }
        if(word.getExplanation() != null){
            wordHTML += "<b>"+getString(R.string.word_explanation)+"</b> "+ word.explanation + "<br>";
        }
        if(word.getOtherGrammar() != null){
            wordHTML += "<b>"+getString(R.string.word_othergrammar)+"</b> "+ word.othergrammar + "<br>";
        }
        if(word.getSynonyms()[0] != null){
            if(word.hasSynParent()){
                synonymHTML += "<div id=\"synonym\"><b>" + getString(R.string.word_synyonym) + " </b>";
            }else{
                synonymHTML += "<div id=\"synonym\" style=\"color:white;padding:0px;background-color:#616161;margin-top:0px;\"><b>" + getString(R.string.word_synyonym) + " </b>";
            }

            for(TermResult.Term.Word.Synonym synonym: word.getSynonyms()){

                synonymHTML += "<div id=\"word\" style =\"width:80%;margin-top:5px;font-family:'PT serif';color:#616161;\"><b><i><a href =\""+synonym.synonym +"\">"+synonym.synonym+ "</a></i></b>";
                String synChild = "";
                if(synonym.getAbbreviation() != null){
                    synChild +=  getString(R.string.word_abbreviation)+" " + synonym.abbreviation+ "<br>";
                }
                if(synonym.getDialect() != null){
                    synChild +=  getString(R.string.word_dialect)+" " + synonym.dialect + "<br>";
                }
                if(synonym.getPronunciation() != null){
                    synChild += getString(R.string.word_pronunciation)+" " + synonym.pronunciation + "<br>";
                }
                if(synonym.getOtherGrammar() != null){
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
        return wordHTML;
    }





    /**
     * This method gets the language name in the correct language
     * from a language code
     * @param langCode the language code
     * @return the language name in the correct language
     */
    private String getLanguage(String langCode){
        return languages.get(1).get(languages.get(0).indexOf(langCode));
    }

    /**
     * This method contains the style for the css styling for the webview
     * @return the css styling
     */
    private String initialiseHtmlStyle(){
        return "<link href='http://fonts.googleapis.com/css?family=PT+Serif' rel='stylesheet' type='text/css'>" +
                "<style>#sbr_refs{font-family: 'PT Serif', serif;font-size:1.1em} h3{color:white} body{margin-left:auto;margin-right:auto;width:75%;background-color:#616161;color:#616161;}p{margin:0pt;padding:0pt;} " +
                "#synonym{padding:5px;background-color:white;margin-top:5px;margin:0px;-webkit-border-radius: 7px;\n" +
                "-moz-border-radius: 7px;margin-left:auto;margin-right:auto;\n" +
                "border-radius: 7px;}" +
                    "#word{padding:5px;background-color:#DCEDC8;margin:0px;-webkit-border-radius: 7px;\n" +
                "-moz-border-radius: 7px;margin-left:auto;margin-right:auto;\n" +
                "border-radius: 7px;}" +
                "#container{margin-top:6px;margin-left:auto;margin-right:auto} " +
                "#textBlock{text-align:center;margin-left:auto;margin-right:auto}" +
                "table{margin-top:6px;color:white;margin-left:auto; margin-right:auto; } table, th, td { border: 0px solid black; border-collapse: collapse; } th, td { padding: 5px; text-align: left; }"+
                "a{color:black;text-decoration: none;}"+
                "</style>";
    }


    /**
     * A method to get the term result for
     * a term ID
     * @param idTerm the term ID
     */
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
     * @param tResult the term result from the api
     */
    @Override
    public void onTermResultObtained(TermResult[] tResult){
        if(!hasResult){
            setupBaseInfo(tResult);
        }
        setupWebView(tResult);
    }

    /**
     * real javadoc will be in final class-see
     * @param statusCode status code of the error
     */
    @Override
    public void onTermResultFailure(int statusCode){
        //TODO: handle error!
    }
}
