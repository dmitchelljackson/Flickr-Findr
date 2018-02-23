package com.example.danieljackson.flickr_findr.data.network.model;

public class PhotoResponse {

    private Photos photos;

    public PhotoResponse(Photos photos) {
        this.photos = photos;
    }

    public Photos getPhotos() {
        return photos;
    }
}
