package com.arnastofnun.idordabanki.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * This class is the Glossary object, which holds
 * important information about the glossaries
 * and ways to access glossary information
 * ------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 14.10.2014
 */
public class Glossary implements Comparable<Glossary>, Parcelable{
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
    boolean selected = true;

    /**
     * use: Glossary glossary = new Glossary(code,name,selected,url);
     * Creates a new instance of the Glossary class
     * Written by Karl Ásgeir Geirsson
     * @param code is a String and represents the glossary code
     * @param name is a String and represents the glossary name
     */
    public Glossary(String code, String name) {
        super();
        this.dict_code = code;
        this.name = name;
        //TODO: get the url from API, or check if it exists in some way
        //temporary fix until API provides the values, check string that contains glossaries currently online
        String glossaryLinks = "bilord.html-arkitekt.html-byggverk.html-edlisfr.html-efnafr.html-endur.html-erfdafr.html-flug.html-fundarord.html-gjaldmidlar.html-hagfr.html-jardfr.html-laekn.html-landafr.html-liford.html-lisa.html-malfr.html-krydd.html-vidur.html-nyyrdi.html-onaemisfr.html-flora.html-raft.html-rettritun.html-rikjaheiti.html-sjodyr.html-pisces.html-sjomenn.html-stjornsysla.html-stjarna.html-thy.html-timbur.html-tolfr.html-tolva.html-saela.html-verkst.html-upplysingafraedi.htm-umhv.htm-malmur.htm-vedur.htm";
        String[] glossaryLinksArray = glossaryLinks.split("-");
        String dictLowerCase = dict_code.toLowerCase();
        if(glossaryLinks.contains(dictLowerCase))
        {
            for(String actualLink:glossaryLinksArray){
                if(actualLink.contains(dictLowerCase)){
                    this.url = "http://www.ismal.hi.is/ob/uppl/" + actualLink;
                }
            }
        }else{
            this.url = "";
        }
        this.selected = true;
    }


    /**
     * Written by Karl Ásgeir Geirsson
     * use: glossary.getCode()
     * @return the code for the glossary, type string
     */
    public String getDictCode() {
        return dict_code;
    }


    /**
     * Written by Karl Ásgeir Geirsson
     * use: glossary.setCode()
     * @param code is a String
     * post:Sets the code for the glossary
     */
    public void setCode(String code) {
        this.dict_code = code;
    }

    /**
     * Written by Karl Ásgeir Geirsson
     * use: glossary.getName()
     * @return the name of the glossary
     */
    public String getName() {
        return name;
    }


    /**
     * Written by Karl Ásgeir Geirsson
     * use: glossary.setName()
     * @param name is of type String
     * post:Sets the name of the glossary
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Written by Karl Ásgeir Geirsson
     * use: glossary.getUrl()
     * @return the url of the glossary
     */
    public String getUrl() {
        return url;
    }

    /**
     * Written by Karl Ásgeir Geirsson
     * use: glossary.setUrl()
     * @param url is a legal URL of type string
     * post: sets the url of the glossary
     */
    public void setUrl(String url) {
        this.url = url;
    }



    /**
     * Written by Karl Ásgeir Geirsson
     * use: glossary.isSelected()
     * @return true if glossary is selected, else false
     */
    public boolean isSelected() {
        return selected;
    }


    /**
     * Written by Karl Ásgeir Geirsson
     * @param selected is of type boolean
     * post: sets glossary selected status
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Written by Karl Ásgeir Geirsson
     * @param g is of type Glossary, to compare with this glossary
     * @return integer that indicates the order these should be compared in
     */
    public int compareTo(@NonNull Glossary g){
        return getName().compareTo(g.getName());
    }


    public static final Parcelable.Creator<Glossary> CREATOR = new Parcelable.ClassLoaderCreator<Glossary>(){

        @Override
        public Glossary createFromParcel(Parcel in){
            return new Glossary(in);
        }

        @Override
        public Glossary createFromParcel(Parcel in,ClassLoader classLoader){
            return new Glossary(in);
        }

        @Override
        public Glossary[] newArray(int size) {
            return new Glossary[size];
        }
    };

    @Override
    public int describeContents(){
        return hashCode();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(dict_code);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeInt((selected) ? 1:0);
    }

    private Glossary(Parcel in){
        dict_code = in.readString();
        name = in.readString();
        url = in.readString();
        selected = (in.readInt() == 1);
    }


}
