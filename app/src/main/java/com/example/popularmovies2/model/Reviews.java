package com.example.popularmovies2.model;

import com.google.gson.annotations.SerializedName;

public class Reviews {

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("id")
    private String mId;

    @SerializedName("url")
    private String mUrl;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}