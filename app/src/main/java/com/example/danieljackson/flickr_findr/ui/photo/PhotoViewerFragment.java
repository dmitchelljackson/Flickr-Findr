package com.example.danieljackson.flickr_findr.ui.photo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.example.danieljackson.flickr_findr.FlickrFindrApp;
import com.example.danieljackson.flickr_findr.R;
import com.example.danieljackson.flickr_findr.data.network.model.Photo;
import com.example.danieljackson.flickr_findr.system.SystemLogger;
import com.example.danieljackson.flickr_findr.ui.BaseFragment;
import com.metova.slim.annotation.Extra;
import com.metova.slim.annotation.Layout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

@Layout(R.layout.fragment_photo_viewer)
public class PhotoViewerFragment extends BaseFragment {

    public static final String TAG = PhotoViewerFragment.class.getSimpleName();

    private static final String PHOTO_EXTRA = "photo";

    @Extra(PHOTO_EXTRA)
    private Photo photo;

    @Inject
    SystemLogger systemLogger;

    @BindView(R.id.photo_view)
    PhotoView photoView;

    @BindView(R.id.photo_viewer_progress)
    ProgressBar progressBar;

    public static PhotoViewerFragment newInstance(Photo photo) {
        PhotoViewerFragment fragment = new PhotoViewerFragment();
        Bundle args = new Bundle();
        args.putSerializable(PHOTO_EXTRA, photo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        FlickrFindrApp.getAppComponent().inject(this);
        loadImage(photo);
    }

    private void loadImage(Photo photo) {
        systemLogger.d(TAG, "Loading Photo: " + photo.getTitle());

        progressBar.setVisibility(View.VISIBLE);

        RequestBuilder<Drawable> thumbnailRequest = Glide
                .with(photoView.getContext())
                .load(photo.getMediumUrl())
                .apply(new RequestOptions().centerInside());

        Glide.with(photoView.getContext())
                .load(photo.getFullSizedUrl())
                .apply(new RequestOptions().centerInside())
                .thumbnail(thumbnailRequest)
                .into(photoView);
    }
}
