package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by cthulhu on 04/11/14.
 */
public class AnActivity extends Activity implements OnTermResultObtainedListener, OnSynonymResultObtainedListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
