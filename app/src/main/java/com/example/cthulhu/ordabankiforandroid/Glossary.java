package com.example.cthulhu.ordabankiforandroid;

/**
 * This class is the Glossary object, which holds
 * important information about the glossaries
 * and ways to access glossary information
 * ------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 14.10.2014.
 */
public class Glossary implements Comparable<Glossary>{
    /*
    * Data invariants:
    *   code is id of glossary
    *
    *   name is name of glossary
    *
    *   url is url of glossary page
    *
    *   selected is true if this glossary
    *   is selected, false otherwise
    * */
    String dict_code;
    String name = null;
    String url = null;
    boolean selected = false;

    //use: Glossary glossary = new Glossary(code,name,selected,url);
    //pre: code,name and url are Strings, selected is a boolean
    //post: creates the glossary object
    public Glossary(String code, String name) {
        super();
        this.dict_code = code;
        this.name = name;
        this.url = "http://www.ismal.hi.is/ob/uppl/" + dict_code.toLowerCase() + ".html";
        this.selected = true;
    }

     //use: glossary.getCode()
    //pre: nothing
    //post: returns the code for the glossary, type string
    public String getDictCode() {
        return dict_code;
    }

    //use: glossary.setCode()
    //pre: code is a String
    //post:Sets the code for the glossary
    public void setCode(String code) {
        this.dict_code = code;
    }


    //use: glossary.getName()
    //pre: nothing
    //post:Returns the name of the glossary
    public String getName() {
        return name;
    }

    //use: glossary.setName()
    //pre: name is of type String
    //post:Sets the name of the glossary
    public void setName(String name) {
        this.name = name;
    }

    //use: glossary.getUrl()
    //pre: nothing
    //post:Returns the url of the glossary
    public String getUrl() {
        return url;
    }

    //use: glossary.setUrl()
    //pre: url is of type String
    //post: sets the url of the glossary
    public void setUrl(String url) {
        this.url = url;
    }

    //use: glossary.isSelected()
    //pre: nothing
    //post: Returns true if glossary is selected, else false
    public boolean isSelected() {
        return selected;
    }

    //use: glossary.setSelected()
    //pre: seæected is of type boolean
    //post: sets glossary selected status
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int compareTo(Glossary g){
        return getName().compareTo(g.getName());
    }

}
