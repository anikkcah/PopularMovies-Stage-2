package com.example.popularmovies2.model;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    private String mVideoId;

    @SerializedName("key")
    private String mKey;

    @SerializedName("name")
    private String mName;

    @SerializedName("site")
    private String mSite;

    @SerializedName("size")
    private int mSize;

    @SerializedName("type")
    private String mType;

    public Video(){

    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String mVideoId) {
        this.mVideoId = mVideoId;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String mSite) {
        this.mSite = mSite;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int mSize) {
        this.mSize = mSize;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }
}
