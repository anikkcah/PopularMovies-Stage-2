package com.example.popularmovies2.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.popularmovies2.R;

import static com.example.popularmovies2.utils.Constants.DEFAULT_VALUE;

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey){

        // add movie preferences ,  defined in the XML file
        addPreferencesFromResource(R.xml.pref_movie);

        //get shared preferences
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();

        //get the preference screen
        PreferenceScreen preferenceScreen = getPreferenceScreen();

        //get the number of preferences
        int count = preferenceScreen.getPreferenceCount();

        for(int i=0 ; i<count ; i++){
            Preference p = preferenceScreen.getPreference(i);

            String value = sharedPreferences.getString(p.getKey(), DEFAULT_VALUE);
            setPreferenceSummary(p, value);
        }
    }

    /**
     * Called when a shared preference is changed.
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key){
        //figure out which preference was changed
        Preference preference = findPreference(key);
        if(null != preference){
            //updates the summary for the preference
            String value = sharedPreferences.getString(preference.getKey(), DEFAULT_VALUE);
            setPreferenceSummary(preference, value);
        }
    }


    /**
     * Updates the summary for the preference
     */
    private void setPreferenceSummary(Preference preference, String value){
        if(preference instanceof ListPreference){
            //for list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if(prefIndex >= 0){
                //set the summary to that label
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Register the OnSharedPreferenceChange listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        // unregister the OnSharedPreferenceChange listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }





}
