package com.example.danieljackson.flickr_findr.dagger;

import com.example.danieljackson.flickr_findr.ui.MainActivity;
import com.example.danieljackson.flickr_findr.ui.photo.PhotoViewerFragment;
import com.example.danieljackson.flickr_findr.ui.search.SearchFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class, PresenterModule.class, SystemModule.class})
public interface AppComponent {
    void inject(SearchFragment searchFragment);
    void inject(PhotoViewerFragment photoViewerFragment);
    void inject(MainActivity mainActivity);
}
