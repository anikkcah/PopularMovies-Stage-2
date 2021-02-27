package com.example.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Cast implements Parcelable {

    @SerializedName("cast_id")
    private int mCastId;

    @SerializedName("character")
    private String mCharacter;

    @SerializedName("id")
    private int mPersonId;

    @SerializedName("name")
    private String mName;

    @SerializedName("profile_path")
    private String mProfilePath;



    private Cast(Parcel in) {
        mCastId = in.readInt();
        mCharacter = in.readString();
        mPersonId = in.readInt();
        mName = in.readString();
        mProfilePath = in.readString();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>(){
        @Override
        public Cast createFromParcel(Parcel in){
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size){
            return new Cast[size];
        }
    };

    public int getCastId() {
        return mCastId;
    }

    public void setCastId(int mCastId) {
        this.mCastId = mCastId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String mCharacter) {
        this.mCharacter = mCharacter;
    }

    public int getPersonId() {
        return mPersonId;
    }

    public void setPersonId(int mPersonId) {
        this.mPersonId = mPersonId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String mProfilePath) {
        this.mProfilePath = mProfilePath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mCastId);
        parcel.writeString(mCharacter);
        parcel.writeInt(mPersonId);
        parcel.writeString(mName);
        parcel.writeString(mProfilePath);
    }
}
