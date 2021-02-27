package com.example.popularmovies2.view.main;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies2.R;
import com.example.popularmovies2.database.MovieDatabase;
import com.example.popularmovies2.database.MovieEntry;
import com.example.popularmovies2.databinding.FavListItemBinding;
import com.example.popularmovies2.utils.AppExecutors;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.popularmovies2.utils.Constants.DELETE;
import static com.example.popularmovies2.utils.Constants.DELETE_GROUP_ID;
import static com.example.popularmovies2.utils.Constants.DELETE_ORDER;
import static com.example.popularmovies2.utils.Constants.IMAGE_BASE_URL;
import static com.example.popularmovies2.utils.Constants.IMAGE_FILE_SIZE;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    /** Member variable for the list of MovieEntries that holds movie data **/
    private List<MovieEntry> mMovieEntries;

    /** Context we use to utility methods, app resources and layout inflaters **/
    private Context mContext;

    /** an on-click handler that we've defined to make it easy for a Activity to interface with
     *  our Recyclerview
     */
    private final FavoriteAdapterOnClickHandler mOnClickHandler;



    /**
     * The interface that receives onClick messages
     */
    public interface FavoriteAdapterOnClickHandler {
        void onFavoriteItemClick(MovieEntry movieEntry);
    }

    /**
     * Constructor for the FavoriteAdapter
     */
    public FavoriteAdapter(Context context, FavoriteAdapterOnClickHandler onClickHandler){
        mContext = context;
        mOnClickHandler = onClickHandler;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView
     *
     * @return A new FavoriteViewHolder that holds the FavoriteListItemBinding
     */
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        FavListItemBinding favItemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.fav_list_item, parent, false);
        return new FavoriteViewHolder(favItemBinding);
    }


    /**
     * Called by the RecyclerView to display the data at the specified position
     *
     * @param holder The ViewHolder which should be updated to represent
     *               the contents of the item at the given position
     *               in the data set.
     *
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position){
        MovieEntry movieEntry = mMovieEntries.get(position);
        holder.bind(movieEntry);
    }

    /**
     * returns the number of items to display
     */
    @Override
    public int getItemCount(){
        if(mMovieEntries == null) return 0;

        return mMovieEntries.size();
    }

    /**
     * when data changes , updates the list of movieEntries
     * and notifies the adapter to use the new values on it
     */
    public void setMovies(List<MovieEntry> movieEntries){
        mMovieEntries = movieEntries;
        notifyDataSetChanged();
    }


    /**
     * returns the list of MovieEntries
     */
    public List<MovieEntry> getMovies() { return mMovieEntries; }


    /**
    * Cache of the children views for favorite movie list item.
    */
public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener,
        MenuItem.OnMenuItemClickListener {


    /** this field is used for data binding **/
    FavListItemBinding mFavoriteItemBinding;

    /**
     * Constructor for FavoriteViewHolder
     *
     */
    public FavoriteViewHolder(FavListItemBinding favListItemBinding){
        super(favListItemBinding.getRoot());

        mFavoriteItemBinding = favListItemBinding;

        //call setOnClickListener on the view
        itemView.setOnClickListener(this);

        //call setOnCreateContextMenuListener on the view
        itemView.setOnCreateContextMenuListener(this);

    }

    void bind(MovieEntry movieEntry){
        // get the complete thumbnail path
        String thumbnail = IMAGE_BASE_URL + IMAGE_FILE_SIZE + movieEntry.getPosterPath();

        // Load thumbnail with Picasso library
        Picasso.get()
                .load(thumbnail)
                .into(mFavoriteItemBinding.ivThumbnail);

        // Set title of the movie to the TextView
        mFavoriteItemBinding.tvTitle.setText(movieEntry.getTitle());
    }

    /**
     * Called whenever a user clicks on a movie in the list
     * @param v The view that was clicked
     */
    @Override
    public void onClick(View v){
        int adapterPosition = getAdapterPosition();
        MovieEntry movieEntry = mMovieEntries.get(adapterPosition);
        mOnClickHandler.onFavoriteItemClick(movieEntry);
    }

    /**
     * when the user performs a long-click on a favorite movie item,
     * a floating menu appears.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        int adapterPosition = getAdapterPosition();
        //set the itemId to adapterPosition to retrieve movieEntry later
        MenuItem item = menu.add(DELETE_GROUP_ID, adapterPosition, DELETE_ORDER, "Delete");
        item.setOnMenuItemClickListener(this);
    }

    /**
     * this gets called when a menu item is clicked
     */
    @Override
    public boolean onMenuItemClick(MenuItem item){
        switch(item.getTitle().toString()){
            case DELETE:
                int adapterPosition = item.getItemId();
                MovieEntry movieEntry = mMovieEntries.get(adapterPosition);
                //delete a favorite movie
                delete(movieEntry);
                return true;
            default:
                return false;
        }
    }

    /**
     * Delete a favorite movie when the user clicks "Delete" menu option
     */
    private void delete(final MovieEntry movieEntry){
        //Get the MovieDatabase instance
        final MovieDatabase db = MovieDatabase.getInstance(mContext);
        AppExecutors.getExecutorInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                //delete a favorite movie from the MovieDatabase by using movieDao
                db.movieDao().deleteMovie(movieEntry);
            }
        });
    }
}
}
