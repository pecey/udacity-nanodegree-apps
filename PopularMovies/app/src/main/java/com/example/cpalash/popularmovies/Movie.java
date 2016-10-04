package com.example.cpalash.popularmovies;

public class Movie {
    private String name;
    private String poster;

    public String getSynopsis() {
        return synopsis;
    }

    public float getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    private String synopsis;
    private float rating;
    private String releaseDate;

    public Movie(String name, String poster, String synopsis, float rating, String releaseDate) {
        this.name = name;
        this.poster = poster;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", poster='" + poster + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", rating=" + rating +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }

    public String getPoster() {
        return poster;
    }
}
