package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/*
*   Holds the functions that are implemented in
*   the select language screen
*/
public class SelectLanguageActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
    }

    //pre:view is of type View
    //post:starts the activity that sets the language of the UI to English
    public void chooseEnglish(View view){
        Intent intent = new Intent(SelectLanguageActivity.this, SearchScreen.class);
        //intent.putExtra("lang","en");
        SelectLanguageActivity.this.startActivity(intent);
    }

    //pre:view is of type View
    //post:starts the activity that sets the language of the UI to Icelandic
    public void chooseIcelandic(View view){
        Intent intent = new Intent(SelectLanguageActivity.this, SearchScreen.class);
    //    intent.putExtra("lang","en");
        SelectLanguageActivity.this.startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_language, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
