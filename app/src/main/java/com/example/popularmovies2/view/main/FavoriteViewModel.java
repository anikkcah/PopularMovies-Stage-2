package com.example.popularmovies2.view.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies2.database.MovieEntry;
import com.example.popularmovies2.database.MovieRepository;

public class FavoriteViewModel extends ViewModel {

    private final MovieRepository mRepository;
    private LiveData<MovieEntry> mMovieEntry;

    public FavoriteViewModel(MovieRepository repository, int movieId){
        mRepository = repository;
        mMovieEntry = mRepository.getFavoriteMovieByMovieId(movieId);
    }

    public LiveData<MovieEntry> getMovieEntry() { return mMovieEntry; }
}
