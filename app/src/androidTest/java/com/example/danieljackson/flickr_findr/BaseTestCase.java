package com.example.danieljackson.flickr_findr;

import com.example.danieljackson.flickr_findr.dagger.AppComponentHolder;
import com.example.danieljackson.flickr_findr.dagger.TestComponent;

import junit.framework.TestCase;

public class BaseTestCase extends TestCase {

    public TestComponent getTestComponent() {
        return (TestComponent) AppComponentHolder.getAppComponentHolder().getAppComponent();
    }
}
