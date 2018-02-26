package com.example.danieljackson.flickr_findr.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.util.Log;

import com.example.danieljackson.flickr_findr.data.provider.FlickrFindrSuggestionProvider;

public class SuggestionDelegate {

    private static final String TAG = SuggestionDelegate.class.getSimpleName();

    public void checkForNewSearchSuggestion(Context context, Intent intent, Callback callback) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.i(TAG, "New query: " + query);

            if (query != null && !query.isEmpty()) {
                if (callback != null) {
                    callback.onNewQueryReceived(query);
                }

                saveSearchAsSuggestion(context, query);
            }
        }
    }

    private void saveSearchAsSuggestion(Context context, String query) {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(context,
                FlickrFindrSuggestionProvider.AUTHORITY, FlickrFindrSuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, null);
    }

    public interface Callback {
        void onNewQueryReceived(String query);
    }
}
