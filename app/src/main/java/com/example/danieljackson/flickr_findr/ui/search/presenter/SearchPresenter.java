package com.example.danieljackson.flickr_findr.ui.search.presenter;

import com.example.danieljackson.flickr_findr.data.network.model.Photos;

public interface SearchPresenter {

    void start(Callback callback);

    void stop();

    void onSearchUpdated(String searchText);

    void onSearchCompleted(String searchText);

    void onLoadMorePhotos();

    boolean isLoadingPhotos();

    boolean hasLoadedAllItems();

    interface Callback {
        void setLoading();

        void setDefaultState();

        void showEmptyResults();

        void showLoadError();

        void showList(Photos photos);
    }
}
