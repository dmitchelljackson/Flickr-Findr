package com.example.danieljackson.flickr_findr.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.danieljackson.flickr_findr.FlickrFindrApp;
import com.example.danieljackson.flickr_findr.R;
import com.example.danieljackson.flickr_findr.data.network.model.Photo;
import com.example.danieljackson.flickr_findr.data.network.model.Photos;
import com.example.danieljackson.flickr_findr.system.SystemLogger;
import com.example.danieljackson.flickr_findr.ui.BaseActivity;
import com.example.danieljackson.flickr_findr.ui.BaseFragment;
import com.example.danieljackson.flickr_findr.ui.search.presenter.SearchPresenter;
import com.metova.slim.annotation.Layout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@Layout(R.layout.fragment_search)
public class SearchFragment extends BaseFragment implements SearchPresenter.Callback {

    private static final String TAG = SearchFragment.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @Inject
    SearchPresenter searchPresenter;

    @Inject
    SystemLogger systemLogger;

    GridLayoutManager gridLayoutManager;

    PhotosAdapter photosAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlickrFindrApp.getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);

        initRecyclerView();
        initSearchView(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(String.valueOf(R.id.search_view), searchView.getQuery().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchPresenter.stop();
    }

    private void initSearchView(Bundle bundle) {

        if (bundle != null) {
            String previousQuery = bundle.getString(String.valueOf(R.id.search_view));
            if (previousQuery == null || previousQuery.isEmpty()) {
                setDefaultState();
            } else {
                searchView.setQuery(previousQuery, false);
            }
        }

        searchView.setQueryHint(getActivity().getString(R.string.search_images));
        searchView.setIconified(false);
        searchView.setOnCloseListener(() -> true);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPresenter.onSearchCompleted(query);
                closeKeyboard(searchView);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchPresenter.onSearchUpdated(newText);
                return false;
            }
        });
    }

    private void initRecyclerView() {
        photosAdapter = new PhotosAdapter();

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        int numberOfElementsPerRow = (int) (recyclerView.getWidth() / Photo.THUMBNAIL_SIZE_PX) - 1;
                        int dpWidthPerItem = recyclerView.getMeasuredWidth() / numberOfElementsPerRow;

                        gridLayoutManager = new GridLayoutManager(getActivity(), numberOfElementsPerRow);
                        photosAdapter.setLayoutParams(new ViewGroup.LayoutParams(dpWidthPerItem, dpWidthPerItem));
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setAdapter(photosAdapter);
                        searchPresenter.start(SearchFragment.this);
                    }
                });
    }

    public void setLoading() {
        systemLogger.d(TAG, "Showing Loading State");
        showList(new Photos());
    }

    public void showList(Photos photos) {
        systemLogger.d(TAG, "Showing new photos");
        photosAdapter.setPhotoList(photos);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void showEmptyResults() {
        systemLogger.d(TAG, "Showing Empty Results");
        recyclerView.setVisibility(View.INVISIBLE);
        Snackbar.make(getActivity().findViewById(R.id.frame), R.string.no_results, TSnackbar.LENGTH_SHORT).show();
    }

    public void showLoadError() {
        systemLogger.d(TAG, "Showing Error State");
        recyclerView.setVisibility(View.INVISIBLE);
        Snackbar.make(getActivity().findViewById(R.id.frame), R.string.error_loading_search, TSnackbar.LENGTH_LONG).show();
    }

    @Override
    public void setDefaultState() {
        systemLogger.d(TAG, "Showing Default State");
        recyclerView.setVisibility(View.INVISIBLE);
    }

    private void closeKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
