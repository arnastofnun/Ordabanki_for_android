package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONException;

/**
 * placeholder activity to stop errors until term and synonym searches are ready to be integrated
 * @author Bill
 */
public class AnActivity extends Activity implements OnTermResultObtainedListener, OnSynonymResultObtainedListener{

    //TODO: javadocs
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //TODO: javadocs
    private void setSynonymResults(String sTerm){
        SynonymResultJsonHandler sJsonHandler = new SynonymResultJsonHandler(this);
        OrdabankiRestClientUsage sClient = new OrdabankiRestClientUsage();
        try {
            String synURL = OrdabankiURLGen.createSynonymURL(sTerm);
            sClient.setSynonymResults(synURL, sJsonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //TODO: javadocs
    private void setTermResults(String sTerm){
        TermResultJsonHandler tJsonHandler = new TermResultJsonHandler(this);
        OrdabankiRestClientUsage tClient = new OrdabankiRestClientUsage();
        try {
            String termURL = OrdabankiURLGen.createTermURL(sTerm);
            tClient.setTermResults(termURL, tJsonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //TODO: javadocs
    @Override
    public void onTermResultObtained(TermResult[] tResult){

    }
    //TODO: javadocs
    @Override
    public void onTermResultFailure(int statusCode){

    }
    //TODO: javadocs
    @Override
    public void onSynonymResultObtained(SynonymResult[] sResult){

    }
    //TODO: javadocs
    @Override
    public void onSynonymResultFailure(int statusCode){

    }
}
