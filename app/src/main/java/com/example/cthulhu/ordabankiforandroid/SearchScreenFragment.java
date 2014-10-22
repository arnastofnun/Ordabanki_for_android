package com.example.cthulhu.ordabankiforandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;


 //Created by karlasgeir on 9.10.2014.

/**
 * <h1>Search Screen fragment</h1>
 * <p>This class contains the methods for the search screen fragment</p>
 * <p>It is supposed to get the source and target languages from the API and
 * display them in the source and target language spinners</p>
 * <p>For now it just puts placeholder values on the spinners</p>
 * -------------------------------------------------------------------------
 * @author Karl Ásgeir Geirsson
 * @since 09.10.2014
 */
public class SearchScreenFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_search_screen,container,false);

        //Adding search by pressing enter functionality

        EditText searchView = (EditText) rootView.findViewById(R.id.searchView);


        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    try {
                        ((SearchScreen)getActivity()).search(v);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });





        return rootView;
    }




}
