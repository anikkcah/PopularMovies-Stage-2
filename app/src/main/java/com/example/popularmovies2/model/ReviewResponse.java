package com.example.popularmovies2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName("id")
    private int mId;

    @SerializedName("page")
    private int mPage;

    @SerializedName("results")
    private List<Reviews> mReviewResults = null;


    public ReviewResponse(){

    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public List<Reviews> getReviewResults() {
        return mReviewResults;
    }

    public void setReviewResults(List<Reviews> mReviewResults) {
        this.mReviewResults = mReviewResults;
    }
}
