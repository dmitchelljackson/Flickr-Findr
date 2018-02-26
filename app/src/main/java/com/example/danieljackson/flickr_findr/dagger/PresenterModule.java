package com.example.danieljackson.flickr_findr.dagger;

import com.example.danieljackson.flickr_findr.data.interactor.search.SearchInteractor;
import com.example.danieljackson.flickr_findr.system.SystemLogger;
import com.example.danieljackson.flickr_findr.ui.search.presenter.SearchPresenter;
import com.example.danieljackson.flickr_findr.ui.search.presenter.SearchPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
@Singleton
public class PresenterModule {

    @Provides
    @Singleton
    public SearchPresenter searchPresenter(SearchInteractor searchInteractor, SystemLogger systemLogger) {
        return new SearchPresenterImpl(searchInteractor, systemLogger, AndroidSchedulers.mainThread());
    }
}
