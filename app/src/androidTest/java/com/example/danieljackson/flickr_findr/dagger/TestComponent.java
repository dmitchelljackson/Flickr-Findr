package com.example.danieljackson.flickr_findr.dagger;

import com.example.danieljackson.flickr_findr.SearchActivityTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class, PresenterModule.class, SystemModule.class})
public interface TestComponent extends AppComponent {

    void inject(SearchActivityTest searchActivityTest);
}
