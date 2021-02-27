package com.example.popularmovies2.view.info;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies2.database.MovieRepository;

/**
 * Factory method that allows us to create a ViewModel with a
 * contructor that takes a {@link MovieRepository} and the movieID
 */
public class InfoViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final MovieRepository mRepository;
    private final int mMovieId;

    public InfoViewModelFactory(MovieRepository repository, int movieId) {
        this.mRepository = repository;
        this.mMovieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        //noinspection unchecked
        return (T) new InfoViewModel(mRepository, mMovieId);
    }





}
