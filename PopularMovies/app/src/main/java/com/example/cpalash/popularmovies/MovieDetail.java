package com.example.cpalash.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class MovieDetail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_movie_name, new MovieDetailFragment()).commit();
//        }
    }

}
