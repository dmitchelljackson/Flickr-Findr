package com.example.danieljackson.flickr_findr;

import com.example.danieljackson.flickr_findr.dagger.AppComponentHolder;
import com.example.danieljackson.flickr_findr.dagger.DaggerTestComponent;
import com.example.danieljackson.flickr_findr.dagger.PresenterModule;
import com.example.danieljackson.flickr_findr.dagger.TestComponent;
import com.example.danieljackson.flickr_findr.dagger.TestDataModule;

import junit.framework.TestCase;

public class BaseTestCase extends TestCase {

    public TestComponent getTestComponent() {
        TestComponent testComponent = DaggerTestComponent.builder()
                .dataModule(new TestDataModule())
                .presenterModule(new PresenterModule())
                .build();
        return (TestComponent) AppComponentHolder.generate(testComponent).getAppComponent();
    }
}
