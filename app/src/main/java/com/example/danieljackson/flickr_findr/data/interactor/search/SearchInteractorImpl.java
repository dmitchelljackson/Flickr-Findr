package com.example.danieljackson.flickr_findr.data.interactor.search;

import com.example.danieljackson.flickr_findr.data.network.FlickrApi;
import com.example.danieljackson.flickr_findr.data.network.model.PhotoResponse;
import com.example.danieljackson.flickr_findr.data.network.model.Photos;
import com.example.danieljackson.flickr_findr.system.SystemLogger;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;

public class SearchInteractorImpl implements SearchInteractor {

    private static final String TAG = SearchInteractorImpl.class.getSimpleName();

    public static final int ITEMS_PER_PAGE = 25;

    public static final int PAGE_TO_SEARCH = 1;

    private FlickrApi flickrApi;

    private SystemLogger systemLogger;

    private Scheduler scheduler;

    private BehaviorRelay<Photos> photosBehaviorRelay = BehaviorRelay.create();

    private Disposable disposable;

    public SearchInteractorImpl(FlickrApi flickrApi, SystemLogger systemLogger, Scheduler scheduler) {
        this.flickrApi = flickrApi;
        this.systemLogger = systemLogger;
        this.scheduler = scheduler;
    }

    @Override
    public void sendNewQuery(String text, int page) {
        cancelCurrentSearch();

        DisposableSubscriber subscriber = new DisposableSubscriber<Photos>() {
            @Override
            public void onNext(Photos photos) {
                systemLogger.d(TAG, "Search OnNext Called");
                photosBehaviorRelay.accept(photos);
            }

            @Override
            public void onError(Throwable t) {
                systemLogger.e(TAG, "Search Error", t);
                photosBehaviorRelay.accept(new Photos(true));
            }

            @Override
            public void onComplete() {
                systemLogger.d(TAG, "Search OnComplete");
            }
        };

        disposable = subscriber;

        flickrApi.getTextSearchResults(text, page, ITEMS_PER_PAGE)
                .map(PhotoResponse::getPhotos)
                .subscribeOn(scheduler)
                .subscribe(subscriber);
    }

    @Override
    public Relay<Photos> getPhotoStream() {
        return photosBehaviorRelay;
    }

    @Override
    public void cancelCurrentSearch() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

}
