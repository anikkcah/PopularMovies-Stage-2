package com.example.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static com.example.popularmovies2.utils.Constants.BYTE;

public class Credits implements Parcelable {

    @SerializedName("cast")
    private List<Cast> mCast;

    @SerializedName("crew")
    private List<Crew> mCrew;

    private Credits(Parcel in) {
        if(in.readByte() == BYTE){
            mCast = new ArrayList<>();
            in.readList(mCast,Cast.class.getClassLoader());
        } else {
            mCast = null;
        }
        if(in.readByte() == BYTE){
            mCrew = new ArrayList<>();
            in.readList(mCrew, Crew.class.getClassLoader());
        } else{
            mCrew = null;
        }
    }

    public static final Creator<Credits> CREATOR = new Creator<Credits>(){
        @Override
        public Credits createFromParcel(Parcel in){
            return new Credits(in);
        }

        @Override
        public Credits[] newArray(int size){
            return new Credits[size];
        }
    };

    public List<Cast> getCast() {
        return mCast;
    }

    public void setCast(List<Cast> mCast) {
        this.mCast = mCast;
    }

    public List<Crew> getCrew() {
        return mCrew;
    }

    public void setCrew(List<Crew> mCrew) {
        this.mCrew = mCrew;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if(mCast == null){
            parcel.writeByte((byte) (0x00));
        } else {
            parcel.writeByte((byte) (0x01));
            parcel.writeList(mCast);
        }

        if(mCrew == null){
            parcel.writeByte((byte) (0x00));
        } else {
            parcel.writeByte((byte) (0x01));
            parcel.writeList(mCrew);
        }
    }
}