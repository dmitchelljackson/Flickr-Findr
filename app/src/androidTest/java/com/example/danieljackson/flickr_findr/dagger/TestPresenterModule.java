package com.example.danieljackson.flickr_findr.dagger;


import com.example.danieljackson.flickr_findr.data.interactor.search.SearchInteractor;
import com.example.danieljackson.flickr_findr.system.SystemLogger;
import com.example.danieljackson.flickr_findr.ui.search.presenter.SearchPresenter;

import org.mockito.Mockito;

public class TestPresenterModule extends PresenterModule {

    //Work around to get singleton for testing
    private static SearchPresenter searchPresenter = Mockito.mock(SearchPresenter.class);

    @Override
    public SearchPresenter searchPresenter(SearchInteractor searchInteractor, SystemLogger systemLogger) {
        return searchPresenter;
    }
}
