package com.example.cthulhu.ordabankiforandroid;
/*
import android.os.Parcel;
import android.os.Parcelable;
*/
import java.util.ArrayList;

/**
 * This class is the Result object, which holds
 * important information about the results
 * and ways to access result information
 * ------------------------------------------------
 * @author Karl Ásgeir Geirsson, Bill Hayhurst
 * @since 14.10.2014.modified 20/11/14
 */
public class Result {
    //Data invariants:
    //  term: term of search result
    //  language: language of search result
    //  glossary: glossary of search result

    String id_word;
    String id_term;
    String language_code;
    String language_name;
    String terminology_dictionary;
    String word;
    String lexical_category;
    ArrayList<Synonym> synonyms;
    String definition;
    String example;

    public static class Synonym {
        public String synonym;

        public Synonym() {
            //noargs constructor
        }
        public String getSynonym(){return synonym;}
    }
/*

    * getters and setters (setters not really needed but here anyway just in case)
    *  use: result.get*Var*()  where *Var* is the variable of interest
    *  pre: nothing
    *  post: returns *Var*
    *
    *  use: result.set*Var*(String *Var*)
    *  pre: *Var* is a string
    *  post: sets *Var* for result
     */
    public String getId_word() {
        return id_word;
    }

    public void setId_word(String id_word) {
        this.id_word = id_word;
    }

    public String getId_term() {
        return id_term;
    }

    public void setId_term(String id_term) {
        this.id_term = id_term;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public String getTerminology_dictionary() {
        return terminology_dictionary;
    }

    public void setTerminology_dictionary(String terminology_dictionary) {
        this.terminology_dictionary = terminology_dictionary;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLexical_category() {
        return lexical_category;
    }

    public void setLexical_category(String lexical_category) {
        this.lexical_category = lexical_category;
    }

    public ArrayList<Synonym> getSynonyms() {
        return synonyms;
    }


    public void setSynonyms(ArrayList<Synonym> synonyms) {
        this.synonyms = synonyms;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}

