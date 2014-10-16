package com.example.cthulhu.ordabankiforandroid;

/**
 * This class is the Result object, which holds
 * important information about the results
 * and ways to access result information
 * ------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 14.10.2014.
 */
public class Result{
    //Data invariants:
    //  term: term of search result
    //  language: language of search result
    //  glossary: glossary of search result
    String term = null;
    String language = null;
    String glossary = null;
    
    //Result result = new Result(term,language,glossary)
    //pre:term,language and glossary are of type String
    //post:Creates the result object
    public Result(String term, String language, String glossary) {
        super();
        this.term = term;
        this.language = language;
        this.glossary = glossary;
    }
    
    //use:result.getTerm();
    //pre:nothing
    //post:Returns the term for the result
    public String getTerm() {
        return term;
    }
    //use:result.setTerm(term);
    //pre: term is a string
    //post:sets the term for the result
    public void setTerm(String term) {
        this.term = term;
    }
    
    //use:result.getLanguage();
    //pre:nothing
    //post:Returns the language of the result
    public String getLanguage() {
        return language;
    }

    //use:result.setLanguage(language);
    //pre:language is a string
    //post:sets the language of the glossary
    public void setLanguage(String language) {
        this.language = language;
    }
    
    //use:result.getGlossary();
    //pre: nothing
    //post:returns the glossary of the result
    public String getGlossary() {
        return glossary;
    }
    
    //use:result.setUrl(glossary);
    //pre:glossary is a string
    //post:sets the glossary of the result
    public void setUrl(String glossary) {
        this.glossary = glossary;
    }

}
