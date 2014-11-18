package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONException;

/**
 * placeholder activity to stop errors until term and synonym searches are ready to be integrated
 * @author Bill
 * @since 01/11/14
 */
public class AnActivity extends Activity implements OnTermResultObtainedListener, OnSynonymResultObtainedListener{

    /**
     * real javadoc will be in final class-see ResultScreen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * real javadoc will be in final class-see ResultScreen
     * @param sTerm
     */
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

    /**
     * real javadoc will be in final class-see ResultScreen
     * @param sTerm
     */
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

    /**
     * real javadoc will be in final class-see ResultScreen
     * @param tResult
     */
    @Override
    public void onTermResultObtained(TermResult[] tResult){

    }

    /**
     * real javadoc will be in final class-see ResultScreen
     * @param statusCode
     */
    @Override
    public void onTermResultFailure(int statusCode){

    }

    /**
     * real javadoc will be in final class-see ResultScreen
     * @param sResult
     */
    @Override
    public void onSynonymResultObtained(SynonymResult[] sResult){

    }

    /**
     * real javadoc will be in final class-see ResultScreen
     * @param statusCode HTTP status code. No result on 200, otherwise error.
     */
    @Override
    public void onSynonymResultFailure(int statusCode){

    }
}
