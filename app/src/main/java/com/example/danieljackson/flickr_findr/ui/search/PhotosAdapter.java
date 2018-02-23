package com.example.danieljackson.flickr_findr.ui.search;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.example.danieljackson.flickr_findr.R;
import com.example.danieljackson.flickr_findr.data.network.model.Photo;
import com.example.danieljackson.flickr_findr.data.network.model.Photos;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private Photos photos;

    private ViewGroup.LayoutParams layoutParams;

    public PhotosAdapter() {
        this.photos = new Photos();
        layoutParams = new ViewGroup.LayoutParams(0,0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.view_loading_item) {
            View view = layoutInflater.inflate(R.layout.view_loading_item, parent, false);
            view.setLayoutParams(layoutParams);
            view.requestLayout();
            return new LoadingViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.view_photo, parent, false);
            view.setLayoutParams(layoutParams);
            view.requestLayout();
            return new PhotoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof LoadingViewHolder) {
            //no op
        } else {
            PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
            Photo photo = getPhotoAt(position);
            photoViewHolder.titleTextView.setText(photo.getTitle());

            RequestBuilder<Drawable> thumbnailRequest = Glide
                    .with(photoViewHolder.photoImageView.getContext())
                    .load(photo.getThumbNailUrl())
                    .apply(new RequestOptions().centerCrop());

            Glide.with(photoViewHolder.photoImageView.getContext())
                    .load(photo.getMediumUrl())
                    .apply(new RequestOptions().centerCrop())
                    .thumbnail(thumbnailRequest)
                    .into(photoViewHolder.photoImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (canLoadMorePhotos()) {
            return photos.getPhotos().size() + 1;
        } else {
            return photos.getPhotos().size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position > photos.getPhotos().size() - 1) {
            return R.layout.view_loading_item;
        } else {
            return R.layout.view_photo;
        }
    }

    private Photo getPhotoAt(int position) {
        return photos.getPhotos().get(position);
    }

    public void setPhotoList(Photos photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    private boolean canLoadMorePhotos() {
        return photos.getPhotos().isEmpty() || photos.getPage() * photos.getPerpage() < photos.getTotal();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class LoadingViewHolder extends PhotosAdapter.ViewHolder {

        public LoadingViewHolder(View view) {
            super(view);
        }
    }

    public static class PhotoViewHolder extends PhotosAdapter.ViewHolder {

        @BindView(R.id.photo_title)
        TextView titleTextView;

        @BindView(R.id.background_photo)
        ImageView photoImageView;

        public PhotoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    private enum ViewType {
        LOADING,
        PHOTO
    }
}
