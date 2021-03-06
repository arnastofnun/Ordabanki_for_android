package com.arnastofnun.idordabanki.models;

/**
 * Holder object for parsing of term result jsons. Repeated variable names are a product of
 * Gson needing the variables to have the same name as json field.
 * @author Bill
 * @since 04.11.2014
 */
public class TermResult {
    Term term;


    /**
     * A class containing informations about
     * a term
     */
    public static class Term{
        String id;
        String dict_code;
        String category;
        String subcategory;
        Word[] words;
        Sbr[] sbr;
        Einnig[] einnig;

        /**
         * A class containing information about a word
         */
        public static class Word{
            String id;
            String lang_code;
            String word;
            String domain;
            String definition;
            String example;
            String othergrammar;
            String dialect;
            String abbreviation;
            String explanation;
            Synonym[] synonyms;

            /**
             * A class containing information
             * about a synonym
             */
            public static class Synonym{
                String fkword;
                String synonym;
                String pronunciation;
                String othergrammar;
                String dialect;
                String abbreviation;

                /**
                 * @return the fkWord number, used as an id number
                 */
                public String getfkWord(){
                    return fkword;
                }

                /**
                 * @return the synonym
                 */
                public String getSynonym(){
                    return synonym;
                }

                /**
                 * @return the pronunciation of the synonym
                 */
                public String getPronunciation(){
                    return pronunciation;
                }

                /**
                 * @return OtherGrammar value of the synonym
                 */
                public String getOtherGrammar(){
                    return othergrammar;
                }

                /**
                 * @return Dialect of the synonym
                 */
                public String getDialect(){
                    return dialect;
                }

                /**
                 * @return Abbreviation of the synonym
                 */
                public String getAbbreviation(){
                    return abbreviation;
                }
            }
            /**
             * @return the id number of the word
             */
            public String getId(){
                return id;
            }

            /**
             * @return the Language code of the word
             */
            public String getLangCode(){
                return lang_code;
            }

            /**
             * @return the word
             */
            public String getWord() {
                return word;
            }

            /**
             * @return the Domain of the word
             */
            public String getDomain(){
                return domain;
            }

            /**
             * @return the definition of the word
             */
            public String getDefinition(){
                return definition;
            }

            /**
             * @return an example of usage of the word
             */
            public String getExample(){
                return example;
            }

            /**
             * @return Other Grammar of the word
             */
            public String getOtherGrammar(){
                return othergrammar;
            }

            /**
             * @return the dialect of the word
             */
            public String getDialect(){
                return dialect;
            }

            /**
             * @return the Abbreviation of the word
             */
            public String getAbbreviation(){
                return abbreviation;
            }

            /**
             * @return an Explanation of the word
             */
            public String getExplanation(){
                return explanation;
            }

            /**
             * @return an array of Synonyms of the word, if available
             */
            public Synonym[] getSynonyms(){
                return synonyms;
            }

            /**
             * This method checks if the word has
             * synonym parent
             * @return true if it has,else false
             */
            public boolean hasSynParent(){
                return (abbreviation != null || definition != null || dialect != null
                        || domain != null|| example != null || explanation != null
                        || othergrammar != null);
            }

        }

        public static class Sbr{
            String sbr_term;
            Refs[] refs;

            public static class Refs{
                String lang_code;
                String word;

                /**
                 * @return The Language code of the refs sub-array of Sbr
                 */
                public String getLangCode(){
                    return lang_code;
                }

                /**
                 * @return The word included in the refs sub-array of Sbr
                 */
                public String getWord(){
                    return word;
                }

            }

            /**
             * @return the number sbr_term, used as an id number.
             */
            public String getTerm(){
                return sbr_term;
            }

            /**
             * @return the Refs sub-array of Sbr
             */
            public Refs[] getRefs(){
                return refs;
            }
        }

        public static class Einnig{
            String einnig_term;
            Refs[] refs;

            public static class Refs{
                String lang_code;
                String word;

                /**
                 * @return The Language code of the refs Sub-array of Einnig
                 */
                public String getLangCode(){
                    return lang_code;
                }

                /**
                 * @return The Word in the the refs Sub-array of Einnig
                 */
                public String getWord(){
                    return word;
                }
            }

            /**
             * @return the einnig_term number, used as an Id number.
             */
            public String getTerm(){
                return einnig_term;
            }

            /**
             * @return the Refs sub-array of Einnig
             */
            public Refs[] getRefs(){
                return refs;
            }
        }
    }

    /**
     * @return The ID of the term
     */
    public String getTermId(){
        return term.id;
    }

    /**
     * @return The Dictionary code of the term
     */
    public String getDictCode(){
        return term.dict_code;
    }

    /**
     * @return The category code of the term
     */
    public String getCategory(){
        return term.category;
    }

    /**
     * @return The subcategory code of the term
     */
    public String getSubcategory(){
        return term.subcategory;
    }

    /**
     * @return The Words Sub-array of Term
     */
    public Term.Word[] getWords(){
        return term.words;
    }

    /**
     * @return the Sbr Sub-array of Term
     */
    public Term.Sbr[] getSbr(){
        return term.sbr;
    }

    /**
     * @return The Einnig Sub-array of Term
     */
    public Term.Einnig[] getEinnig(){
        return term.einnig;
    }



}
