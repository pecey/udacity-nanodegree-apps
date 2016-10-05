package com.example.cpalash.popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
            String name = movieDetails.get(0);
            String poster = movieDetails.get(1);
            String synopsis = movieDetails.get(2);
            String date = movieDetails.get(3);
            String rating = movieDetails.get(4);

            TextView nameTextView  = (TextView) rootView.findViewById(R.id.movie_detail_movie_name);
            nameTextView.setText(name);

            TextView dateTextView  = (TextView) rootView.findViewById(R.id.movie_detail_movie_release);
            dateTextView.setText(date);

            TextView ratingTextView  = (TextView) rootView.findViewById(R.id.movie_detail_movie_rating);
            ratingTextView.setText(rating);

            TextView synopsisTextView  = (TextView) rootView.findViewById(R.id.movie_detail_movie_synopsis);
            synopsisTextView.setText(synopsis);

            ImageView posterImageView = (ImageView) rootView.findViewById(R.id.movie_detail_movie_poster);
            Picasso.with(getContext()).load(poster).fit().centerCrop().into(posterImageView);

            Log.v("Movie data", movieDetails.toString());
            return rootView;
        }
    }
}
