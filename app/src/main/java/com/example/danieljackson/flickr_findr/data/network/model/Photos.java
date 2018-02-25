package com.example.danieljackson.flickr_findr.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Photos {

    private boolean loadError;

    private int page;

    @SerializedName("perpage")
    private int perPage;

    private int total;

    @SerializedName("photo")
    private List<Photo> photos;

    public Photos() {
        this(-1, 0, 0, new ArrayList<>());
    }

    public Photos(boolean loadError) {
        this();
        this.loadError = loadError;
    }

    public Photos(int page, int perPage, int total, List<Photo> photos) {
        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.photos = photos;
    }

    public int getPage() {
        return page;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotal() {
        return total;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public boolean isLoadError() {
        return loadError;
    }

    public void setLoadError(boolean loadError) {
        this.loadError = loadError;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photos photos1 = (Photos) o;

        if (loadError != photos1.loadError) return false;
        if (page != photos1.page) return false;
        if (perPage != photos1.perPage) return false;
        if (total != photos1.total) return false;

        return photos != null ? photos.equals(photos1.photos) : photos1.photos == null;
    }

    @Override
    public int hashCode() {
        int result = (loadError ? 1 : 0);
        result = 31 * result + page;
        result = 31 * result + perPage;
        result = 31 * result + total;
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        return result;
    }
}
