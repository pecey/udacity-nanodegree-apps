package com.example.cpalash.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v("Home Fragment", "Called");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ImageAdapter imageAdapter = new ImageAdapter(getActivity().getApplicationContext());
        View mainView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView images = (GridView) mainView.findViewById(R.id.image_grid_view);

        Log.v("Home Fragment" , imageAdapter.toString());
        Log.v("Home Fragment", String.valueOf(imageAdapter.getCount()));
        images.setAdapter(imageAdapter);

        return mainView;
    }
}
