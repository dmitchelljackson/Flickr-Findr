package com.example.danieljackson.flickr_findr.ui;

import com.example.danieljackson.flickr_findr.data.network.model.Photo;

public interface Router {
    void loadSearchFragment();

    void loadPhotoViewerFragment(Photo photo);
}
