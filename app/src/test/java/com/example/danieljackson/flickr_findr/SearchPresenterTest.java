package com.example.danieljackson.flickr_findr;

import com.example.danieljackson.flickr_findr.data.interactor.search.SearchInteractor;
import com.example.danieljackson.flickr_findr.data.network.model.Photos;
import com.example.danieljackson.flickr_findr.ui.search.presenter.SearchPresenter;
import com.example.danieljackson.flickr_findr.ui.search.presenter.SearchPresenterImpl;
import com.jakewharton.rxrelay2.PublishRelay;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.schedulers.Schedulers;

import static com.example.danieljackson.flickr_findr.SearchInteractorTest.TEST_SEARCH_STRING;
import static com.example.danieljackson.flickr_findr.SearchInteractorTest.getTestPhotos;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchPresenterTest {

    SearchPresenter searchPresenter;

    SearchInteractor searchInteractor;

    PublishRelay<Photos> testRelay = PublishRelay.create();

    SearchPresenter.Callback callback;

    @Before
    public void setUp(){
        searchInteractor = mock(SearchInteractor.class);
        when(searchInteractor.getPhotoStream()).thenReturn(testRelay);
        searchPresenter = new SearchPresenterImpl(searchInteractor, new JavaSystemLogger(), Schedulers.trampoline());
        callback = mock(SearchPresenter.Callback.class);
        searchPresenter.start(callback);
    }

    @Test
    public void testOnSearchUpdatedHasText() {
        searchPresenter.onSearchUpdated(TEST_SEARCH_STRING);
        verify(searchInteractor).sendNewQuery(eq(TEST_SEARCH_STRING), eq(SearchInteractorTest.TEST_PAGE_NUMBER_ONE));
        verify(callback).setLoading();
    }

    @Test
    public void testOnSearchUpdatedHasNoText() {
        searchPresenter.onSearchUpdated("");
        verify(searchInteractor, times(0)).sendNewQuery(eq(TEST_SEARCH_STRING), eq(SearchInteractorTest.TEST_PAGE_NUMBER_ONE));
        verify(searchInteractor).cancelCurrentSearch();
        verify(callback).setDefaultState();
    }

    @Test
    public void testOnSearchCompletedHasText() {
        searchPresenter.onSearchCompleted(TEST_SEARCH_STRING);
        verify(searchInteractor).sendNewQuery(eq(TEST_SEARCH_STRING), eq(SearchInteractorTest.TEST_PAGE_NUMBER_ONE));
        verify(callback).setLoading();
    }

    @Test
    public void testOnSearchCompletedHasNoText() {
        searchPresenter.onSearchCompleted("");
        verify(searchInteractor, times(0)).sendNewQuery(eq(TEST_SEARCH_STRING), eq(SearchInteractorTest.TEST_PAGE_NUMBER_ONE));
        verify(searchInteractor).cancelCurrentSearch();
        verify(callback).setDefaultState();
    }

    @Test
    public void testLoadError() {
        testRelay.accept(new Photos(true));
        verify(callback).showLoadError();
    }

    @Test
    public void testEmptyResponse() {
        testRelay.accept(new Photos());
        verify(callback).showEmptyResults();
    }

    @Test
    public void testPhotosReceived() {
        testRelay.accept(getTestPhotos());
        verify(callback).showList(eq(getTestPhotos()));
    }

    @Test
    public void testOnLoadMorePhotosNotLoading() {
        searchPresenter.onSearchCompleted(TEST_SEARCH_STRING);
        testRelay.accept(getTestPhotos());
        searchPresenter.onLoadMorePhotos();
        verify(searchInteractor).sendNewQuery(eq(TEST_SEARCH_STRING), eq(2));
    }

    @Test
    public void testOnLoadMorePhotosLoading() {
        searchPresenter.onSearchCompleted(TEST_SEARCH_STRING);
        searchPresenter.onLoadMorePhotos();
        verify(searchInteractor, times(0)).sendNewQuery(eq(TEST_SEARCH_STRING), eq(2));
    }

    @Test
    public void testIsLoadingPhotos() {
        searchPresenter.onSearchCompleted(TEST_SEARCH_STRING);
        testRelay.accept(getTestPhotos());
        testRelay.accept(getTestPhotos());
        assertTrue(!searchPresenter.isLoadingPhotos());
        searchPresenter.onLoadMorePhotos();
        assertTrue(searchPresenter.isLoadingPhotos());
    }

    @Test
    public void testHasLoadedAllItems() {
        Photos photos = getTestPhotos();
        photos.setTotal(25);
        testRelay.accept(photos);
        assertTrue(searchPresenter.hasLoadedAllItems());
    }

    @Test
    public void testHasNotLoadedAllItems() {
        Photos photos = getTestPhotos();
        photos.setTotal(26);
        testRelay.accept(photos);
        assertTrue(!searchPresenter.hasLoadedAllItems());
    }

    @Test
    public void testNoCallbacksAfterStop() {
        searchPresenter.stop();
        testRelay.accept(getTestPhotos());
        verify(callback, times(0)).showList(any());
        testRelay.accept(new Photos());
        verify(callback, times(0)).showEmptyResults();
        testRelay.accept(new Photos(true));
        verify(callback, times(0)).showLoadError();
    }

}
