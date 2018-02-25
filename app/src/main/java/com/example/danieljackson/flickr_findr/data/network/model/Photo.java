package com.example.danieljackson.flickr_findr.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Photo implements Serializable {

    //as per https://www.flickr.com/services/api/misc.urls.html
    public static final float THUMBNAIL_SIZE_PX = 240f;

    private String title;

    @SerializedName("url_t")
    private String thumbNailUrl;

    @SerializedName("url_m")
    private String mediumUrl;

    @SerializedName("url_o")
    private String fullSizedUrl;

    public Photo(String title, String thumbNailUrl, String mediumUrl, String fullSizedUrl) {
        this.title = title;
        this.thumbNailUrl = thumbNailUrl;
        this.mediumUrl = mediumUrl;
        this.fullSizedUrl = fullSizedUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbNailUrl() {
        return thumbNailUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public String getFullSizedUrl() {
        return fullSizedUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (title != null ? !title.equals(photo.title) : photo.title != null) return false;
        if (thumbNailUrl != null ? !thumbNailUrl.equals(photo.thumbNailUrl) : photo.thumbNailUrl != null)
            return false;
        if (mediumUrl != null ? !mediumUrl.equals(photo.mediumUrl) : photo.mediumUrl != null)
            return false;
        return fullSizedUrl != null ? fullSizedUrl.equals(photo.fullSizedUrl) : photo.fullSizedUrl == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (thumbNailUrl != null ? thumbNailUrl.hashCode() : 0);
        result = 31 * result + (mediumUrl != null ? mediumUrl.hashCode() : 0);
        result = 31 * result + (fullSizedUrl != null ? fullSizedUrl.hashCode() : 0);
        return result;
    }

}