package com.example.popularmovies2.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.popularmovies2.model.Movie;
import com.example.popularmovies2.model.MovieResponse;
import com.example.popularmovies2.service.MoviesService;
import com.example.popularmovies2.service.RetrofitInstance;
import com.example.popularmovies2.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.popularmovies2.utils.Constants.NEXT_PAGE_KEY_TWO;
import static com.example.popularmovies2.utils.Constants.PREVIOUS_PAGE_KEY_ONE;
import static com.example.popularmovies2.utils.Constants.RESPONSE_CODE_API_STATUS;

public class  MovieDataSource extends PageKeyedDataSource<Integer, Movie> {

    private static final String TAG = MovieDataSource.class.getSimpleName();


    private MoviesService mMovieAPI;


    private String mSortCriteria;


    public MovieDataSource(String sortCriteria){
        mMovieAPI = RetrofitInstance.getClient().create(MoviesService.class);
        mSortCriteria = sortCriteria;
    }

    /** This method is called first to initialize a PageList with data.
     *
     * @param params
     * @param callback
     */
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie> callback) {

        mMovieAPI.getMovies(mSortCriteria, Constants.API_KEY, Constants.LANGUAGE, Constants.PAGE_ONE)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            callback.onResult(response.body().getMovieResults(),
                                    PREVIOUS_PAGE_KEY_ONE, NEXT_PAGE_KEY_TWO);
                        } else if (response.code() == RESPONSE_CODE_API_STATUS) {
                            Log.e(TAG, "Invalid Api key. Response code: " + response.code());
                        } else {
                            Log.e(TAG, "Response Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                        Log.e(TAG, "Failed initializing a PagedList: " + t.getMessage());
                    }
                });
    }

    /**
     * Prepend page with the key specified by LoadParams.key
     * @param params
     * @param callback
     */
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }

    /**
     * Append page with the key specified by LoadParams.key
     * @param params
     * @param callback
     */
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {

        final int currentPage = params.key;

        mMovieAPI.getMovies(mSortCriteria, Constants.API_KEY, Constants.LANGUAGE, currentPage)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if(response.isSuccessful()){
                            int nextKey = currentPage + 1;
                            callback.onResult(response.body().getMovieResults(), nextKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Log.e(TAG, "Failed appending page: "+t.getMessage());
                    }
                });
    }
}
