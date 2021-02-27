package com.example.popularmovies2.view.info;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies2.R;
import com.example.popularmovies2.databinding.FragmentInfoBinding;
import com.example.popularmovies2.model.Movie;
import com.example.popularmovies2.model.MovieDetails;
import com.example.popularmovies2.utils.FormatUtils;
import com.example.popularmovies2.utils.InjectorUtils;


import static com.example.popularmovies2.utils.Constants.EXTRA_MOVIE;

/**
 * The InformationFragment displays information for the selected movie.
 */
public class InformationFragment extends Fragment {


    /**
     *  This field is used for data binding
     */
    private FragmentInfoBinding mInfoBinding;

    /**
     *  Defines a new interface OnInfoSelectedListener that triggers a callback
     *  in the host activity.
     *  The callback is a method named onInformationSelected(MovieDetails movieDetails) that contains
     *  information about the MovieDetails
     */
    OnInfoSelectedListener mCallback;

    public interface OnInfoSelectedListener {
        void onInformationSelected(MovieDetails movieDetails);
    }


    /** Tag for logging */
    public static final String TAG = InformationFragment.class.getSimpleName();

    /** Member variable for the Movie object */
    private Movie mMovie;

    /** ViewModel for InformationFragment */
    private InfoViewModel mInfoViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public InformationFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get movie data from the MainActivity
        mMovie = getMovieData();

        // Observe the data and update the UI
        setupViewModel(this.getActivity(), mMovie.getId());

        // display the overview, vote average, release date of the movie.
        loadDetails();
    }

    /**
     * Every time the user data is updated, the onChanged callback will be invoked and update the UI
     */
    private void setupViewModel(Context context, int movieId) {
        InfoViewModelFactory factory = InjectorUtils.provideInfoViewModelFactory(context, movieId);
        mInfoViewModel = new ViewModelProvider(this, factory).get(InfoViewModel.class);

        // Retrieve live data object using the getMovieDetails() method from the ViewModel
        mInfoViewModel.getMovieDetails().observe(getViewLifecycleOwner(), new Observer<MovieDetails>() {
            @Override
            public void onChanged(@Nullable MovieDetails movieDetails) {
                if (movieDetails != null) {
                    // Trigger the callback onInformationSelected
                    mCallback.onInformationSelected(movieDetails);

                    // Display vote count, budget, revenue, status of the movie
                    loadMovieDetailInfo(movieDetails);

                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Instantiate mInfoBinding using DataBindingUtil
        mInfoBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_info, container, false);
        View rootView = mInfoBinding.getRoot();


        return rootView;
    }

    /**
     * Gets movie data from the MainActivity.
     */
    private Movie getMovieData() {
        // Store the Intent
        Intent intent = getActivity().getIntent();
        // Check if the Intent is not null, and has the extra we passed from MainActivity
        if (intent != null) {
            if (intent.hasExtra(EXTRA_MOVIE)) {
                // Receive the Movie object which contains information, such as ID, original title,
                // poster path, overview, vote average, release date, backdrop path.
                Bundle b = intent.getBundleExtra(EXTRA_MOVIE);
                mMovie = b.getParcelable(EXTRA_MOVIE);
            }
        }
        return mMovie;
    }


    /**
     * Display vote count, budget, revenue, status of the movie
     */
    private void loadMovieDetailInfo(MovieDetails movieDetails) {
        // Get the  vote count, budget, revenue, status
        int voteCount = movieDetails.getVoteCount();
        long budget = movieDetails.getBudget();
        long revenue = movieDetails.getRevenue();
        String status = movieDetails.getStatus();

        // Display vote count, budget, revenue, status of the movie. Use FormatUtils class
        // to format the integer number
        mInfoBinding.tvVoteCount.setText(FormatUtils.formatNumber(voteCount));
        mInfoBinding.tvBudget.setText(FormatUtils.formatCurrency(budget));
        mInfoBinding.tvRevenue.setText(FormatUtils.formatCurrency(revenue));
        mInfoBinding.tvStatus.setText(status);
    }

    /**
     * Get the detail information from the Movie object, then set them to the TextView to display the
     * overview, vote average, release date of the movie.
     */
    private void loadDetails() {
        // Display the overview of the movie
        mInfoBinding.tvOverview.setText(mMovie.getOverview());
        // Display the vote average of the movie
        mInfoBinding.tvVoteAverage.setText(String.valueOf(mMovie.getVoteAverage()));
        // Display the original title of the movie
        mInfoBinding.tvOriginalTitle.setText(mMovie.getOriginalTitle());
        // Display the release date of the movie
        mInfoBinding.tvReleaseDate.setText(FormatUtils.formatDate(mMovie.getReleaseDate()));
    }

    /**
     * Override onAttach to make sure that the container activity has implemented the callback
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnInfoSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnInfoSelectedListener");
        }

    }
}






