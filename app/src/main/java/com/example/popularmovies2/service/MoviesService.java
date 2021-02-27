package com.example.popularmovies2.service;

import com.example.popularmovies2.model.MovieDetails;
import com.example.popularmovies2.model.MovieResponse;
import com.example.popularmovies2.model.ReviewResponse;
import com.example.popularmovies2.model.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("movie/{sort_criteria}")
    Call<MovieResponse> getMovies(@Path("sort_criteria") String sortCriteria,
                                  @Query("api_key") String apiKey,
                                  @Query("language") String language,
                                  @Query("page") int page);

    @GET("movie/{id}")
    Call<MovieDetails> getDetails(@Path("id") int id,
                                  @Query("api_key") String apiKey,
                                  @Query("language") String language,
                                  @Query("append_to_response") String credits);


    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getReviews(@Path("id") int id ,
                                    @Query("api_key") String apiKey,
                                    @Query("language") String language,
                                    @Query("page") int page);


    @GET("movie/{id}/videos")
    Call<VideoResponse> getVideos(@Path("id") int id,
                                  @Query("api_key") String apiKey,
                                  @Query("language") String language );

}
