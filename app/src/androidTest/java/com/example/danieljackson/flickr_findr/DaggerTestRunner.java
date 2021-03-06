package com.example.danieljackson.flickr_findr;

import android.app.Application;
import android.support.test.runner.AndroidJUnitRunner;

import com.example.danieljackson.flickr_findr.dagger.AppComponentHolder;
import com.example.danieljackson.flickr_findr.dagger.DaggerTestComponent;
import com.example.danieljackson.flickr_findr.dagger.DataModule;
import com.example.danieljackson.flickr_findr.dagger.TestComponent;
import com.example.danieljackson.flickr_findr.dagger.TestPresenterModule;
import com.example.danieljackson.flickr_findr.dagger.TestSystemModule;

public class DaggerTestRunner extends AndroidJUnitRunner {

    @Override
    public void callApplicationOnCreate(Application app) {
        TestComponent testComponent = DaggerTestComponent.builder()
                .dataModule(new DataModule())
                .presenterModule(new TestPresenterModule())
                .systemModule(new TestSystemModule())
                .build();
        AppComponentHolder.generate(testComponent);
        super.callApplicationOnCreate(app);
    }
}
