package com.example.danieljackson.flickr_findr.dagger;

import com.example.danieljackson.flickr_findr.data.FlickrApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
    private static final String API_URL_BASE = "https://api.flickr.com/services/rest/";

    private static final String FORMAT = "format";
    private static final String JSON = "json";

    @Provides
    public FlickrApi providesFlickrApi() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            HttpUrl url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(API_KEY, API_ID_VALUE)
                    .addQueryParameter(FORMAT, JSON)
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
}
