package com.example.danieljackson.flickr_findr.dagger;

import com.example.danieljackson.flickr_findr.system.AndroidSystemLogger;
import com.example.danieljackson.flickr_findr.system.SystemLogger;
import com.example.danieljackson.flickr_findr.ui.search.SuggestionDelegate;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class SystemModule {

    @Provides
    @Singleton
    public SystemLogger providesSystemLogger() {
        return new AndroidSystemLogger();
    }

    @Provides
    @Singleton
    public SuggestionDelegate providesSuggestionDelegate() {
        return new SuggestionDelegate();
    }
}
