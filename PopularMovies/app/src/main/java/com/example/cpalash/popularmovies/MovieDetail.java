package com.example.cpalash.popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MovieDetail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.movie_detail, new MovieDetailFragment()).commit();
        }
    }

    public static class MovieDetailFragment extends android.support.v4.app.Fragment {
        public MovieDetailFragment() {

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.v("MovieDetailFragment", "Called");
            View rootView = inflater.inflate(R.layout.movie_detail, container, false);
            Intent movieDetailsIntent = getActivity().getIntent();
            List<String> movieDetails = movieDetailsIntent.getStringArrayListExtra("details");
            Log.v("MovieDetailFragment", movieDetails.toString());
            TextView details = (TextView) rootView.findViewById(R.id.movie_detail_movie_name);
            details.setText(movieDetails.toString());
            return rootView;
        }
    }
}
