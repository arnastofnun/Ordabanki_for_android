package com.arnastofnun.idordabanki;


import java.util.ArrayList;
import java.util.ListIterator;

/**

 * This class creates URLs to be passed to the client from paramters within the app
 * <h1>OrðabankiURLGen</h1>
 * <p>Creates URLs to be passed into the rest client</p>
 * @author Bill
 * @since 13.10.2014

 */
class OrdabankiURLGen {

    final static String baseURL = "http://api.arnastofnun.is/ordabanki.php?";

/*    public static String createWordOnlyURL(String sTerm){
        //takes base URL and appends search term
        final String baseURL = "http://api.arnastofnun.is/ordabanki.php?word=";
        return baseURL+ sTerm;
    }
*/
    /**
     * creates URL with word search parameter
     * @param sTerm search word
     * @return relative url
     */
    public static String createWordURL(String sTerm) {
        //takes base URL and appends search term
        return appendParams(baseURL + "word="+sTerm);
    }

    /**
     * creates url with term search parameter
     * @param sTerm search term
     * @return relative url
     */

    public static String createTermURL(String sTerm){
        return appendParams(baseURL + "term="+sTerm);
    }

    /**
     * creates url with synonym search parameter
     * @param sTerm synonym search term
     * @return relative url
     */
    public static String createSynonymURL(String sTerm){
        return appendParams(baseURL +"synonym="+sTerm);
    }

    /**
     * appends extra parameters to base search string with search term
     * @param relURL base term with main search parameter appended
     * @return relative url
     */
    private static String appendParams(String relURL){
        final String delim = ",";
        if (!ChooseLanguagesFragment.getSourceLanguage().equals("ALL"))
            relURL = relURL + "&slang="+ChooseLanguagesFragment.getSourceLanguage();
            /* commented until target language implemented in api
        if(!ChooseLanguagesFragment.getTargetLanguage().equals("ALL"))
            relURL = relURL + "&tlang=" + ChooseLanguagesFragment.getTargetLanguage();
             */
        if(!PickGlossaryFragment.areAllSelected()) {
                relURL = relURL + "&dicts=";
                ArrayList<String> selectedGlossaries = PickGlossaryFragment.getSelectedGlossaries();
        if (selectedGlossaries.size() == 1) {
            relURL = relURL + selectedGlossaries.get(0);
        } else {
            ListIterator<String> it = selectedGlossaries.listIterator();
            //iterate until last but one member,
            while (it.hasNext() && it.nextIndex() != selectedGlossaries.size() - 1) {
                relURL = relURL + it.next() + delim;
            }
            relURL = relURL + selectedGlossaries.get(selectedGlossaries.size() - 1);
        }
    }
    return relURL+"&agent=ordabankaapp";
    }
}



