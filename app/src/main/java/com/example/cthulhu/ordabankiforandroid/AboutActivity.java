package com.example.cthulhu.ordabankiforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * ITEM ON DROPDOWN MENU ON ACTIONBAR
 * PLEASE SEE
 * http://ordabanki.hi.is/wordbank/about
 * http://ordabanki.hi.is/wordbank/about?changeToWebLanguage=IS
 * Created by tka on 24.10.2014.
 *
 */
public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_activity);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_settings){
            View v = findViewById(R.id.action_settings);
            Settings settings = new Settings(this);
            settings.createOptionsPopupMenu(v,AboutActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
