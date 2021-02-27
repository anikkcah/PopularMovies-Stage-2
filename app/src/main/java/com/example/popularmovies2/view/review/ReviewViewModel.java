package com.example.popularmovies2.view.review;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies2.database.MovieRepository;
import com.example.popularmovies2.model.ReviewResponse;

/**
 * {@link ViewModel} for ReviewFragment
 */
public class ReviewViewModel extends ViewModel {

    private final MovieRepository mRepository;
    private final LiveData<ReviewResponse> mReviewResponse;

    public ReviewViewModel (MovieRepository repository, int movieId) {
        mRepository = repository;
        mReviewResponse = mRepository.getReviewResponse(movieId);
    }

    public LiveData<ReviewResponse> getReviewResponse() {
        return mReviewResponse;
    }
}
