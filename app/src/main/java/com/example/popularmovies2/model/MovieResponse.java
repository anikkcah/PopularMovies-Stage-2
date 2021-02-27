package com.example.popularmovies2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {

    @SerializedName("page")
    private int mPage;

    @SerializedName("total_results")
    private int mTotalResults;

    @SerializedName("results")
    private List<Movie> mMovieResults = null;



    public MovieResponse() {

    }

    public List<Movie> getMovieResults() {
        return mMovieResults;
    }
}

