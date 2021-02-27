package com.example.popularmovies2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {

    @SerializedName("id")
    private int mId;

    @SerializedName("results")
    private List<Video> mVideoResults = null;


    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public List<Video> getVideoResults() {
        return mVideoResults;
    }

    public void setVideoResults(List<Video> videoResults) {
        this.mVideoResults = videoResults;
    }
}
