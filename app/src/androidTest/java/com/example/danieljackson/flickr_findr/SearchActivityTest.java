package com.example.danieljackson.flickr_findr;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.danieljackson.flickr_findr.data.network.model.Photo;
import com.example.danieljackson.flickr_findr.data.network.model.Photos;
import com.example.danieljackson.flickr_findr.ui.search.SearchActivity;
import com.example.danieljackson.flickr_findr.ui.search.presenter.SearchPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchActivityTest extends BaseTestCase {

    private static final int TEST_PAGE_NUMBER = 0;
    private static final int TEST_NUMBER_PERPAGE = 25;
    private static final int TEST_TOTAL_NUMBER = 100;

    public static final String TEST_SEARCH_STRING = "search";
    private static final String TEST_SEARCH_STRING_ALTERNATE = "alternate";

    private static final String TEST_PHOTO_TITLE = "title";
    private static final String TEST_PHOTO_THUMBNAIL_URL = "thumb";
    private static final String TEST_MEDUIM_URL = "medium";
    private static final String TEST_PHOTO_URL = "full";

    private ArgumentCaptor<SearchPresenter.Callback> argumentCaptor = ArgumentCaptor.forClass(SearchPresenter.Callback.class);

    @Inject
    SearchPresenter searchPresenter;

    @Rule
    public ActivityTestRule<SearchActivity> activityRule =
            new ActivityTestRule(SearchActivity.class, false,false);

    @Before
    public void setUp() {
        getTestComponent().inject(this);
        activityRule.launchActivity(new Intent());
        verify(searchPresenter, atLeastOnce()).start(argumentCaptor.capture());
    }

    @Test
    public void testSetLoading() throws InterruptedException {
        activityRule.getActivity().runOnUiThread(() -> argumentCaptor.getValue().setLoading());
        Thread.sleep(100);
        onView(withText(TEST_PHOTO_TITLE + 0)).check(doesNotExist());
        onView(withId(R.id.progress)).check(matches(isDisplayed()));
    }

    @Test
    public void testSetDefaultState() throws InterruptedException {
        activityRule.getActivity().runOnUiThread(() -> argumentCaptor.getValue().setDefaultState());
        Thread.sleep(100);
        onView(withText(TEST_PHOTO_TITLE + 0)).check(doesNotExist());
    }

    @Test
    public void testShowEmptyResults() throws InterruptedException {
        activityRule.getActivity().runOnUiThread(() -> argumentCaptor.getValue().showEmptyResults());
        Thread.sleep(100);
        onView(withText(TEST_PHOTO_TITLE + 0)).check(doesNotExist());
        onView(withText(R.string.no_results)).check(matches(isDisplayed()));
    }

    @Test
    public void testShowLoadErrorResults() throws InterruptedException {
        activityRule.getActivity().runOnUiThread(() -> argumentCaptor.getValue().showLoadError());
        Thread.sleep(100);
        onView(withText(TEST_PHOTO_TITLE + 0)).check(doesNotExist());
        onView(withText(R.string.error_loading_search)).check(matches(isDisplayed()));
    }


    @Test
    public void testShowList() throws InterruptedException {
        activityRule.getActivity().runOnUiThread(() -> argumentCaptor.getValue().showList(getTestPhotos()));
        Thread.sleep(100);
        onView(withText(TEST_PHOTO_TITLE + 0)).check(matches(isDisplayed()));
        onView(withText(TEST_PHOTO_TITLE + 1)).check(matches(isDisplayed()));
        onView(withText(TEST_PHOTO_TITLE + 2)).check(matches(isDisplayed()));
        onView(withId(R.id.progress)).check(matches(isDisplayed()));
    }

    public static Photos getTestPhotos() {
        return new Photos(TEST_PAGE_NUMBER, TEST_NUMBER_PERPAGE, TEST_TOTAL_NUMBER, TEST_SEARCH_STRING, getTestPhotoList());
    }

    public static Photos getTestPhotosTwo() {
        return new Photos(TEST_PAGE_NUMBER, TEST_NUMBER_PERPAGE, TEST_TOTAL_NUMBER, TEST_SEARCH_STRING_ALTERNATE, getTestPhotoList());
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
