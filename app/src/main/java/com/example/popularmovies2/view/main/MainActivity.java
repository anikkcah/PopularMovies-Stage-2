package com.example.popularmovies2.view.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.popularmovies2.R;
import com.example.popularmovies2.database.MovieEntry;
import com.example.popularmovies2.database.MoviePreferences;
import com.example.popularmovies2.databinding.ActivityMainBinding;
import com.example.popularmovies2.model.Movie;
import com.example.popularmovies2.utils.GridSpacingItemDecoration;
import com.example.popularmovies2.utils.InjectorUtils;
import com.example.popularmovies2.view.SettingsActivity;
import com.example.popularmovies2.view.detail.DetailActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static com.example.popularmovies2.utils.Constants.EXTRA_MOVIE;
import static com.example.popularmovies2.utils.Constants.GRID_INCLUDE_EDGE;
import static com.example.popularmovies2.utils.Constants.GRID_SPACING;
import static com.example.popularmovies2.utils.Constants.GRID_SPAN_COUNT;
import static com.example.popularmovies2.utils.Constants.LAYOUT_MANAGER_STATE;
import static com.example.popularmovies2.utils.Constants.REQUEST_CODE_DIALOG;

public class MainActivity extends AppCompatActivity implements FavoriteAdapter.FavoriteAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener,
        MoviePagedListAdapter.MoviePagedListAdapterOnClickHandler{


    // log message tagging
    private static final String TAG = MainActivity.class.getSimpleName();

    //MoviePagedListAdapter enables for data to be loaded in chunks
    private MoviePagedListAdapter mMoviePagedListAdapter;

    // exposes a list of favorite movies from a list of MovieEntry to a RecyclerView
    private FavoriteAdapter mFavoriteAdapter;

    //String for the sort criteria(" most popular and highest rated")
    private String mSortCriteria;

    //Member variable for restoring list items positions on device rotation
    private Parcelable mSavedLayoutState;

    //viewmodel for MainActivity
    private MainActivityViewModel mMainViewModel;

    //This field is used for databinding
    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //set the LayoutManager to the Recyclerview and create MoviePagedListAdapter and FavoriteAdapter
        initAdapter();

        //check if savedInstance is null not to recreate a dialog when rotating
        if(savedInstanceState == null){
            //show a dialog when there is no internet connection
            showNetworkDialog(isOnline());
        }
        //get the sort criteria currently set in Preferences
        mSortCriteria = MoviePreferences.getPreferredSortCriteria(this);

        //get the ViewModel from the factory
        setupViewModel(mSortCriteria);

        //update the UI depending on the sort order
        updateUI(mSortCriteria);

        //register MainActivity as an OnPreferenceChangedListener to receive a callback when a
        // SharedPreference has changed. Please note that we must unregister MainActivity as an
        // OnSharedPreferenceChanged listener in OnDestroy to avoid any memory leaks.

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        //set the color scheme of the SwipeRefreshLayout and setup OnRefreshListener
        setSwipeRefreshLayout();

        //set column spacing to make each column have the same spacing
        setColumnSpacing();

        if(savedInstanceState != null){
            //get the scroll position
            mSavedLayoutState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE);
            //Restore the scroll position
            mMainBinding.rvMovie.getLayoutManager().onRestoreInstanceState(mSavedLayoutState);
        }

    }

    /**
     * Set the LayoutManager to the RecyclerView and create MoviePagedListAdapter
     * and FavoriteAdapter
     */
    private void initAdapter(){
        // a GridLayoutManager is responsible for measuring and positioning item views within a
        // RecyclerView  into a grid layout.
        GridLayoutManager layoutManager = new GridLayoutManager(this, GRID_SPAN_COUNT);

        //Set the layout manager to the RecyclerView
        mMainBinding.rvMovie.setLayoutManager(layoutManager);

        //use this setting to improve performance if you know that changes in content
        //do not change the child layout size in the RecyclerView
        mMainBinding.rvMovie.setHasFixedSize(true);

        //Create MoviePagedListAdapter
        mMoviePagedListAdapter = new MoviePagedListAdapter(this);

        //Create FavoriteAdapter that is responsible for linking favorite movies
        // with the Views
        mFavoriteAdapter = new FavoriteAdapter(this, this);

    }

    /**
     * Get the MainActivityViewModel from the factory
     */
     private void setupViewModel(String sortCriteria){
         MainViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(
                 MainActivity.this, sortCriteria);

         mMainViewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);
     }

    /**
     * Update the UI depending on the sort criteria
     */
    private void updateUI(String sortCriteria){
        //Set a new value for the list of MovieEntries
        mMainViewModel.setFavoriteMovies();

        //If the sortCriteria is equal to "favorites", set the FavoriteAdapter to the RecyclerView
        // and observe the list of MovieEntry and update UI to display favorite movies
        if(sortCriteria.equals(getString(R.string.pref_sort_by_favorites))){
            mMainBinding.rvMovie.setAdapter(mFavoriteAdapter);
            observeFavoriteMovies();
        } else{
            //otherwise set the MoviePagedListAdapter to the RecyclerView
            // and observe the MoviePagedList
            // and update the UI to display movies

            mMainBinding.rvMovie.setAdapter(mMoviePagedListAdapter);
            observeMoviePagedList();
        }
    }

    /**
     * Update the MoviePagedList from LiveData in MainActivityViewModel
     */
    private void observeMoviePagedList(){
        mMainViewModel.getMoviePagedList().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> pagedList) {
                showMovieDataView();
                if(pagedList != null){
                    mMoviePagedListAdapter.submitList(pagedList);

                    //restore the scroll position after setting up the adapter
                    // with the list of movies

                    mMainBinding.rvMovie.getLayoutManager().onRestoreInstanceState(mSavedLayoutState);

                }

                //when offline, make the movie data view visible and show a snackbar message
                if(!isOnline()){
                    showMovieDataView();
                    showSnackbarOffline();
                }
            }
        });
    }

    /**
     * Update the list of MovieEntries from LiveData in MainActivityViewModel
     */
    private void observeFavoriteMovies(){
        mMainViewModel.getFavoriteMovies().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(List<MovieEntry> movieEntries) {
                //set the list of MovieEntries to display favorite movies
                mFavoriteAdapter.setMovies(movieEntries);

                //restore the scroll position after setting up the adapter with the list of favorite movies
                mMainBinding.rvMovie.getLayoutManager().onRestoreInstanceState(mSavedLayoutState);

                if(movieEntries == null || movieEntries.size() == 0){

                    //when there are no favorite movies, display an empty view
                    showEmptyView();
                } else if(!isOnline())
                {
                    // when offline, make the movie data view visible
                    showMovieDataView();
                                    }
            }
        });
    }

    /**
     * When preferences have been changed, make a network request again.
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
        // if the key is equal to sort_by, update the sort by preference
        if(key.equals(getString(R.string.pref_sort_by_key))){
            mSortCriteria = sharedPreferences.getString(key,
                    getString(R.string.pref_sort_by_default));
        }

        //when SharedPreference changes, observe the data and update the UI
        //Set a new value for the PagedList of movies to clear old list and reload. Needs to call it
        // when the SharedPreferences is changed because at that time its okay to overwrite everything.
        mMainViewModel.setMoviePagedList(mSortCriteria);
        updateUI(mSortCriteria);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //unregister MainActivity as an OnPreferenceChangedListener to avoid any memory leaks
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

/**
 * This method is overwritten by our MainActivity class in order to handle RecyclerView
 * item clicks.
 *
 * @param movie The movie that was clicked
 */
@Override
    public void onItemClick(Movie movie){
    // Wrap the parcelable into a bundle
    Bundle b = new Bundle();
    b.putParcelable(EXTRA_MOVIE, movie);

    //Create the Intent the will start the DetailActivity
    Intent intent = new Intent(MainActivity.this, DetailActivity.class);

    //Pass the bundle through Intent
    intent.putExtra(EXTRA_MOVIE, b);

    //once the Intent has been created, start the DetailActivity
    startActivity(intent);
}

@Override
    public void onFavoriteItemClick(MovieEntry movieEntry){
    // Get the movie data from the MovieEntry
    int movieId = movieEntry.getMovieId();
    String originalTitle = movieEntry.getOriginalTitle();
    String title = movieEntry.getTitle();
    String posterPath = movieEntry.getPosterPath();
    String overview = movieEntry.getOverview();
    double voteAverage = movieEntry.getVoteAverage();
    String releaseDate = movieEntry.getReleaseDate();
    String backdropPath = movieEntry.getBackdropPath();

    //Create a movie object based on the MovieEntry data
    Movie movie = new Movie(movieId, originalTitle, title, posterPath, overview, voteAverage, releaseDate, backdropPath);

    //wrap the parcelable into a bundle
    Bundle b = new Bundle();
    b.putParcelable(EXTRA_MOVIE, movie);

    //Create the Intent that will start the DetailActivity
    Intent intent = new Intent(MainActivity.this, DetailActivity.class);

    //Pass the bundle through Intent
    intent.putExtra(EXTRA_MOVIE, b);

    //Once the Intent has been created, start the DetailActivity
    startActivity(intent);
}

/**
 * Set the SwipeRefreshLayout triggered by a swipe gesture.
 */
private void setSwipeRefreshLayout(){

    //Set the colors used in the progress animation
    mMainBinding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

    //Set the listener to be notified when a refresh is triggered
    mMainBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

        /**
         * Called when a swipe gesture triggers a refresh
         */
        @Override
        public void onRefresh(){
            //make the movie data visible and hide an empty message
            showMovieDataView();

            //when refreshing, observe the data and update the UI
            updateUI(mSortCriteria);

            //hide refresh progress
            hideRefresh();

            //when online, show a snackbar message notifying updated
            showSnackbarRefresh(isOnline());
        }
    });
}

/**
 * When online, show a snack bar message notifying updated
 *
 * @param isOnline True if connected to the network
 */
private void showSnackbarRefresh(boolean isOnline){
    if(isOnline){
        //show snack bar message
        Snackbar.make(mMainBinding.rvMovie, getString(R.string.snackbar_updated), Snackbar.LENGTH_SHORT).show();
    }
}

/**
 * Check if there is network connectivity
 *
 * @return true if connected to the network
 */
private boolean isOnline(){

    //Get a reference to the ConnectivityManager to check the state of network connectivity
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

    //get details on the currently active default data network
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

    return networkInfo != null && networkInfo.isConnected();
}

/**
 * Show a dialog when there is no internet connection
 *
 * @param isOnline true if connected to the network
 */
private void showNetworkDialog(final boolean isOnline){
    if(!isOnline){

        //Create an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        //Set an Icon and title, and message
        builder.setIcon(R.drawable.ic_warning);

        builder.setTitle(getString(R.string.no_network_title));

        builder.setMessage(getString(R.string.no_network_message));

        builder.setPositiveButton(getString(R.string.go_to_settings),
                new DialogInterface.OnClickListener(){
            @Override
                    public void onClick(DialogInterface dialog, int which){
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), REQUEST_CODE_DIALOG);
            }
                });

        builder.setNegativeButton(getString(R.string.cancel), null);

        //Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}

/**
 * This method will make the View for the movie data visible
 */
private void showMovieDataView(){
    //First hide an empty view
    mMainBinding.tvEmpty.setVisibility(View.INVISIBLE);
    //Then make sure the movie data is visible
    mMainBinding.rvMovie.setVisibility(View.VISIBLE);
}

/**
 * When there are no favorite movies, display an empty view
 */
private void showEmptyView(){
    mMainBinding.tvEmpty.setVisibility(View.VISIBLE);

    mMainBinding.tvEmpty.setText(getString(R.string.message_empty_favorites));

    mMainBinding.tvEmpty.setTextColor(Color.WHITE);
}

/**
 * When offline, show a snackbar message
 */
private void showSnackbarOffline(){
    Snackbar snackbar = Snackbar.make(
            mMainBinding.frameMain, R.string.snackbar_offline, Snackbar.LENGTH_LONG
    );
    //set background color of the snackbar
    View sbView = snackbar.getView();

    sbView.setBackgroundColor(Color.WHITE);

    //set background color of the snackbar
    TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);

    textView.setTextColor(Color.BLACK);
    snackbar.show();
}

/**
 * Set column spacing to make each column have the same spacing
 */
private void setColumnSpacing(){
    GridSpacingItemDecoration decoration = new GridSpacingItemDecoration(
            GRID_SPAN_COUNT, GRID_SPACING, GRID_INCLUDE_EDGE
    );

    mMainBinding.rvMovie.addItemDecoration(decoration);
}

/**
 * Methods for setting up the menu
 */
@Override
public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item){
    int id = item.getItemId();
    switch(id){
        case R.id.action_settings:
            // launch SettingsActivity when the Settings option is clicked
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;

        default:
            return super.onOptionsItemSelected(item);
    }
}

/**
 * Method for persisting data across Activity recreation
 */
@Override
    protected void onSaveInstanceState(Bundle outState){
    super.onSaveInstanceState(outState);
    //store the scroll position in our bundle
    outState.putParcelable(LAYOUT_MANAGER_STATE,
            mMainBinding.rvMovie.getLayoutManager().onSaveInstanceState());
}

/**
 * Hide refresh progress
 */
private void hideRefresh(){
    mMainBinding.swipeRefresh.setRefreshing(false);
}







}
