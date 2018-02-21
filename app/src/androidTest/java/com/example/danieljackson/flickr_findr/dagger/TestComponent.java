package com.example.danieljackson.flickr_findr.dagger;

import com.example.danieljackson.flickr_findr.ExampleInstrumentedTest;

import dagger.Component;

@Component(modules = {DataModule.class, PresenterModule.class})
public interface TestComponent extends AppComponent {

    void inject(ExampleInstrumentedTest exampleInstrumentedTest);
}
