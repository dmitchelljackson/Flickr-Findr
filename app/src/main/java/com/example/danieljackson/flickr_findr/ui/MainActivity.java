package com.example.danieljackson.flickr_findr.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.danieljackson.flickr_findr.R;
import com.example.danieljackson.flickr_findr.data.network.model.Photo;
import com.example.danieljackson.flickr_findr.ui.photo.PhotoViewerFragment;
import com.example.danieljackson.flickr_findr.ui.search.SearchFragment;

public class MainActivity extends BaseActivity implements Router {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState == null) {
            loadSearchFragment();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void loadSearchFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.class.getSimpleName());
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
        if(fragment != null && fragment instanceof SearchFragment) {
            ((SearchFragment) fragment).clearFocus();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame, PhotoViewerFragment.newInstance(photo), PhotoViewerFragment.TAG)
                .addToBackStack(photo.getTitle())
                .commit();
    }

}
