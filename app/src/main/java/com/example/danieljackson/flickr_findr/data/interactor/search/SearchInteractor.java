package com.example.danieljackson.flickr_findr.data.interactor.search;


import com.example.danieljackson.flickr_findr.data.network.model.Photos;
import com.jakewharton.rxrelay2.Relay;

public interface SearchInteractor {

    void sendNewQuery(String searchString);
    
    Relay<Photos> getPhotoStream();

    void cancelCurrentSearch();
}
