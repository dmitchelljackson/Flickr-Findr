package com.example.danieljackson.flickr_findr.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Photos {

    private boolean loadError;

    private int page;

    private int perpage;

    private int total;

    private String searchString = "";

    @SerializedName("photo")
    private List<Photo> photos;

    public Photos() {
        this(-1, 0, 0, "", new ArrayList<>());
    }

    public Photos(boolean loadError) {
        this();
        this.loadError = loadError;
    }

    public Photos(int page, int perpage, int total, String searchString, List<Photo> photos) {
        this.page = page;
        this.perpage = perpage;
        this.total = total;
        this.searchString = searchString;
        this.photos = photos;
    }

    public int getPage() {
        return page;
    }

    public int getPerpage() {
        return perpage;
    }

    public int getTotal() {
        return total;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public boolean isLoadError() {
        return loadError;
    }

    public void setLoadError(boolean loadError) {
        this.loadError = loadError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photos photos1 = (Photos) o;

        if (loadError != photos1.loadError) return false;
        if (page != photos1.page) return false;
        if (perpage != photos1.perpage) return false;
        if (total != photos1.total) return false;
        if (searchString != null ? !searchString.equals(photos1.searchString) : photos1.searchString != null)
            return false;
        return photos != null ? photos.equals(photos1.photos) : photos1.photos == null;
    }

    @Override
    public int hashCode() {
        int result = (loadError ? 1 : 0);
        result = 31 * result + page;
        result = 31 * result + perpage;
        result = 31 * result + total;
        result = 31 * result + (searchString != null ? searchString.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        return result;
    }
}
