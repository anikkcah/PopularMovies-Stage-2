package com.example.popularmovies2.utils;

import android.content.Context;

import com.example.popularmovies2.database.MovieDatabase;
import com.example.popularmovies2.database.MovieRepository;
import com.example.popularmovies2.service.MoviesService;
import com.example.popularmovies2.service.RetrofitInstance;
import com.example.popularmovies2.view.info.InfoViewModelFactory;
import com.example.popularmovies2.view.main.FavoriteViewModelFactory;
import com.example.popularmovies2.view.main.MainViewModelFactory;
import com.example.popularmovies2.view.review.ReviewViewModelFactory;
import com.example.popularmovies2.view.trailer.TrailerViewModelFactory;

public class InjectorUtils {
    public static MovieRepository provideRepository(Context context) {
        MovieDatabase database = MovieDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getExecutorInstance();
        // The Retrofit class generates an implementation of the TheMovieApi interface
        MoviesService theMovieApi = RetrofitInstance.getClient().create(MoviesService.class);
        return MovieRepository.getInstance(database.movieDao(), theMovieApi, executors);
    }

    public static MainViewModelFactory provideMainActivityViewModelFactory(Context context, String sortCriteria) {
        MovieRepository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository, sortCriteria);
    }

    public static InfoViewModelFactory provideInfoViewModelFactory(Context context, int movieId) {
        MovieRepository repository = provideRepository(context.getApplicationContext());
        return new InfoViewModelFactory(repository, movieId);
    }

    public static ReviewViewModelFactory provideReviewViewModelFactory(Context context, int movieId) {
        MovieRepository repository = provideRepository(context.getApplicationContext());
        return new ReviewViewModelFactory(repository, movieId);
    }

    public static TrailerViewModelFactory provideTrailerViewModelFactory(Context context, int movieId) {
        MovieRepository repository = provideRepository(context.getApplicationContext());
        return new TrailerViewModelFactory(repository, movieId);
    }

    public static FavoriteViewModelFactory provideFavViewModelFactory(Context context, int movieId) {
        MovieRepository repository = provideRepository(context.getApplicationContext());
        return new FavoriteViewModelFactory(repository, movieId);
    }
}
