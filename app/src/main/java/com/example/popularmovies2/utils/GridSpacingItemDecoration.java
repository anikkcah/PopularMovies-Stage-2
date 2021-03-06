package com.example.popularmovies2.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import static com.example.popularmovies2.utils.Constants.ONE;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    /**
     * Constructor
     *
     * @param spanCount number of columns
     * @param spacing spacing between each grid item
     * @param includeEdge whether to include left and right margins
     *
     */
    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge){
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        // item position
        int position = parent.getChildAdapterPosition(view);

        //item column
        int column = position % spanCount;

        if(includeEdge) {
            //spacing - column * ((1f / spanCount) * spacing )
            outRect.left = spacing - column * spacing / spanCount;

            // (column + 1) * ( (1f / spanCount) * spacing )
            outRect.right = (column + ONE) * spacing / spanCount;

            //top edge
            if (position < spanCount) {
                outRect.top = spacing;
            }

            // item bottom
            outRect.bottom = spacing;
        }
        else {
            // column * (( 1f / spanCount) * spacing )
            outRect.left = column * spacing /spanCount;
            if(position >= spanCount){
                // item top
                outRect.top = spacing;
            }
        }
    }
    }





