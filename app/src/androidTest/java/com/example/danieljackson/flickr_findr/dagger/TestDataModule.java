package com.example.danieljackson.flickr_findr.dagger;

import com.example.danieljackson.flickr_findr.data.network.FlickrApi;

import org.mockito.Mockito;

public class TestDataModule extends DataModule {

    @Override
    public FlickrApi providesFlickrApi() {
        return Mockito.mock(FlickrApi.class);
    }
}
