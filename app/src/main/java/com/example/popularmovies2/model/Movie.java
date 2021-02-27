package com.example.popularmovies2.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Movie implements Parcelable {

   /** The id of the movie **/
   @SerializedName("id")
   private int mId;

   /** Original title of the Movie **/
   @SerializedName("original_title")
   private String mOriginalTitle;

   /** Title of the movie **/
   @SerializedName("title")
   private String mTitle;

   /** Movie poster image thumbnail **/
   @SerializedName("poster_path")
   private String mPosterPath;

   /** Overview (or plot synopsis) of the movie **/
   @SerializedName("overview")
   private String mOverview;

   /** Vote Average of the movie **/
   @SerializedName("vote_average")
   private double mVoteAverage;

   /** Releasedate of the movie **/
   @SerializedName("release_date")
   private String mReleaseDate;

   /** Backdrop of the movie **/
   @SerializedName("backdrop_path")
   private String mBackdropPath;


    public Movie(int movieId, String originalTitle, String title,String posterPath, String overview,
                 double voteAverage, String releaseDate, String backdropPath) {
       mId = movieId;
       mOriginalTitle = originalTitle;
       mTitle = title;
       mPosterPath = posterPath;
       mOverview = overview;
       mVoteAverage = voteAverage;
       mReleaseDate = releaseDate;
       mBackdropPath = backdropPath;
    }


    public int getId() {
        return mId;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mOriginalTitle);
        dest.writeString(mTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeDouble(mVoteAverage);
        dest.writeString(mReleaseDate);
        dest.writeString(mBackdropPath);
    }


    private Movie(Parcel in){
        this.mId =  in.readInt();
        this.mOriginalTitle = in.readString();
        this.mTitle = in.readString();
        this.mPosterPath = in.readString();
        this.mOverview = in.readString();
        this.mVoteAverage = in.readDouble();
        this.mReleaseDate = in.readString();
        this.mBackdropPath = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}

