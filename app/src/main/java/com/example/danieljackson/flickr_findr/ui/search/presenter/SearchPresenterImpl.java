package com.example.danieljackson.flickr_findr.ui.search.presenter;

import com.example.danieljackson.flickr_findr.data.interactor.search.SearchInteractor;
import com.example.danieljackson.flickr_findr.data.network.model.Photos;
import com.example.danieljackson.flickr_findr.system.SystemLogger;

import javax.inject.Inject;

import io.reactivex.Scheduler;

public class SearchPresenterImpl implements SearchPresenter {

    private static final String TAG = SearchPresenterImpl.class.getSimpleName();

    private SearchInteractor searchInteractor;

    private SystemLogger systemLogger;

    private Scheduler mainScheduler;

    private Callback callback;

    @Inject
    public SearchPresenterImpl(SearchInteractor searchInteractor, SystemLogger systemLogger, Scheduler mainScheduler) {
        this.searchInteractor = searchInteractor;
        this.systemLogger = systemLogger;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void start(Callback callback) {
        systemLogger.d(TAG, "Starting Presenter");
        this.callback = callback;

        searchInteractor.getPhotoStream().observeOn(mainScheduler).subscribe(photos -> {
           if(photos.isLoadError()) {
               this.callback.showLoadError();
           } else if(photos.getPhotos().isEmpty()) {
               this.callback.showEmptyResults();
           } else {
               this.callback.showList(photos);
           }
        });
    }

    @Override
    public void stop() {
        systemLogger.d(TAG, "Stopping Presenter");
        this.callback = new EmptyCallback();
    }

    @Override
    public void onSearchUpdated(String searchText) {
        if (!searchText.isEmpty()) {
            callback.setLoading();
            searchInteractor.sendNewQuery(searchText);
        } else {
            searchInteractor.cancelCurrentSearch();
            callback.setDefaultState();
        }
    }

    @Override
    public void onSearchCompleted(String searchText) {
        onSearchUpdated(searchText);
    }

    private class EmptyCallback implements SearchPresenter.Callback {
        @Override
        public void setLoading() {
            systemLogger.d(TAG, "setLoading Called On Empty Callback");
        }

        @Override
        public void setDefaultState() {
            systemLogger.d(TAG, "setDefaultState Called On Empty Callback");

        }

        @Override
        public void showEmptyResults() {
            systemLogger.d(TAG, "showEmptyResults Called On Empty Callback");
        }

        @Override
        public void showLoadError() {
            systemLogger.d(TAG, "showLoadError Called On Empty Callback");

        }

        @Override
        public void showList(Photos photos) {
            systemLogger.d(TAG, "showList Called On Empty Callback");

        }
    }

}
