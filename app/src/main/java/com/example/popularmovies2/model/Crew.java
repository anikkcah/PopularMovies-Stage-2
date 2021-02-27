package com.example.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Crew implements Parcelable {

    @SerializedName("job")
    private String mJob;

    @SerializedName("name")
    private String mName;

    private Crew(Parcel in){
        mJob = in.readString();
        mName = in.readString();
    }

    public static final Creator<Crew> CREATOR = new Creator<Crew>() {
        @Override
        public Crew createFromParcel(Parcel in){
            return new Crew(in);
        }

        @Override
        public Crew[] newArray(int size){
            return new Crew[size];
        }
    };

    public String getJob() {
        return mJob;
    }

    public void setJob(String mJob) {
        this.mJob = mJob;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mJob);
        parcel.writeString(mName);
    }
}
