package com.example.danieljackson.flickr_findr;

import android.app.Application;

import com.example.danieljackson.flickr_findr.dagger.AppComponent;
import com.example.danieljackson.flickr_findr.dagger.AppComponentHolder;
import com.example.danieljackson.flickr_findr.dagger.DaggerAppComponent;
import com.example.danieljackson.flickr_findr.dagger.DataModule;
import com.example.danieljackson.flickr_findr.dagger.PresenterModule;
import com.example.danieljackson.flickr_findr.dagger.SystemModule;

public class FlickrFindrApp extends Application {

    public static AppComponentHolder appComponentHolder;

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.builder()
                .presenterModule(new PresenterModule())
                .dataModule(new DataModule())
                .systemModule(new SystemModule())
                .build();

        appComponentHolder = AppComponentHolder.generate(appComponent);
    }

    public static AppComponent getAppComponent() {
        return appComponentHolder.getAppComponent();
    }
}
