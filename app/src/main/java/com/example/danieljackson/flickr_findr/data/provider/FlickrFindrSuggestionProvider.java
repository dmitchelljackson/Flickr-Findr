package com.example.danieljackson.flickr_findr.data.provider;

import android.content.SearchRecentSuggestionsProvider;

public class FlickrFindrSuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "com.example.danieljackson.flickr_findr";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public FlickrFindrSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
