package com.example.danieljackson.flickr_findr.dagger;

import com.example.danieljackson.flickr_findr.ui.search.SuggestionDelegate;

import static org.mockito.Mockito.mock;

public class TestSystemModule extends SystemModule{

    @Override
    public SuggestionDelegate providesSuggestionDelegate() {
        return mock(SuggestionDelegate.class);
    }
}
