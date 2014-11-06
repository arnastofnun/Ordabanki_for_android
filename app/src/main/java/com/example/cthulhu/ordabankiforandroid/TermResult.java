package com.example.cthulhu.ordabankiforandroid;

/**
 * Created by cthulhu on 04/11/14.
 */
/*sample 1 -synonyms only
*[{"term":{"id":481413,"dict_code":"ERFDAFR","category":null,"subcategory":null,"words":[{"id":1127859,
*"lang_code":"EN","word":"recombinant DNA technology","domain":null,"definition":null,"example":null,
*"othergrammar":null,"dialect":null,"abbreviation":null,"explanation":null,"synonyms":[null]},
*{"id":1127858,"lang_code":"IS","word":"DNA-tækni","domain":null,"definition":null,"example":null,
*"othergrammar":null,"dialect":null,"abbreviation":null,"explanation":null,
*"synonyms":[{"fkword":1127858,"synonym":"genatækni","pronounciation":null,"othergrammar":null,
*"dialect":null,"abbreviation":null},{"fkword":1127858,"synonym":"erfðatækni","pronounciation":null,
*"othergrammar":null,"dialect":null,"abbreviation":null}]}],"sbr":[null],"einnig":[null]}}]
*
*Sample 2 - sbr
*[{"term":{"id":328471,"dict_code":"FLUG","category":null,"subcategory":"17","words":[{"id":769631,
* "lang_code":"EN","word":"grey-out","domain":null,"definition":null,"example":null,"othergrammar":null,
* "dialect":null,"abbreviation":null,"explanation":null,"synonyms":[null]},{"id":769630,"lang_code":"IS",
* "word":"grámasýni","domain":null,"definition":"Sjónskynjun þegar birta virðist minnka vegna þess að líkaminn verður fyrir jákvæðu hröðunarálagi.",
* "example":null,"othergrammar":null,"dialect":null,"abbreviation":null,"explanation":null,
* "synonyms":[null]}],"sbr":[{"sbr_term":329439,"refs":[{"lang_code":"EN","word":"blackout"},
* {"lang_code":"IS","word":"sjónmyrkvi"}]},{"sbr_term":330879,"refs":[{"lang_code":"EN","word":"red-out"},
* {"lang_code":"IS","word":"roðasýni"}]}],"einnig":[null]}}]
*
* Sample 3 -sjá einnig
*[{"term":{"id":295692,"dict_code":"TOLFR","category":null,"subcategory":null,"words":[{"id":697818,
* "lang_code":"IS","word":"hlutfallskvarði","domain":null,"definition":"Mælikvarði með jafnar einingar og núll sem minnsta mögulega gildi.",
* "example":null,"othergrammar":null,"dialect":null,"abbreviation":null,"explanation":null,"synonyms":[null]},
* {"id":697819,"lang_code":"EN","word":"ratio scale","domain":null,"definition":null,"example":null,
* "othergrammar":null,"dialect":null,"abbreviation":null,"explanation":null,"synonyms":[null]}],"sbr":[null],
* "einnig":[{"einnig_term":295890,"refs":[{"lang_code":"IS","word":"jafnbilakvarði"},
* {"lang_code":"EN","word":"interval scale"}]},{"einnig_term":296096,"refs":[{"lang_code":"IS","word":"flokkunarkvarði"},
 * {"lang_code":"EN","word":"nominal scale"}]},{"einnig_term":296172,"refs":[{"lang_code":"IS","word":"raðkvarði"}, {"lang_code":"EN","word":"ordinal scale"}]}]}}]
*/

/**
 * Holder object for parsing of term result jsons. Repeated variable names are a product of
 * Gson needing the variables to have the same name as json field.
 * @author Bill
 * todo add getters and setters
 */
public class TermResult {
    Term term;
    public static class Term{
        String id;
        String dict_code;
        String category;
        String subcategory;
        Word[] words;
        Sbr[] sbr;
        Einnig[] einnig;

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

            public static class Synonym{
                String fkword;
                String synonym;
                String pronunciation;
                String othergrammar;
                String dialect;
                String abbreviation;
            }

        }

        public static class Sbr{
            String sbr_term;
            Refs[] refs;

            public static class Refs{
                String lang_code;
                String word;
            }
        }

        public static class Einnig{
            String einnig_term;
            Refs[] refs;

            public static class Refs{
                String lang_code;
                String word;
            }
        }
    }
}
