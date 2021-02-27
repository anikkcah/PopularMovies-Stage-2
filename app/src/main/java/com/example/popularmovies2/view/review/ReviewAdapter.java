package com.example.popularmovies2.view.review;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies2.R;
import com.example.popularmovies2.databinding.ReviewListItemBinding;
import com.example.popularmovies2.model.Reviews;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    /** Member variable for the list of {@link Reviews}s */
    private List<Reviews> mReviews;

    /** An on-click handler that we've defined to make it easy for a Fragment to interface with
     * our RecyclerView
     */
    private final ReviewAdapterOnClickHandler mOnClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface ReviewAdapterOnClickHandler {
        void onItemClick(String url);
    }

    /**
     * Constructor for ReviewAdapter that accepts a list of reviews to display
     *
     * @param reviews List of {@link Reviews}s
     * @param onClickHandler The on-click handler for this adapter. This single handler is called
     *                       when an item is clicked.
     */
    public ReviewAdapter(List<Reviews> reviews, ReviewAdapterOnClickHandler onClickHandler) {
        mReviews = reviews;
        mOnClickHandler = onClickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created.
     *
     *  @return A new ReviewViewHolder that holds the ReviewListItemBinding
     */
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ReviewListItemBinding reviewItemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.review_list_item, viewGroup, false);
        return new ReviewViewHolder(reviewItemBinding);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position.
     */
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Reviews review = mReviews.get(position);
        holder.bind(review);
    }

    /**
     * This method simply return the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of reviews
     */
    @Override
    public int getItemCount() {
        if (null == mReviews) return 0;
        return mReviews.size();
    }

    /**
     * This method is to add a list of {@link Reviews}s
     */
    public void addAll(List<Reviews> reviews) {
        mReviews.clear();
        mReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    /**
     * Cache of the children views for a review list item.
     */
    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /** This field is used for data binding */
        ReviewListItemBinding mReviewItemBinding;

        /**
         * Constructor for our ViewHolder
         *
         * @param reviewItemBinding Used to access the layout's variables and views
         */
        ReviewViewHolder(ReviewListItemBinding reviewItemBinding) {
            super(reviewItemBinding.getRoot());
            mReviewItemBinding = reviewItemBinding;

            // Call setOnClickListener on  the View passed into the constructor
            itemView.setOnClickListener(this);
        }

        /**
         * This method will take a Review object as input and use that review to display the appropriate
         * text within a list item
         */
        void bind(Reviews review) {
            // Set author and content of review to the TextView
            mReviewItemBinding.setReview(review);
        }

        /**
         * Called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Reviews review = mReviews.get(adapterPosition);
            mOnClickHandler.onItemClick(review.getUrl());
        }
    }
}
