package com.example.danieljackson.flickr_findr.dagger;

import com.example.danieljackson.flickr_findr.system.AndroidSystemLogger;
import com.example.danieljackson.flickr_findr.system.SystemLogger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class SystemModule {

    @Provides
    public SystemLogger providesSystemLogger() {
        return new AndroidSystemLogger();
    }
}
