package com.example.danieljackson.flickr_findr.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.danieljackson.flickr_findr.FlickrFindrApp;
import com.example.danieljackson.flickr_findr.R;
import com.example.danieljackson.flickr_findr.data.network.model.Photo;
import com.example.danieljackson.flickr_findr.ui.photo.PhotoViewerFragment;
import com.example.danieljackson.flickr_findr.ui.search.SearchFragment;
import com.example.danieljackson.flickr_findr.ui.search.SuggestionDelegate;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements Router {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    SuggestionDelegate suggestionDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FlickrFindrApp.getAppComponent().inject(this);

        if (savedInstanceState == null && !searchFragmentIsAttached()) {
            loadSearchFragment();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.TAG);
        if(fragment != null &&  fragment instanceof SearchFragment && searchFragmentIsAttached()) {
            suggestionDelegate.checkForNewSearchSuggestion(this, intent, (SearchFragment) fragment);
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void loadSearchFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.TAG);
        if (fragment == null) {
            fragment = new SearchFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment, SearchFragment.TAG)
                .addToBackStack(SearchFragment.TAG)
                .commit();
    }

    @Override
    public void loadPhotoViewerFragment(Photo photo) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.TAG);
        if (fragment != null && fragment instanceof SearchFragment) {
            ((SearchFragment) fragment).clearFocus();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame, PhotoViewerFragment.newInstance(photo), PhotoViewerFragment.TAG)
                .addToBackStack(photo.getTitle())
                .commit();
    }

    private boolean searchFragmentIsAttached() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.TAG);
        return fragment != null && !fragment.isDetached();
    }


}
