package com.example.danieljackson.flickr_findr;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.danieljackson.flickr_findr.data.FlickrApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends BaseTestCase {

    @Inject
    FlickrApi flickrApi;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        getTestComponent().inject(this);
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.danieljackson.flickr_findr", appContext.getPackageName());

        Log.d("Test", flickrApi.toString());
    }
}
