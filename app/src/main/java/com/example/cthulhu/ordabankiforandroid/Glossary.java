package com.example.cthulhu.ordabankiforandroid;

/**
 * Created by karlasgeir on 14.10.2014.
 * This class contains the glossary object
 */
public class Glossary{

    String code = null;
    String name = null;
    boolean selected = false;

    //Creates the glossary object
    public Glossary(String code, String name, boolean selected) {
        super();
        this.code = code;
        this.name = name;
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

    //Returns true if glossary is selected, else false
    public boolean isSelected() {
        return selected;
    }

    //Sets glossary selected status
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
