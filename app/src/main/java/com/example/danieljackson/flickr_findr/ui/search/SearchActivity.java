package com.example.danieljackson.flickr_findr.ui.search;

import android.os.Bundle;

import com.example.danieljackson.flickr_findr.R;
import com.example.danieljackson.flickr_findr.ui.BaseActivity;

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.frame, new SearchFragment(), SearchFragment.class.getSimpleName()).commit();
        }
    }
}
