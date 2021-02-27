package com.example.popularmovies2.model;

import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("id")
    private int mGenreId;

    @SerializedName("name")
    private String mGenreName;

    public int getGenreId() {
        return mGenreId;
    }

    public void setGenreId(int mGenreId) {
        this.mGenreId = mGenreId;
    }

    public String getGenreName() {
        return mGenreName;
    }

    public void setGenreName(String mGenreName) {
        this.mGenreName = mGenreName;
    }
}
