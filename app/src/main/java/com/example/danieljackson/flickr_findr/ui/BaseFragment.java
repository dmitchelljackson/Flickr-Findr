package com.example.danieljackson.flickr_findr.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metova.slim.Slim;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = Slim.createLayout(getActivity(), this, container);
        ButterKnife.bind(view);
        return view;
    }
}
