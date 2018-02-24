package com.example.danieljackson.flickr_findr.dagger;

import com.example.danieljackson.flickr_findr.ui.search.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class, PresenterModule.class, SystemModule.class})
public interface AppComponent {
    void inject(SearchFragment searchFragment);
}
