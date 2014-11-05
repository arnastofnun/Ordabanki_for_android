package com.example.cthulhu.ordabankiforandroid;
/*
import android.os.Parcel;
import android.os.Parcelable;
*/
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
    String dictionary_code;
    String word;
    String lexical_category;
    Synonym[] synonyms;
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

    public String getDictionary_code() {
        return dictionary_code;
    }

    public void setDictionary_code(String dictionary_code) {
        this.dictionary_code = dictionary_code;
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

    public Synonym[] getSynonyms() {
        return synonyms;
    }


    public void setSynonyms(Synonym[] synonyms) {
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

