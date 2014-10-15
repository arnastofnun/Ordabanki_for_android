package com.example.cthulhu.ordabankiforandroid;

/**
 * This class is the Result object, which holds
 * important information about the results
 * and ways to access result information
 * ------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @date 14.10.2014.
 */
public class Result{
    //Initialize
    String term = null;
    String language = null;
    String glossary = null;

    //Creates the result object
    public Result(String term, String language, String glossary) {
        super();
        this.term = term;
        this.language = language;
        this.glossary = glossary;
    }

    //Returns the term for the result
    public String getTerm() {
        return term;
    }

    //Sets the term for the result
    public void setTerm(String term) {
        this.term = term;
    }

    //Returns the language of the result
    public String getLanguage() {
        return language;
    }

    //Sets the language of the glossary
    public void setLanguage(String language) {
        this.language = language;
    }

    //Returns the glossary of the result
    public String getGlossary() {
        return glossary;
    }

    //Sets the glossary of the result
    public void setUrl(String glossary) {
        this.glossary = glossary;
    }

}
