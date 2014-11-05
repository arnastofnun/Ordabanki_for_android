package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONException;

/**
 * Created by cthulhu on 04/11/14.
 */
public class AnActivity extends Activity implements OnTermResultObtainedListener, OnSynonymResultObtainedListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
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
    @Override
    public void onTermResultObtained(TermResult[] tResult){

    }
    @Override
    public void onTermResultFailure(int statusCode){

    }
    @Override
    public void onSynonymResultObtained(SynonymResult[] sResult){

    }
    @Override
    public void onSynonymResultFailure(int statusCode){

    }
}
