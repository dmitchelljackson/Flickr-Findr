package com.example.danieljackson.flickr_findr.dagger;

import com.example.danieljackson.flickr_findr.data.interactor.search.SearchInteractor;
import com.example.danieljackson.flickr_findr.data.interactor.search.SearchInteractorImpl;
import com.example.danieljackson.flickr_findr.data.network.FlickrApi;
import com.example.danieljackson.flickr_findr.system.SystemLogger;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module
public class DataModule {

    private static final String API_KEY = "api_key";
    private static final String API_ID_VALUE = "1508443e49213ff84d566777dc211f2a";
    private static final String API_URL_BASE = "https://api.flickr.com/services/";
    private static final String NO_JSON_CALLBACK = "nojsoncallback";

    private static final String FORMAT = "format";
    private static final String JSON = "json";

    @Provides
    @Singleton
    public FlickrApi providesFlickrApi() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            HttpUrl url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(API_KEY, API_ID_VALUE)
                    .addQueryParameter(FORMAT, JSON)
                    .addQueryParameter(NO_JSON_CALLBACK, Boolean.valueOf(true).toString())
                    .build();
            return chain.proceed(chain.request().newBuilder().url(url).build());
        }).addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(FlickrApi.class);
    }

    @Provides
    @Singleton
    public SearchInteractor providesSearchInteractor(FlickrApi flickrApi, SystemLogger systemLogger) {
        return new SearchInteractorImpl(providesFlickrApi(), systemLogger, Schedulers.io());
    }
}
