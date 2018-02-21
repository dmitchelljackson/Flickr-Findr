package com.example.danieljackson.flickr_findr.dagger;

public class AppComponentHolder {

    private AppComponent appComponent;

    private static AppComponentHolder appComponentHolder;

    public static synchronized AppComponentHolder generate(AppComponent maybeAppComponent) {
        if(appComponentHolder == null) {
            appComponentHolder = new AppComponentHolder(maybeAppComponent);
        }

        return appComponentHolder;
    }

    private AppComponentHolder(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
