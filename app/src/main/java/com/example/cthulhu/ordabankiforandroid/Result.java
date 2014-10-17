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
    /*  Data invariants:
     *  term: term of search result
     *  language: language of search result
     *  glossary: glossary of search result
     */
    String term = null;
    String language = null;
    String glossary = null;
    

    /**
    *  use: Result result = new Result(term,language,glossary)
    * @param term type String
    * @param language type String
    * @param glossary type String
    * @return nothing, creates the result object
    */
    public Result(String term, String language, String glossary) {
        super();
        this.term = term;
        this.language = language;
        this.glossary = glossary;
    }
    
    
    /**
    * 
    * @return the term for the result
    */
    public String getTerm() {
        return term;
    }
    
    /**
    *  use:result.setTerm();
    * @param term type String
    * @return nothing, set the term for the result
    */
    public void setTerm(String term) {
        this.term = term;
    }
    
    /**
    *  use:result.getLanguage();
    * @return the language of the result
    */
    public String getLanguage() {
        return language;
    }

    /**
     *  use:result.setLanguage(language);
    *   pre:language is a string
    *   @param language
    *   @return nothing, sets the language of the glossary
    */    
    public void setLanguage(String language) {
        this.language = language;
    }
    
     /**
     *  use:result.getGlossary();
    *   @return the glossary of the result
    */   
    public String getGlossary() {
        return glossary;
    }
    
     /**
     *  use:result.setUrl(glossary);
    *   pre:glossary is a string
    *   @param glossary
    *   @return nothing, sets the glossary of the result
    */   
    public void setUrl(String glossary) {
        this.glossary = glossary;
    }

}
