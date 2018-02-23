package com.example.danieljackson.flickr_findr;

import android.app.Application;
import android.support.test.runner.AndroidJUnitRunner;

import com.example.danieljackson.flickr_findr.dagger.AppComponentHolder;
import com.example.danieljackson.flickr_findr.dagger.DaggerTestComponent;
import com.example.danieljackson.flickr_findr.dagger.SystemModule;
import com.example.danieljackson.flickr_findr.dagger.TestComponent;
import com.example.danieljackson.flickr_findr.dagger.TestDataModule;
import com.example.danieljackson.flickr_findr.dagger.TestPresenterModule;

public class DaggerTestRunner extends AndroidJUnitRunner {

    @Override
    public void callApplicationOnCreate(Application app) {
        TestComponent testComponent = DaggerTestComponent.builder()
                .dataModule(new TestDataModule())
                .presenterModule(new TestPresenterModule())
                .systemModule(new SystemModule())
                .build();
        AppComponentHolder.generate(testComponent);
        super.callApplicationOnCreate(app);
    }
}
