package com.example.popularmovies2.view.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies2.database.MovieRepository;

public class FavoriteViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final MovieRepository mRepository;
    private final int mMovieId;

    public FavoriteViewModelFactory(MovieRepository repository, int movieId){
        mRepository = repository;
        mMovieId = movieId;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new FavoriteViewModel(mRepository, mMovieId);
    }


}
