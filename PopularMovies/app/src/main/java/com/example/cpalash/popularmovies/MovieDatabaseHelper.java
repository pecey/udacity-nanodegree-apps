package com.example.cpalash.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDatabaseHelper extends AsyncTask<Void, Void, Map<String,Movie>>{

    private String buildUrl() {
        final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";
        final String API_KEY = "d44d016ef29d8eb9be3bdc7fd32193a2";
        Uri baseUrl = Uri.parse(BASE_URL);
        Uri url = baseUrl.buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("language", "en-US")
                .build();

        return url.toString();
    }


    private String fetchData(String urlString) {
        HttpURLConnection urlConnection;
        BufferedReader reader;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String jsonData = reader.readLine();

            return jsonData;

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }


    @Override
    protected Map<String, Movie> doInBackground(Void... params) {
        Map<String, Movie>  movies = new HashMap<>();
        List<String> urls = new ArrayList<>();

        String url = buildUrl();
        String jsonData = fetchData(url);
        try {
            JSONObject json = new JSONObject(jsonData);
            JSONArray results = json.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String movieName = result.getString("original_title");
                String posterUrl = result.getString("poster_path");
                posterUrl = "http://image.tmdb.org/t/p/w185/"+posterUrl;
                Movie newMovie = new Movie(movieName, posterUrl);
                movies.put(posterUrl, newMovie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

}
