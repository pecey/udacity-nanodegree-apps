package com.example.cpalash.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    private DisplayBy category;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.rating_menu, menu);
        inflater.inflate(R.menu.popular_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!MovieDatabaseHelper.isOnline(getContext())) {
            Toast.makeText(getContext(), "Internet required but not found", Toast.LENGTH_SHORT).show();
            return false;
        }

        ViewGroup main = (ViewGroup) getActivity().findViewById(R.id.image_grid_view);
        GridView images = (GridView) main.findViewById(R.id.image_grid_view);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity().getApplicationContext());
        if (item.getItemId() == R.id.rating_menu) {
            imageAdapter.setCategory(DisplayBy.RATED);
        }
        if(item.getItemId() == R.id.popular_menu){
            imageAdapter.setCategory(DisplayBy.POPULAR);
        }
        images.setAdapter(imageAdapter);
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v("Creating View", String.valueOf(category));
        View mainView = inflater.inflate(R.layout.fragment_main, container, false);

        if (MovieDatabaseHelper.isOnline(getContext())) {
            ImageAdapter imageAdapter = new ImageAdapter(getActivity().getApplicationContext());
            GridView images = (GridView) mainView.findViewById(R.id.image_grid_view);
            images.setAdapter(imageAdapter);
        } else {
            Toast.makeText(getContext(), "Internet required but not found", Toast.LENGTH_SHORT).show();
        }
        return mainView;
    }
}
