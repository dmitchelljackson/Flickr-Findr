package com.example.danieljackson.flickr_findr.ui.search.presenter;

import com.example.danieljackson.flickr_findr.data.interactor.search.SearchInteractor;
import com.example.danieljackson.flickr_findr.data.network.model.Photos;
import com.example.danieljackson.flickr_findr.system.SystemLogger;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

public class SearchPresenterImpl implements SearchPresenter {

    private static final String TAG = SearchPresenterImpl.class.getSimpleName();

    private static final int TOTAL_PAGES_UNDEFINED = Integer.MAX_VALUE;

    private SearchInteractor searchInteractor;

    private SystemLogger systemLogger;

    private Scheduler mainScheduler;

    private Callback callback;

    private int currentPage;

    private int totalPagesInQuery;

    private String currentQuery;

    private boolean isLoading;

    private Disposable disposable;

    @Inject
    public SearchPresenterImpl(SearchInteractor searchInteractor, SystemLogger systemLogger, Scheduler mainScheduler) {
        this.searchInteractor = searchInteractor;
        this.systemLogger = systemLogger;
        this.mainScheduler = mainScheduler;
        callback = new EmptyCallback();
    }

    @Override
    public void start(Callback callback) {
        systemLogger.d(TAG, "Starting Presenter");
        this.callback = callback;

        disposable = searchInteractor.getPhotoStream().scan((lastPhotos, photos) -> {
            if (lastPhotos.getPage() + 1 == photos.getPage()) {
                lastPhotos.getPhotos().addAll(photos.getPhotos());
                photos.setPhotos(lastPhotos.getPhotos());
            }
            return photos;
        }).observeOn(mainScheduler).subscribe(photos -> {
            currentPage = photos.getPage();
            totalPagesInQuery = photos.getTotal();
            isLoading = false;
            if (photos.isLoadError()) {
                this.callback.showLoadError();
            } else if (photos.getPhotos().isEmpty()) {
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

        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    public void onSearchUpdated(String searchText) {
        if (!searchText.isEmpty()) {
            currentPage = 1;
            isLoading = true;
            totalPagesInQuery = TOTAL_PAGES_UNDEFINED;
            currentQuery = searchText;

            callback.setLoading();
            searchInteractor.sendNewQuery(searchText, currentPage);
        } else {
            searchInteractor.cancelCurrentSearch();
            callback.setDefaultState();
        }
    }

    @Override
    public void onSearchCompleted(String searchText) {
        onSearchUpdated(searchText);
    }

    @Override
    public void onLoadMorePhotos() {
        if (!isLoading) {
            searchInteractor.sendNewQuery(currentQuery, currentPage + 1);
        }
    }

    @Override
    public boolean isLoadingPhotos() {
        return isLoading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return currentPage >= totalPagesInQuery;
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
