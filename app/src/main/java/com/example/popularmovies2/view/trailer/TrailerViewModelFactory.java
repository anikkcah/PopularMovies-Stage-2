package com.example.popularmovies2.view.trailer;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies2.database.MovieRepository;

public class TrailerViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MovieRepository mRepository;
    private final int mMovieId;

    public TrailerViewModelFactory (MovieRepository repository, int movieId) {
        this.mRepository = repository;
        this.mMovieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new TrailerViewModel(mRepository, mMovieId);
    }
}
