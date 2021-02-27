package com.example.popularmovies2.database;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.popularmovies2.model.MovieDetails;
import com.example.popularmovies2.model.ReviewResponse;
import com.example.popularmovies2.model.VideoResponse;
import com.example.popularmovies2.service.MoviesService;
import com.example.popularmovies2.utils.AppExecutors;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.popularmovies2.utils.Constants.API_KEY;
import static com.example.popularmovies2.utils.Constants.CREDITS;
import static com.example.popularmovies2.utils.Constants.LANGUAGE;
import static com.example.popularmovies2.utils.Constants.PAGE;

/**
 * MovieRepository is responsible for handling data operations.
 * Acts as a mediator between {@link MoviesService} and {@link MovieDao}
 */



public class MovieRepository {

    // tag for logging
    private static final String TAG = MovieRepository.class.getSimpleName();

    //For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieRepository sInstance;
    private final MovieDao mMovieDao;
    private final MoviesService mMovieApi;
    private final AppExecutors mExecutors;

    private MovieRepository(MovieDao movieDao,
                            MoviesService movieApi,
                            AppExecutors executors){
        mMovieDao = movieDao;
        mMovieApi = movieApi;
        mExecutors = executors;
    }

    public synchronized static MovieRepository getInstance(
            MovieDao movieDao, MoviesService movieApi, AppExecutors executors){
        Log.d(TAG, "Getting the Repository");
        if(sInstance == null){
            synchronized(LOCK){
                Log.d(TAG, "Making new repository");
                sInstance = new MovieRepository(movieDao, movieApi, executors);
            }
        }
        return sInstance;
    }

    /**
     * Make a network request by calling enqueue and provide a LiveData object
     * of MovieDetails for ViewModel
     */
    public LiveData<MovieDetails> getMovieDetails(int movieId){
        final MutableLiveData<MovieDetails> movieDetailsMutableLiveData = new MutableLiveData<>();

        /**
         * Make a HTTP request to the remote web server.
         * Send Request:
         * https://api.themoviedb.org/3/movie/{movie_id}?api_key={API_KEY}&language=en-US
         * &append_to_response=credits
         */
        mMovieApi.getDetails(movieId, API_KEY, LANGUAGE, CREDITS)
                //Calls are executed with asynchronously with enqueue
                // and notify callback of its response
                .enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                        if(response.isSuccessful()){
                            MovieDetails movieDetails = response.body();
                            movieDetailsMutableLiveData.setValue(movieDetails);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {

                        movieDetailsMutableLiveData.setValue(null);
                        Log.e(TAG,"Failed getting MovieDetails: "+ t.getMessage());
                    }
                });
        return movieDetailsMutableLiveData;
    }

    /**
     * Make a network request by calling enqueue and provide a LiveData object of ReviewResponse
     * for ViewModel
     */
    public LiveData<ReviewResponse> getReviewResponse(int movieId){
        final MutableLiveData<ReviewResponse> reviewResponseMutableLiveData = new MutableLiveData<>();


        // Make a HTTP request to the remote web server.
        // Send request:
        // https://api.themoviedb.org/3/movie/{id}/reviews?api_key={API_KEY}&language=en-US&page=1
        mMovieApi.getReviews(movieId, API_KEY, LANGUAGE, PAGE)
                .enqueue(new Callback<ReviewResponse>() {
                    /**
                     * Invoked for a received HTTP response.
                     * @param call
                     * @param response
                     */
                    @Override
                    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {

                        if(response.isSuccessful()){
                            ReviewResponse reviewResponse = response.body();
                            reviewResponseMutableLiveData.setValue(reviewResponse);
                        }
                    }


                    /**
                     * Invoked when a network exception occurred talking to the server or when an
                     * unexpected exception occurred creating the request or processing the response
                      *
                     */
                    @Override
                    public void onFailure(Call<ReviewResponse> call, Throwable t) {
                            reviewResponseMutableLiveData.setValue(null);
                            Log.e(TAG, "Failed getting ReviewResponse: " + t.getMessage());
                    }
                });

        return reviewResponseMutableLiveData;
    }

    /**
     * Make a network request by calling enqueue and provide a LiveData object of VideoResponse
     * for ViewModel
     */
    public LiveData<VideoResponse> getVideoResponse(int movieId){

        final MutableLiveData<VideoResponse> videoResponseMutableLiveData = new MutableLiveData<>();

        //Make a HTTP request to the remote web server.
        // send request:
        // https://api.themoviedb.org/3/movie/{id}/videos?api_key={API_KEY}&language=en-US
        mMovieApi.getVideos(movieId, API_KEY, LANGUAGE)
                .enqueue(new Callback<VideoResponse>() {
                    @Override
                    public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                        if(response.isSuccessful()){
                            VideoResponse videoResponse = response.body();
                            if(videoResponse != null){
                                videoResponseMutableLiveData.setValue(videoResponse);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<VideoResponse> call, Throwable t) {

                        videoResponseMutableLiveData.setValue(null);
                        Log.e(TAG, "Failed getting VideoResponse: "+t.getMessage());

                    }
                });

        return videoResponseMutableLiveData;

                }


    /**
     * Return a LiveData of the list of MovieEntries directly from the database.
     */
    public LiveData<List<MovieEntry>> getFavoriteMovies(){
        return mMovieDao.loadAllMovies();
    }


    /**
     * Return a LiveData of MovieEntry directly from the database
     */
    public LiveData<MovieEntry> getFavoriteMovieByMovieId(int movieId){
        return mMovieDao.loadMovieByMovieId(movieId);
    }






}
