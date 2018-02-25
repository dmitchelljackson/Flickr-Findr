package com.example.danieljackson.flickr_findr;

import com.example.danieljackson.flickr_findr.data.interactor.search.SearchInteractor;
import com.example.danieljackson.flickr_findr.data.interactor.search.SearchInteractorImpl;
import com.example.danieljackson.flickr_findr.data.network.FlickrApi;
import com.example.danieljackson.flickr_findr.data.network.model.Photo;
import com.example.danieljackson.flickr_findr.data.network.model.PhotoResponse;
import com.example.danieljackson.flickr_findr.data.network.model.Photos;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static com.example.danieljackson.flickr_findr.data.interactor.search.SearchInteractorImpl.*;
import static com.example.danieljackson.flickr_findr.data.interactor.search.SearchInteractorImpl.PAGE_TO_SEARCH;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class SearchInteractorTest {

    public static final int TEST_PAGE_NUMBER_ONE = 1;
    public static final int TEST_PAGE_NUMBER_TWO = 2;
    private static final int TEST_NUMBER_PERPAGE = 25;
    private static final int TEST_TOTAL_NUMBER = 100;

    public static final String TEST_SEARCH_STRING = "search";
    private static final String TEST_SEARCH_STRING_ALTERNATE = "alternate";

    private static final String TEST_PHOTO_TITLE = "title";
    private static final String TEST_PHOTO_THUMBNAIL_URL = "thumb";
    private static final String TEST_MEDUIM_URL = "medium";
    private static final String TEST_PHOTO_URL = "full";

    SearchInteractor searchInteractor;

    FlickrApi flickrApi;

    TestScheduler testScheduler;

    @Before
    public void setUp(){
        flickrApi = Mockito.mock(FlickrApi.class);
        testScheduler = new TestScheduler();
        searchInteractor = new SearchInteractorImpl(flickrApi, new JavaSystemLogger(), testScheduler);
        when(flickrApi.getTextSearchResults(eq(TEST_SEARCH_STRING), eq(TEST_PAGE_NUMBER_TWO), eq(ITEMS_PER_PAGE))).thenThrow(new RuntimeException());
        when(flickrApi.getTextSearchResults(eq(TEST_SEARCH_STRING_ALTERNATE), eq(TEST_PAGE_NUMBER_TWO), eq(ITEMS_PER_PAGE))).thenThrow(new RuntimeException());
    }

    @Test
    public void testSendNewQuerySuccess() {
        when(flickrApi.getTextSearchResults(eq(TEST_SEARCH_STRING), eq(PAGE_TO_SEARCH), eq(ITEMS_PER_PAGE))).thenReturn(getSuccessfulStream());
        TestObserver<Photos> testSubscriber = searchInteractor.getPhotoStream().test();

        searchInteractor.sendNewQuery(TEST_SEARCH_STRING, TEST_PAGE_NUMBER_ONE);

        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        testSubscriber.assertValueSequence(Arrays.asList(getTestPhotos()));
        testSubscriber.assertNotComplete();
        testSubscriber.assertNoErrors();
    }

    @Test
    public void testSendNewQueryMultipleSequence() {

        when(flickrApi.getTextSearchResults(eq(TEST_SEARCH_STRING), eq(PAGE_TO_SEARCH), eq(ITEMS_PER_PAGE))).thenReturn(getSuccessfulStream());
        when(flickrApi.getTextSearchResults(eq(TEST_SEARCH_STRING_ALTERNATE), eq(PAGE_TO_SEARCH), eq(ITEMS_PER_PAGE))).thenReturn(getSuccessfulStreamTwo());
        TestObserver<Photos> testSubscriber = searchInteractor.getPhotoStream().test();

        searchInteractor.sendNewQuery(TEST_SEARCH_STRING, TEST_PAGE_NUMBER_ONE);
        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        searchInteractor.sendNewQuery(TEST_SEARCH_STRING_ALTERNATE, TEST_PAGE_NUMBER_ONE);
        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        testSubscriber.assertValueSequence(Arrays.asList(getTestPhotos(), getTestPhotosTwo()));
        testSubscriber.assertNotComplete();
        testSubscriber.assertNoErrors();
    }

    @Test
    public void testSendNewQueryCancelsPrevious() {
        when(flickrApi.getTextSearchResults(eq(TEST_SEARCH_STRING), eq(PAGE_TO_SEARCH), eq(ITEMS_PER_PAGE))).thenReturn(Flowable.error(new RuntimeException()));
        when(flickrApi.getTextSearchResults(eq(TEST_SEARCH_STRING_ALTERNATE), eq(PAGE_TO_SEARCH), eq(ITEMS_PER_PAGE))).thenReturn(getSuccessfulStream());
        TestObserver<Photos> testSubscriber = searchInteractor.getPhotoStream().test();

        searchInteractor.sendNewQuery(TEST_SEARCH_STRING, TEST_PAGE_NUMBER_ONE);
        searchInteractor.sendNewQuery(TEST_SEARCH_STRING_ALTERNATE, TEST_PAGE_NUMBER_ONE);

        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        testSubscriber.assertValueSequence(Arrays.asList(getTestPhotos()));
        testSubscriber.assertNotComplete();
        testSubscriber.assertNoErrors();
    }

    @Test
    public void testSendNewQueryNetworkFailure() {
        when(flickrApi.getTextSearchResults(eq(TEST_SEARCH_STRING), eq(PAGE_TO_SEARCH), eq(ITEMS_PER_PAGE))).thenReturn(Flowable.error(new RuntimeException()));
        TestObserver<Photos> testSubscriber = searchInteractor.getPhotoStream().test();

        searchInteractor.sendNewQuery(TEST_SEARCH_STRING, TEST_PAGE_NUMBER_ONE);

        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        testSubscriber.assertValueSequence(Arrays.asList(getErrorPhotos()));
        testSubscriber.assertNotComplete();
        testSubscriber.assertNoErrors();
    }

    @Test
    public void testSendNewQueryCompletePrematurely() {
        when(flickrApi.getTextSearchResults(eq(TEST_SEARCH_STRING),  eq(PAGE_TO_SEARCH), eq(ITEMS_PER_PAGE))).thenReturn(Flowable.empty());
        TestObserver<Photos> testSubscriber = searchInteractor.getPhotoStream().test();

        searchInteractor.sendNewQuery(TEST_SEARCH_STRING, TEST_PAGE_NUMBER_ONE);

        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        testSubscriber.assertNoValues();
        testSubscriber.assertNotComplete();
        testSubscriber.assertNoErrors();
    }

    @Test
    public void testCancelCurrentSearch() {
        when(flickrApi.getTextSearchResults(eq(TEST_SEARCH_STRING), eq(PAGE_TO_SEARCH), eq(ITEMS_PER_PAGE))).thenReturn(getSuccessfulStream());
        TestObserver<Photos> testSubscriber = searchInteractor.getPhotoStream().test();

        searchInteractor.sendNewQuery(TEST_SEARCH_STRING, TEST_PAGE_NUMBER_ONE);
        searchInteractor.cancelCurrentSearch();

        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        testSubscriber.assertNoValues();
        testSubscriber.assertNotComplete();
        testSubscriber.assertNoErrors();
    }


    private static Flowable<PhotoResponse> getSuccessfulStream() {
        return Flowable.just(new PhotoResponse(getTestPhotos()));
    }

    private static Flowable<PhotoResponse> getSuccessfulStreamTwo() {
        return Flowable.just(new PhotoResponse(getTestPhotosTwo()));
    }

    public static Photos getTestPhotos() {
        return new Photos(TEST_PAGE_NUMBER_ONE, TEST_NUMBER_PERPAGE, TEST_TOTAL_NUMBER, getTestPhotoList());
    }

    public static Photos getTestPhotosTwo() {
        return new Photos(TEST_PAGE_NUMBER_ONE, TEST_NUMBER_PERPAGE, TEST_TOTAL_NUMBER, getTestPhotoList());
    }

    private static List<Photo> getTestPhotoList() {
        List<Photo> photos = new ArrayList<>();
        int listCount = 3;

        for(int i = 0; i < listCount; i++) {
            photos.add(new Photo(TEST_PHOTO_TITLE + i, TEST_PHOTO_THUMBNAIL_URL + i, TEST_MEDUIM_URL, TEST_PHOTO_URL + i));
        }

        return photos;
    }

    public static Photos getErrorPhotos() {
        return new Photos(true);
    }
}
