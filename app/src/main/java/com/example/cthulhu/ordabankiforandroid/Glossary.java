package com.example.cthulhu.ordabankiforandroid;

/**
 * This class is the Glossary object, which holds
 * important information about the glossaries
 * and ways to access glossary information
 * ------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @date 14.10.2014.
 */
public class Glossary{
    //Initialize
    String code = null;
    String name = null;
    String url = null;
    boolean selected = false;

    //Creates the glossary object
    public Glossary(String code, String name, boolean selected, String url) {
        super();
        this.code = code;
        this.name = name;
        this.url = url;
        this.selected = selected;
    }

    //Returns the code for the glossary
    public String getCode() {
        return code;
    }

    //Sets the code for the glossary
    public void setCode(String code) {
        this.code = code;
    }

    //Returns the name of the glossary
    public String getName() {
        return name;
    }

    //Sets the name of the glossary
    public void setName(String name) {
        this.name = name;
    }

    //Returns the url of the glossary
    public String getUrl() {
        return url;
    }

    //Sets the url of the glossary
    public void setUrl(String url) {
        this.url = url;
    }

    //Returns true if glossary is selected, else false
    public boolean isSelected() {
        return selected;
    }

    //Sets glossary selected status
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
