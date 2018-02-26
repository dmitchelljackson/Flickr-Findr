package com.example.danieljackson.flickr_findr.data.network;

import com.example.danieljackson.flickr_findr.data.network.model.PhotoResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {

    @GET("rest?method=flickr.photos.search&extras=url_m, url_o, url_t&sort=relevance&media=photos")
    Flowable<PhotoResponse> getTextSearchResults(@Query("text") String searchText,
                                                 @Query("page") int pageNumber,
                                                 @Query("per_page") int itemsPerPage);
}
