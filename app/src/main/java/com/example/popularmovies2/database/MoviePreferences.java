package com.example.popularmovies2.database;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.popularmovies2.R;

public class MoviePreferences {

    public static String getPreferredSortCriteria(Context context){
        // Get all of values from Shared Preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //String for the key
        String keyForSortBy = context.getString(R.string.pref_sort_by_key);
        //String for default value
        String defaultSortBy = context.getString(R.string.pref_sort_by_default);

        return prefs.getString(keyForSortBy, defaultSortBy);
    }
}
