package com.arnastofnun.idordabanki.models;
/*
import android.os.Parcel;
import android.os.Parcelable;
*/

import android.support.annotation.NonNull;

import com.arnastofnun.idordabanki.Globals;
import com.arnastofnun.idordabanki.preferences.SharedPrefs;
import com.google.common.collect.BiMap;

/**
 * This class is the Result object, which holds
 * important information about the results
 * and ways to access result information
 * ------------------------------------------------
 * @author Karl Ásgeir Geirsson, Bill Hayhurst
 * @since 14.10.2014 modified 20.10.2014
 */
public class Result implements Comparable<Result>{
    //Data invariants:
    String id_word; //id of the word (different between languages)
    String id_term; //id of the term (same for all languages)
    String language_code; //code of the language
    String dictionary_code; //code of the dictionary
    String word; //the word
    String lexical_category; //the lexical category (kk, kvk, ..)
    Synonym[] synonyms; //Array of Synonym classes
    String definition; //Definition for the word
    String example; //Example of use of the word

    /**
     * Keeps the synonym strings
     * @author Bill
     * @since  20.10.2014
     */
    public static class Synonym {
        public String synonym;

        /**
         * Creates a new instance of the Synonym class
         */
        public Synonym() {
            //noargs constructor
        }

        /**
         * Method to get the synonym
         * @return synonym
         */
        public String getSynonym(){return synonym;}
    }

   /**
    * getters and setters (setters not really needed but here anyway just in case)
    *  use: result.get*Var*()  where *Var* is the variable of interest
    *  pre: nothing
    *  post: returns *Var*
    *
    *  use: result.set*Var*(String *Var*)
    *  pre: *Var* is a string
    *  post: sets *Var* for result
    */

    /**
     * @return the word id
     */
    public String getId_word() {
        return id_word;
    }

    /**
     * @param id_word the word id id be set
     */
    public void setId_word(String id_word) {
        this.id_word = id_word;
    }

    /**
     * @return the term id
     */
    public String getId_term() {
        return id_term;
    }

    /**
     * @param id_term the term id to be set
     */
    public void setId_term(String id_term) {
        this.id_term = id_term;
    }

    /**
     * @return the language code
     */
    public String getLanguage_code() {
        return language_code;
    }

    /**
     * @param language_code the language code to be set
     */
    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    /**
     * @return the dictionary code
     */
    public String getDictionary_code() {
        return dictionary_code;
    }

    /**
     * @param dictionary_code the dictionary code to be set
     */
    public void setDictionary_code(String dictionary_code) {
        this.dictionary_code = dictionary_code;
    }

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * @param word is the word to be set
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * @return the lexical category
     */
    public String getLexical_category() {
        return lexical_category;
    }

    /**
     * @param lexical_category the lexical category to be set
     */
    public void setLexical_category(String lexical_category) {
        this.lexical_category = lexical_category;
    }

    /**
     * @return an array of Synonym objects
     */
    public Synonym[] getSynonyms() {
        return synonyms;
    }

    /**
     * @param synonyms array of Synonym objects to be set
     */
    public void setSynonyms(Synonym[] synonyms) {
        this.synonyms = synonyms;
    }

    /**
     * @return the definition
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * @param definition the definition to be set
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     * @return the example
     */
    public String getExample() {
        return example;
    }

    /**
     * @param example the example to be set
     */
    public void setExample(String example) {
        this.example = example;
    }


    /**
     * A method that tells if this result equals another result
     * @param r the result to compare
     * @return true if they are equal, else false
     */
    public boolean equals(Result r){
        return (getWord().equals(r.getWord()) && getDictionary_code().equals(r.getDictionary_code())&&getLanguage_code().equals(r.getLanguage_code()));
    }


    /**
     * Written by Karl Ásgeir Geirsson
     * use: int cmp = result.compareTo(result2);
     * @param r is the Result to be compared to
     * @return returns an int indicating how this Result
     *          compares to the result r
     */
    @Override
    public int compareTo(@NonNull Result r){
        Globals globals = (Globals) Globals.getContext();
        BiMap<String,String> dictionaries = SharedPrefs.getStringBiMap("loc_dictionaries");
        BiMap<String,String> languages = SharedPrefs.getStringBiMap("languages");

        //Compare the words alphabetically
        int comp1 = getWord().compareTo(r.getWord());
        //If the words are equal, compare by glossary name
        if(comp1 == 0){
            int comp2;
            if(getDictionary_code()!=null && r.getDictionary_code()!=null) {
                comp2 = dictionaries.get(getDictionary_code()).compareTo(dictionaries.get(r.getDictionary_code()));
            } else{
                comp2 = 0;
            }
            if(comp2 == 0){
                if(getLanguage_code()!= null && r.getLanguage_code() !=null) {
                    return languages.get(getLanguage_code()).compareTo(languages.get(r.getLanguage_code()));
                }
                else return 0;
            }
            else return comp2;
        }
        else return comp1;

    }
}

