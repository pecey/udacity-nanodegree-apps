package com.example.cpalash.popularmovies;

public class Movie {
    private String name;
    private String poster;

    public Movie(String name, String poster) {
        this.name = name;
        this.poster = poster;
    }

    public String getName() {
        return name;
    }

    public String getPoster() {
        return poster;
    }
}
