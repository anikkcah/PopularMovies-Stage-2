package com.example.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails implements Parcelable {

    @SerializedName("budget")
    private long mBudget;

    @SerializedName("genres")
    private List<Genre> mGenres = null;

    @SerializedName("runtime")
    private int mRuntime;

    @SerializedName("revenue")
    private long mRevenue;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("vote_count")
    private int mVoteCount;

    @SerializedName("credits")
    private Credits mCredits;


    private MovieDetails(Parcel in){
        mBudget = in.readLong();
        mRuntime = in.readInt();
        mRevenue = in.readLong();
        mStatus = in.readString();
        mVoteCount = in.readInt();
        mCredits = (Credits) in.readValue(Credits.class.getClassLoader());
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>(){
        @Override
        public MovieDetails createFromParcel(Parcel in){
            return new MovieDetails(in);
        }

        @Override
        public MovieDetails[] newArray(int size){
            return new MovieDetails[size];
        }
    };


    public long getBudget() {
        return mBudget;
    }

    public void setBudget(long mBudget) {
        this.mBudget = mBudget;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> mGenres) {
        this.mGenres = mGenres;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public void setRuntime(int mRuntime) {
        this.mRuntime = mRuntime;
    }

    public long getRevenue() {
        return mRevenue;
    }

    public void setRevenue(long mRevenue) {
        this.mRevenue = mRevenue;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(int mVoteCount) {
        this.mVoteCount = mVoteCount;
    }

    public Credits getCredits() {
        return mCredits;
    }

    public void setCredits(Credits mCredits) {
        this.mCredits = mCredits;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(mBudget);
        parcel.writeInt(mRuntime);
        parcel.writeLong(mRevenue);
        parcel.writeString(mStatus);
        parcel.writeInt(mVoteCount);
        parcel.writeValue(mCredits);

    }
}
