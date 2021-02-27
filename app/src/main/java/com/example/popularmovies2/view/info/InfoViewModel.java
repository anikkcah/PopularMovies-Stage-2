package com.example.popularmovies2.view.info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies2.database.MovieRepository;
import com.example.popularmovies2.model.MovieDetails;

public class InfoViewModel extends ViewModel {

    private final MovieRepository mRepository;
    private final LiveData<MovieDetails> mMovieDetails;

    public InfoViewModel(MovieRepository repository, int movieId){
        mRepository = repository;
        mMovieDetails = mRepository.getMovieDetails(movieId);
    }

    public LiveData<MovieDetails> getMovieDetails() { return mMovieDetails; }

}
