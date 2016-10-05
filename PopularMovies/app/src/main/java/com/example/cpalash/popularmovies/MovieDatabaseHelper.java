package com.example.cpalash.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.util.HashMap;
import java.util.Map;

import static com.example.cpalash.popularmovies.DisplayBy.*;

public class MovieDatabaseHelper extends AsyncTask<Void, Void, Map<String, Movie>> {
    private String API_KEY;
    private DisplayBy category;

    public MovieDatabaseHelper(DisplayBy category, Context context) {
        API_KEY = ConfHelper.getConfigValue(context, "api_key");
        this.category = category;
    }

    private String getBaseUrl() {
        if (category == POPULAR) {
            return "https://api.themoviedb.org/3/movie/popular";
        }
        if (category == RATED) {
            return "https://api.themoviedb.org/3/movie/top_rated";
        }
        return "https://api.themoviedb.org/3/discover/movie";
    }

    private String buildUrl() {
        Uri baseUrl = Uri.parse(getBaseUrl());
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
        Map<String, Movie> movies = new HashMap<>();

        String url = buildUrl();
        String jsonData = fetchData(url);
        try {
            JSONObject json = new JSONObject(jsonData);
            JSONArray results = json.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String movieName = result.getString("original_title");
                String posterUrl = result.getString("poster_path");
                String synopsis = result.getString("overview");
                String releaseDate = result.getString("release_date");
                float rating = Float.parseFloat(result.getString("vote_average"));
                posterUrl = "http://image.tmdb.org/t/p/w185/" + posterUrl;
                Movie newMovie = new Movie(movieName, posterUrl, synopsis, rating, releaseDate);
                movies.put(posterUrl, newMovie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
